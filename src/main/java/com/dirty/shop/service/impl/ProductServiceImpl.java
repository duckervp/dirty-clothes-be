package com.dirty.shop.service.impl;

import com.dirty.shop.dto.projection.ColorProjection;
import com.dirty.shop.dto.request.ProductDetailRequest;
import com.dirty.shop.dto.request.ProductImageRequest;
import com.dirty.shop.dto.request.ProductRequest;
import com.dirty.shop.dto.request.FindProductRequest;
import com.dirty.shop.dto.response.DetailedProductResponse;
import com.dirty.shop.dto.response.ProductDetailResponse;
import com.dirty.shop.dto.response.ProductImageResponse;
import com.dirty.shop.dto.response.ProductResponse;
import com.dirty.shop.enums.apicode.ProductApiCode;
import com.dirty.shop.exception.ApiException;
import com.dirty.shop.model.*;
import com.dirty.shop.repository.*;
import com.dirty.shop.service.ProductService;
import com.dirty.shop.utils.BusinessUtils;
import com.dirty.shop.utils.SortUtils;
import com.dirty.shop.utils.StreamUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final ProductDetailRepository productDetailRepository;

    private final ProductImageRepository productImageRepository;

    private final ColorRepository colorRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public Page<ProductResponse> findAll(FindProductRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSort()), request.getSortBy());

        Pageable pageable = PageRequest.of(
                request.getPageNo(),
                request.getPageSize(),
                sort
        );

        List<Product> productList = productRepository.findAllProducts(request);

        if (Objects.nonNull(request.getCategoryValue())) {
            Category category = categoryRepository.findByValue(request.getCategoryValue()).orElse(null);
            if (Objects.isNull(category)) {
                request.setCategoryIds(List.of());
            } else {
                if (Objects.nonNull(category.getParentId())) {
                    setCategoryIds(request, List.of(category.getId()));
                } else {
                    List<Category> categories = categoryRepository.findByParentId(category.getId());
                    setCategoryIds(request, StreamUtils.toList(categories, Category::getId));
                }
            }
        }

        if (Objects.nonNull(request.getCategoryIds())) {
            productList = productList.stream().filter(product -> {
                List<Long> ctgIds = StringUtils.commaDelimitedListToSet(product.getCategoryIds())
                        .stream().map(Long::parseLong).toList();
                return ctgIds.stream().anyMatch(id -> request.getCategoryIds().contains(id));
            }).toList();
        }

        if (Objects.nonNull(request.getSizes())) {
            List<Long> productIds = StreamUtils.toList(productList, Product::getId);
            List<Long> filteredProductIds = productDetailRepository.findProductBySize(productIds, request.getSizes());
            productList = productList.stream().filter(product -> filteredProductIds.contains(product.getId())).toList();
        }

        List<Long> productIds = StreamUtils.toList(productList, Product::getId);
        List<ColorProjection> colorProjectionList = colorRepository.findColorByProductIds(productIds, request.getColorIds());

        if (Objects.nonNull(request.getColorIds())) {
            List<Long> filteredProductIds = colorProjectionList.stream().map(ColorProjection::getProductId).toList();
            productList = productList.stream().filter(product -> filteredProductIds.contains(product.getId())).toList();
        }

        Map<Long, List<ColorProjection>> mapColorByProductId = StreamUtils.groupingApply(
                colorProjectionList, ColorProjection::getProductId);

        Comparator<Product> comparator = getComparator(request);

        return StreamUtils.toPage(productList, pageable, comparator)
                .map(item -> ProductResponse.from(item, mapColorByProductId.getOrDefault(item.getId(), List.of())));
    }

    private static void setCategoryIds(FindProductRequest request, List<Long> ids) {
        if (Objects.isNull(request.getCategoryIds())) {
            request.setCategoryIds(ids);
        } else {
            List<Long> cIds = new ArrayList<>(ids);
            cIds.addAll(request.getCategoryIds());
            request.setCategoryIds(cIds);
        }
    }

    private Comparator<Product> getComparator(FindProductRequest request) {
        return switch (request.getSortBy()) {
            case "price" -> SortUtils.getComparator(Product::getPrice, request.getSort());
            case "createdAt" -> SortUtils.getComparator(Product::getCreatedAt, request.getSort());
            default -> SortUtils.getNoOpComparator();
        };
    }

    @Override
    public DetailedProductResponse findById(Long id) {
        Product product = findProductById(id);

        return findDetailedProduct(product);
    }

    @Override
    public DetailedProductResponse findBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ApiException(ProductApiCode.PRODUCT_NOT_FOUND));

        return findDetailedProduct(product);
    }

    @Override
    @Transactional
    public String save(ProductRequest request) {
        Product product = saveProduct(request);

        List<ProductDetail> productDetailList = new ArrayList<>();
        saveProductDetail(request, product, productDetailList);
        saveProductImage(request, product);
        return "Created new product successfully!";
    }

    @Override
    @Transactional
    public String update(Long id, ProductRequest request) {
        Product product = saveProduct(id, request);

        List<ProductDetail> productDetailList = productDetailRepository.findByProductId(product.getId());
        removeDeletedProductDetail(request, productDetailList);

        List<ProductImage> productImageList = productImageRepository.findByProductId(product.getId());
        removeDeletedProductImage(request, productImageList);

        Map<Long, ProductDetail> mapProductDetailById = StreamUtils.toMap(productDetailList, ProductDetail::getId);
        Map<Long, ProductImage> mapProductImageById = StreamUtils.toMap(productImageList, ProductImage::getId);
        updateProductDetail(request, mapProductDetailById, product, productDetailList);
        updateProductImage(request, product, mapProductImageById, productImageList);
        return "Updated product successfully!";
    }

    @Override
    @Transactional
    public String delete(Long id) {
        Product product = findProductById(id);
        product.setDeleted(true);
        productRepository.save(product);

        List<ProductDetail> productDetailList = productDetailRepository.findByProductId(product.getId());
        productDetailList.forEach(item -> item.setDeleted(true));
        productDetailRepository.saveAll(productDetailList);

        List<ProductImage> productImageList = productImageRepository.findByProductId(product.getId());
        productImageList.forEach(item -> item.setDeleted(true));
        productImageRepository.saveAll(productImageList);

        return "Deleted product successfully!";
    }

    @Override
    public String delete(List<Long> ids) {
        List<Product> products = productRepository.findAllById(ids);
        products.forEach(item -> item.setDeleted(true));
        productRepository.saveAll(products);

        List<ProductDetail> productDetailList = productDetailRepository.findByProductIdIn(ids);
        productDetailList.forEach(item -> item.setDeleted(true));
        productDetailRepository.saveAll(productDetailList);

        List<ProductImage> productImageList = productImageRepository.findByProductIdIn(ids);
        productImageList.forEach(item -> item.setDeleted(true));
        productImageRepository.saveAll(productImageList);

        return "Deleted products successfully!";
    }


    /* ===============Private=============== */

    private DetailedProductResponse findDetailedProduct(Product product) {
        List<Long> categoryIds = StringUtils.commaDelimitedListToSet(product.getCategoryIds())
                .stream().map(Long::parseLong).toList();

        List<Category> categoryList = categoryRepository.findAllById(categoryIds);

        List<ProductDetailResponse> productDetailResponseList = productDetailRepository.findByProductId(product.getId())
                .stream().map(ProductDetailResponse::from).toList();

        List<Color> productColors = colorRepository.findColorByProductId(product.getId());
        Map<Long, Color> mapColorById = StreamUtils.toMap(productColors, Color::getId);

        List<ProductImageResponse> productImageResponseList = productImageRepository.findByProductId(product.getId())
                .stream().map(item -> ProductImageResponse.from(item, mapColorById.get(item.getColorId()))).toList();

        return DetailedProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .status(product.getStatus())
                .price(product.getPrice())
                .target(product.getTarget())
                .productDetails(productDetailResponseList)
                .images(productImageResponseList)
                .categories(categoryList)
                .salePrice(product.getSalePrice())
                .slug(product.getSlug())
                .avatarUrl(product.getAvatarUrl())
                .createdAt(product.getCreatedAt())
                .createdBy(product.getCreatedBy())
                .updatedAt(product.getUpdatedAt())
                .updatedBy(product.getUpdatedBy())
                .build();
    }

    private Product saveProduct(Long id, ProductRequest request) {
        Product product = findProductById(id);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setTarget(request.getTarget());
        product.setStatus(request.getStatus());
        product.setCategoryIds(StringUtils.collectionToCommaDelimitedString(request.getCategoryIds()));
        product.setSalePrice(request.getSalePrice());
        product.setAvatarUrl(request.getAvatarUrl());
        product.setSlug(BusinessUtils.genSlug(request.getName()));
        productRepository.save(product);
        return product;
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ApiException(ProductApiCode.PRODUCT_NOT_FOUND));
    }

    private void saveProductImage(ProductRequest request, Product product) {
        List<ProductImage> productImageList = new ArrayList<>();

        for (ProductImageRequest piRequest : request.getImages()) {
            ProductImage productImage = ProductImage.builder()
                    .productId(product.getId())
                    .colorId(piRequest.getColorId())
                    .imageUrl(piRequest.getImageUrl())
                    .build();
            productImageList.add(productImage);
        }

        productImageRepository.saveAll(productImageList);
    }

    private void saveProductDetail(ProductRequest request, Product product, List<ProductDetail> productDetailList) {
        for (ProductDetailRequest pdRequest : request.getProductDetails()) {
            ProductDetail productDetail = ProductDetail.builder()
                    .productId(product.getId())
                    .colorId(pdRequest.getColorId())
                    .size(pdRequest.getSize())
                    .inventory(pdRequest.getInventory())
                    .sold(pdRequest.getSold())
                    .build();
            productDetailList.add(productDetail);
        }

        productDetailRepository.saveAll(productDetailList);
    }

    private Product saveProduct(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .target(request.getTarget())
                .status(request.getStatus())
                .categoryIds(StringUtils.collectionToCommaDelimitedString(request.getCategoryIds()))
                .avatarUrl(request.getAvatarUrl())
                .slug(BusinessUtils.genSlug(request.getName()))
                .salePrice(request.getSalePrice())
                .build();

        productRepository.save(product);
        return product;
    }

    private static void removeDeletedProductImage(ProductRequest request, List<ProductImage> productImageList) {
        List<Long> existedProductImageIds = StreamUtils.toList(productImageList, ProductImage::getId);
        List<Long> requestedProductImageIds = StreamUtils.toList(request.getImages(), ProductImageRequest::getId);
        List<Long> shouldBeDeletedProductImageIds = new ArrayList<>(existedProductImageIds);
        shouldBeDeletedProductImageIds.removeAll(requestedProductImageIds);

        for (ProductImage productImage : productImageList) {
            if (shouldBeDeletedProductImageIds.contains(productImage.getId())) {
                productImage.setDeleted(true);
            }
        }
    }

    private static void removeDeletedProductDetail(ProductRequest request, List<ProductDetail> productDetailList) {
        List<Long> existedProductDetailIds = StreamUtils.toList(productDetailList, ProductDetail::getId);
        List<Long> requestedProductDetailIds = StreamUtils.toList(request.getProductDetails(), ProductDetailRequest::getId);
        List<Long> shouldBeDeletedProductDetailIds = new ArrayList<>(existedProductDetailIds);
        shouldBeDeletedProductDetailIds.removeAll(requestedProductDetailIds);

        for (ProductDetail productDetail : productDetailList) {
            if (shouldBeDeletedProductDetailIds.contains(productDetail.getId())) {
                productDetail.setDeleted(true);
            }
        }
    }

    private void updateProductDetail(ProductRequest request, Map<Long, ProductDetail> mapProductDetailById,
                                     Product product, List<ProductDetail> productDetailList) {
        for (ProductDetailRequest pdRequest : request.getProductDetails()) {
            ProductDetail productDetail = null;
            if (Objects.nonNull(pdRequest.getId())) {
                productDetail = mapProductDetailById.get(pdRequest.getId());
                if (Objects.nonNull(productDetail)) {
                    productDetail.setColorId(pdRequest.getColorId());
                    productDetail.setSize(pdRequest.getSize());
                    productDetail.setInventory(pdRequest.getInventory());
                    productDetail.setSold(pdRequest.getSold());
                }
            }
            if (Objects.isNull(productDetail)) {
                productDetail = ProductDetail.builder()
                        .productId(product.getId())
                        .colorId(pdRequest.getColorId())
                        .size(pdRequest.getSize())
                        .inventory(pdRequest.getInventory())
                        .sold(pdRequest.getSold())
                        .build();
                productDetailList.add(productDetail);
            }
        }

        productDetailRepository.saveAll(productDetailList);
    }

    private void updateProductImage(ProductRequest request, Product product, Map<Long, ProductImage> mapProductImageById, List<ProductImage> productImageList) {
        for (ProductImageRequest piRequest : request.getImages()) {
            ProductImage productImage = null;
            if (Objects.nonNull(piRequest.getId())) {
                productImage = mapProductImageById.get(piRequest.getId());
                if (Objects.nonNull(productImage)) {
                    productImage.setColorId(piRequest.getColorId());
                    productImage.setImageUrl(piRequest.getImageUrl());
                }
            }
            if (Objects.isNull(productImage)) {
                productImage = ProductImage.builder()
                        .productId(product.getId())
                        .colorId(piRequest.getColorId())
                        .imageUrl(piRequest.getImageUrl())
                        .build();
                productImageList.add(productImage);
            }
        }

        productImageRepository.saveAll(productImageList);
    }

}
