package com.dirty.shop.service.impl;

import com.dirty.shop.dto.projection.ProductDetailProjection;
import com.dirty.shop.dto.request.OrderDetailRequest;
import com.dirty.shop.dto.request.OrderRequest;
import com.dirty.shop.dto.request.FindOrderRequest;
import com.dirty.shop.dto.response.OrderDetailResponse;
import com.dirty.shop.dto.response.OrderItemResponse;
import com.dirty.shop.dto.response.OrderResponse;
import com.dirty.shop.enums.OrderStatus;
import com.dirty.shop.enums.PaymentMethod;
import com.dirty.shop.enums.apicode.OrderApiCode;
import com.dirty.shop.exception.ApiException;
import com.dirty.shop.model.*;
import com.dirty.shop.repository.AddressRepository;
import com.dirty.shop.repository.OrderDetailRepository;
import com.dirty.shop.repository.OrderRepository;
import com.dirty.shop.repository.ProductDetailRepository;
import com.dirty.shop.service.OrderService;
import com.dirty.shop.utils.StreamUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final ProductDetailRepository productDetailRepository;

    private final AddressRepository addressRepository;

    @Override
    public Page<OrderResponse> findAll(FindOrderRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSort()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(), sort);
        Page<OrderResponse> page = orderRepository.findOrder(request, pageable);
        List<Long> orderIds = page.getContent().stream().map(OrderResponse::getId).toList();
        List<OrderItemResponse> orderItemResponses = orderDetailRepository.findOrderItemResponse(orderIds);
        Map<Long, OrderItemResponse> mapOrderItemToOrderId = new HashMap<>();
        for (OrderItemResponse orderItemResponse : orderItemResponses) {
            if (!mapOrderItemToOrderId.containsKey(orderItemResponse.getOrderId())) {
                mapOrderItemToOrderId.put(orderItemResponse.getOrderId(), orderItemResponse);
            }
        }

        page.getContent().forEach(t -> t.setFirstItem(mapOrderItemToOrderId.get(t.getId())));
        return page;
    }

    @Override
    public OrderDetailResponse findDetailById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ApiException(OrderApiCode.ORDER_NOT_FOUND));
        return getOrderDetailResponse(order);
    }

    @Override
    public OrderDetailResponse findDetailByCode(String code) {
        Order order = orderRepository.findByCode(code).orElseThrow(() -> new ApiException(OrderApiCode.ORDER_NOT_FOUND));
        return getOrderDetailResponse(order);
    }

    @Override
    public String save(OrderRequest request) {
        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Address address = addressRepository.findById(request.getShippingAddressId()).orElseThrow();

        List<Long> productDetailIds = request.getOrderDetails().stream()
                .map(OrderDetailRequest::getProductDetailId).toList();
        List<ProductDetail> productDetails = productDetailRepository.findAllById(productDetailIds);
        Map<Long, ProductDetail> mapProductDetailToId = StreamUtils.toMap(productDetails, ProductDetail::getId);

        List<OrderDetailRequest> validOrderDetailRequest = new ArrayList<>();
        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()) {
            ProductDetail productDetail = mapProductDetailToId.get(orderDetailRequest.getProductDetailId());
            if (Objects.nonNull(productDetail)) {
                if (productDetail.getInventory() >= orderDetailRequest.getQuantity()) {
                    validOrderDetailRequest.add(orderDetailRequest);
                }
            }
        }

        Order order = Order.builder()
                .code(UUID.randomUUID().toString())
                .userId(userPrincipal.getId())
                .status(OrderStatus.ORDER)
                .paymentMethod(PaymentMethod.CASH_ON_DELIVERY)
                .shippingAddressId(address.getId())
                .shippingFee(request.getShippingFee())
                .total(request.getTotal())
                .build();
        orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailRequest orderDetailRequest : validOrderDetailRequest) {
            OrderDetail orderDetail = OrderDetail.builder()
                    .orderId(order.getId())
                    .productDetailId(orderDetailRequest.getProductDetailId())
                    .price(orderDetailRequest.getPrice())
                    .quantity(orderDetailRequest.getQuantity())
                    .build();

            ProductDetail productDetail = mapProductDetailToId.get(orderDetailRequest.getProductDetailId());
            productDetail.setInventory(productDetail.getInventory() - orderDetail.getQuantity());
            orderDetails.add(orderDetail);
        }

        productDetailRepository.saveAll(productDetails);
        orderDetailRepository.saveAll(orderDetails);
        return "Create order successful";
    }

    @Override
    @Transactional
    public String update(Long id, OrderRequest request) {
        Address address = addressRepository.findById(request.getShippingAddressId()).orElseThrow();
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(request.getOrderStatus());
        order.setShippingAddressId(address.getId());
        orderRepository.save(order);

        List<Long> productDetailIds = request.getOrderDetails().stream()
                .map(OrderDetailRequest::getProductDetailId).collect(Collectors.toList());
        List<ProductDetail> productDetails = productDetailRepository.findByIdIn(productDetailIds);
        Map<Long, ProductDetail> mapProductDetailToId = StreamUtils.toMap(productDetails, ProductDetail::getId);

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
        Map<Long, OrderDetail> mapOrderDetailToProductDetail = StreamUtils.toMap(orderDetails, OrderDetail::getProductDetailId);

        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()) {
            ProductDetail productDetail = mapProductDetailToId.get(orderDetailRequest.getProductDetailId());
            OrderDetail orderDetail = mapOrderDetailToProductDetail.get(orderDetailRequest.getProductDetailId());
            if (Objects.nonNull(productDetail) && Objects.nonNull(orderDetail)) {
                if ((orderDetailRequest.getQuantity() - orderDetail.getQuantity()) < productDetail.getInventory()) {
                    continue;
                }

                productDetail.setInventory(productDetail.getInventory() - (orderDetailRequest.getQuantity() - orderDetail.getQuantity()));

                orderDetail.setQuantity(orderDetailRequest.getQuantity());
                orderDetail.setPrice(orderDetailRequest.getPrice());
            }
        }
        productDetailRepository.saveAll(productDetails);
        orderDetailRepository.saveAll(orderDetails);
        return "Update order successful";
    }

    @Override
    public String delete(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(id);

        order.setDeleted(true);
        orderDetails.forEach(o -> o.setDeleted(true));

        orderRepository.save(order);
        orderDetailRepository.saveAll(orderDetails);
        return "Delete order successful";
    }

    @Override
    public String delete(List<Long> ids) {
        List<Order> orders = orderRepository.findAllById(ids);
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderIdIn(ids);

        orders.forEach(t -> t.setDeleted(true));
        orderDetails.forEach(o -> o.setDeleted(true));

        orderRepository.saveAll(orders);
        orderDetailRepository.saveAll(orderDetails);
        return "Delete order successful";
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    private OrderDetailResponse getOrderDetailResponse(Order order) {
        Address address = addressRepository.findById(order.getShippingAddressId()).orElseThrow();
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
        Map<Long, OrderDetail> mapOrderDetailToProductDetailId = StreamUtils.toMap(orderDetails, OrderDetail::getProductDetailId);

        List<ProductDetailProjection> productDetails = productDetailRepository.findProductDetailByIdIn(mapOrderDetailToProductDetailId.keySet().stream().toList());
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (ProductDetailProjection productDetail : productDetails) {
            OrderDetail orderDetail = mapOrderDetailToProductDetailId.get(productDetail.getProductDetailId());

            if (Objects.nonNull(orderDetail)) {
                OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                        .productName(productDetail.getProductName())
                        .price(orderDetail.getPrice())
                        .quantity(orderDetail.getQuantity())
                        .color(productDetail.getProductColor())
                        .imageUrl(productDetail.getImageUrl())
                        .build();

                orderItemResponses.add(orderItemResponse);
            }
        }

        return OrderDetailResponse.builder()
                .id(order.getId())
                .code(order.getCode())
                .status(order.getStatus())
                .total(order.getTotal())
                .shippingFee(order.getShippingFee())
                .paymentMethod(order.getPaymentMethod())
                .reason(order.getReason())
                .orderItems(orderItemResponses)
                .address(address)
                .build();
    }
}
