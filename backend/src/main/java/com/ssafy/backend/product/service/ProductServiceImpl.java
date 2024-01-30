package com.ssafy.backend.product.service;

import com.ssafy.backend.category.model.domain.ProductCategory;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.response.BaseResponseStatus;
import com.ssafy.backend.inventory.model.domain.Inventory;
import com.ssafy.backend.inventory.model.repository.InventoryRepository;
import com.ssafy.backend.product.model.domain.Product;
import com.ssafy.backend.product.model.dto.ProductDto;
import com.ssafy.backend.product.model.dto.ProductListResDto;
import com.ssafy.backend.product.model.repository.ProductRepository;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_EXIST_INVENTORY;
import static com.ssafy.backend.common.response.BaseResponseStatus.NOT_EXIST_PRODUCT;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Override
    public ProductListResDto getList(int page) throws BaseException {
        ArrayList<Sort.Order> sorts = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, 10,Sort.by(sorts));
        Page<Product> products = productRepository.findAll(pageable);

        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product: products){
            ProductCategory productCategory = product.getProductCategory();
            ProductDto productDto = ProductDto.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productCategoryDto(productCategory.toDto())
                    .productPrice(product.getProductPrice())
                    .productDescription(product.getProductDescription())
                    .build();
            productDtoList.add(productDto);
        }

        ProductListResDto productListResDto = ProductListResDto.builder()
                .totalPage(products.getTotalPages())
                .productList(productDtoList)
                .build();

        return productListResDto;
    }

    @Override
    public List<Product> searchList(int categoryId) throws MyException {
        return productRepository.findByProductCategory_ProductCategoryId(categoryId);
    }

    @Override
    public void buyProduct(int productId, String userId) {
        User user = userRepository.findUserByUserId(userId);
        if(user == null){
            throw new BaseException(BaseResponseStatus.NOT_EXIST_MEMBER);
        }

        Product product = productRepository.findById(productId).orElseThrow(()-> new BaseException(NOT_EXIST_PRODUCT));
        int price = product.getProductPrice();
        deductPoint(user, price);

        inventoryRepository.save(Inventory.builder()
                .isWearing(0)
                .product(product)
                .userId(userId)
                .build());
    }

    @Override
    public void sellProduct(int inventoryId, String userId) throws BaseException {
        User user = userRepository.findUserByUserId(userId);
        if(user == null){
            throw new BaseException(BaseResponseStatus.NOT_EXIST_MEMBER);
        }
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(()-> new BaseException(NOT_EXIST_INVENTORY));
        int price = inventory.getProduct().getProductPrice();
        raisePoint(user, price);
        inventoryRepository.deleteByInventoryId(inventory.getInventoryId());
    }

    private void raisePoint(User user, int price) {
        int prevPoint = user.getUserPoint();
        user.setUserPoint(prevPoint + price);
        userRepository.save(user);
    }

    public void deductPoint(User user, int price){
        int prevPoint = user.getUserPoint();
        user.setUserPoint(prevPoint - price);
        userRepository.save(user);
    }
}
