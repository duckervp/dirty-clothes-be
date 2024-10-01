package com.dirty.shop.service.impl;

import com.dirty.shop.dto.projection.ProductDetailProjection;
import com.dirty.shop.dto.request.*;
import com.dirty.shop.dto.response.OrderDetailResponse;
import com.dirty.shop.dto.response.OrderItemResponse;
import com.dirty.shop.dto.response.OrderResponse;
import com.dirty.shop.enums.OrderStatus;
import com.dirty.shop.enums.PaymentMethod;
import com.dirty.shop.enums.Role;
import com.dirty.shop.enums.apicode.AddressApiCode;
import com.dirty.shop.enums.apicode.AuthApiCode;
import com.dirty.shop.enums.apicode.OrderApiCode;
import com.dirty.shop.exception.ApiException;
import com.dirty.shop.model.*;
import com.dirty.shop.repository.*;
import com.dirty.shop.service.OrderService;
import com.dirty.shop.utils.AuthUtils;
import com.dirty.shop.utils.StreamUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final UserRepository userRepository;

    @Override
    public Page<OrderResponse> findAll(FindOrderRequest request) {
        boolean setUserId = Role.USER.equals(AuthUtils.currentRole())
                || (Role.ADMIN.equals(AuthUtils.currentRole()) && Boolean.TRUE.equals(request.getUserOnly()));

        if (setUserId) {
            request.setUserId(AuthUtils.currentUserId());
        }

        Sort sort = Sort.by(Sort.Direction.fromString(request.getSort()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPageNo(), request.getPageSize(), sort);
        Page<OrderResponse> page = orderRepository.findOrder(request, pageable);
        List<Long> orderIds = page.getContent().stream().map(OrderResponse::getId).toList();
        List<OrderItemResponse> orderItemResponses = orderDetailRepository.findOrderItemResponse(orderIds);
        Map<Long, OrderItemResponse> mapOrderItemToOrderId = new HashMap<>();
        Map<Long, Set<Long>> mapOrderTotalItem = new HashMap<>();
        Map<Long, Integer> mapOrderTotalItemQuantity = new HashMap<>();
        for (OrderItemResponse orderItemResponse : orderItemResponses) {
            if (!mapOrderItemToOrderId.containsKey(orderItemResponse.getOrderId())) {
                mapOrderItemToOrderId.put(orderItemResponse.getOrderId(), orderItemResponse);
            }

            if (!mapOrderTotalItem.containsKey(orderItemResponse.getOrderId())) {
                mapOrderTotalItem.put(orderItemResponse.getOrderId(), new HashSet<>());
            }
            mapOrderTotalItem.get(orderItemResponse.getOrderId()).add(orderItemResponse.getOrderDetailId());

            if (!mapOrderTotalItemQuantity.containsKey(orderItemResponse.getOrderId())) {
                mapOrderTotalItemQuantity.put(orderItemResponse.getOrderId(), 0);
            }
            mapOrderTotalItemQuantity.put(
                    orderItemResponse.getOrderId(),
                    mapOrderTotalItemQuantity.get(orderItemResponse.getOrderId()) + orderItemResponse.getQuantity());
        }

        page.getContent().forEach(t -> {
            t.setFirstItem(mapOrderItemToOrderId.get(t.getId()));
            t.setTotalItems(Objects.nonNull(mapOrderTotalItem.get(t.getId())) ? mapOrderTotalItem.get(t.getId()).size() : 0);
            t.setTotalItemQuantity(mapOrderTotalItemQuantity.get(t.getId()));
        });
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

        if (Role.USER.equals(AuthUtils.currentRole()) && !order.getUserId().equals(AuthUtils.currentUserId())) {
            throw new ApiException(AuthApiCode.PERMISSION_DENIED);
        }

        return getOrderDetailResponse(order);
    }

    @Override
    public String save(OrderRequest request) {
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
                .userId(AuthUtils.currentUserId())
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
            orderDetails.add(orderDetail);
        }

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

        if (Role.USER.equals(AuthUtils.currentRole()) && !order.getUserId().equals(AuthUtils.currentUserId())) {
            throw new ApiException(AuthApiCode.PERMISSION_DENIED);
        }

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

                orderDetail.setQuantity(orderDetailRequest.getQuantity());
                orderDetail.setPrice(orderDetailRequest.getPrice());
            }
        }

        orderDetailRepository.saveAll(orderDetails);
        return "Update order successful";
    }

    @Override
    public String delete(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();

        if (Role.USER.equals(AuthUtils.currentRole()) && !order.getUserId().equals(AuthUtils.currentUserId())) {
            throw new ApiException(AuthApiCode.PERMISSION_DENIED);
        }

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

    @Override
    public String bulkAction(OrderBulkActionRequest request) {
        List<Order> orders = orderRepository.findAllById(request.getOrderIds());
        if (!orders.isEmpty()) {
            OrderStatus currentStatus = orders.getFirst().getStatus();

            if (currentStatus.isFinalStatus()) {
                throw new ApiException(OrderApiCode.ORDER_FINAL_STATUS);
            }

            if (currentStatus.getLevel() >= request.getStatus().getLevel()) {
                throw new ApiException(OrderApiCode.ORDER_STATUS_PRIORITY);
            }

            if (request.getStatus().getLevel() - currentStatus.getLevel() > 1) {
                throw new ApiException(OrderApiCode.ORDER_STATUS_PRIORITY);
            }

            orders.forEach(item -> item.setStatus(request.getStatus()));
            orderRepository.saveAll(orders);

            if (request.getStatus().equals(OrderStatus.ACCEPTED)) {
                List<Long> orderIds = StreamUtils.toList(orders, Order::getId);
                List<OrderDetail> orderDetails = orderDetailRepository.findByOrderIdIn(orderIds);
                List<Long> productDetailIds = orderDetails.stream()
                        .map(OrderDetail::getProductDetailId).toList();
                List<ProductDetail> productDetails = productDetailRepository.findAllById(productDetailIds);
                Map<Long, ProductDetail> mapProductDetailToId = StreamUtils.toMap(productDetails, ProductDetail::getId);

                for (OrderDetail orderDetail : orderDetails) {
                    ProductDetail productDetail = mapProductDetailToId.get(orderDetail.getProductDetailId());
                    productDetail.setInventory(productDetail.getInventory() - orderDetail.getQuantity());
                    productDetail.setSold(productDetail.getSold() + orderDetail.getQuantity());
                }

                productDetailRepository.saveAll(productDetails);
            }
        }
        return "Update orders status successfully!";
    }

    @Override
    public String updateStatus(Long id, OrderStatusRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ApiException(OrderApiCode.ORDER_NOT_FOUND));
        if (order.getStatus().getLevel() >= request.getStatus().getLevel()) {
            throw new ApiException(OrderApiCode.ORDER_STATUS_PRIORITY);
        }

        if (order.getStatus().isFinalStatus()) {
            throw new ApiException(OrderApiCode.ORDER_FINAL_STATUS);
        }

        if (request.getStatus().getLevel() - order.getStatus().getLevel() > 1) {
            throw new ApiException(OrderApiCode.ORDER_STATUS_PRIORITY);
        }

        if (request.getStatus().equals(OrderStatus.ACCEPTED)) {
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
            List<Long> productDetailIds = orderDetails.stream()
                    .map(OrderDetail::getProductDetailId).toList();
            List<ProductDetail> productDetails = productDetailRepository.findAllById(productDetailIds);
            Map<Long, ProductDetail> mapProductDetailToId = StreamUtils.toMap(productDetails, ProductDetail::getId);

            for (OrderDetail orderDetail : orderDetails) {
                ProductDetail productDetail = mapProductDetailToId.get(orderDetail.getProductDetailId());
                productDetail.setInventory(productDetail.getInventory() - orderDetail.getQuantity());
                productDetail.setSold(productDetail.getSold() + orderDetail.getQuantity());
            }

            productDetailRepository.saveAll(productDetails);
        }

        order.setStatus(request.getStatus());
        orderRepository.save(order);
        return "Update orders status successfully!";
    }

    @Override
    public String cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ApiException(OrderApiCode.ORDER_NOT_FOUND));

        Long currentUserId = AuthUtils.currentUserId();

        if (!currentUserId.equals(order.getUserId())) {
            throw new ApiException(AuthApiCode.PERMISSION_DENIED);
        }

        if (order.getStatus().isFinalStatus()) {
            throw new ApiException(OrderApiCode.ORDER_FINAL_STATUS);
        }

        if (order.getStatus().getLevel() >= OrderStatus.CANCELLED.getLevel()) {
            throw new ApiException(OrderApiCode.ORDER_STATUS_PRIORITY);
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
        return "Cancel order successfully!";
    }

    /* PRIVATE */

    private OrderDetailResponse getOrderDetailResponse(Order order) {
        User user = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new ApiException(AuthApiCode.USER_NOT_FOUND));
        Address address = addressRepository.findOrderAddressById(order.getShippingAddressId())
                .orElseThrow(() -> new ApiException(AddressApiCode.ADDRESS_NOT_FOUND));
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
        List<Long> productDetailIds = StreamUtils.toList(orderDetails, OrderDetail::getProductDetailId);

        List<ProductDetailProjection> productDetails = productDetailRepository.findProductDetailByIdIn(productDetailIds);
        Map<Long, ProductDetailProjection> mapProductDetailById = StreamUtils
                .toMap(productDetails, ProductDetailProjection::getProductDetailId);

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            ProductDetailProjection productDetail = mapProductDetailById.get(orderDetail.getProductDetailId());

            if (Objects.isNull(productDetail)) {
                continue;
            }

            OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                    .orderDetailId(orderDetail.getId())
                    .productName(productDetail.getProductName())
                    .price(orderDetail.getPrice())
                    .quantity(orderDetail.getQuantity())
                    .color(productDetail.getProductColor())
                    .imageUrl(productDetail.getImageUrl())
                    .size(productDetail.getProductSize().getValue())
                    .slug(productDetail.getSlug())
                    .build();

            orderItemResponses.add(orderItemResponse);
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
                .createdAt(order.getCreatedAt())
                .createdBy(order.getCreatedBy())
                .updatedAt(order.getUpdatedAt())
                .updatedBy(order.getUpdatedBy())
                .user(User.getUserInfo(user))
                .build();
    }
}
