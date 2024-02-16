package com.ssafy.backend.product.service;

import com.ssafy.backend.category.model.domain.ProductCategory;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.inventory.model.domain.Inventory;
import com.ssafy.backend.inventory.model.repository.InventoryRepository;
import com.ssafy.backend.product.model.domain.Product;
import com.ssafy.backend.product.model.dto.ProductDTO;
import com.ssafy.backend.product.model.repository.ProductRepository;
import com.ssafy.backend.product.model.vo.ProductListVO;
import com.ssafy.backend.product.model.vo.ProductVO;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Override
    public ProductListVO getList(int page) throws BaseException {
        ArrayList<Sort.Order> sorts = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, 30,Sort.by(sorts));
        Page<Product> products = productRepository.findAll(pageable);

        List<ProductDTO> productDTOList = new ArrayList<>();
        for(Product product: products){
            ProductCategory productCategory = product.getProductCategory();
            ProductDTO productDto = ProductDTO.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productCategoryDto(productCategory.toDto())
                    .productPrice(product.getProductPrice())
                    .productDescription(product.getProductDescription())
                    .productImage(product.getProductImage())
                    .build();
            productDTOList.add(productDto);
        }

        ProductListVO productListVO = ProductListVO.builder()
                .totalPage(products.getTotalPages())
                .productList(productDTOList)
                .build();

        return productListVO;
    }

    @Override
    public List<ProductVO> searchList(int categoryId) throws MyException {
        List<Product> products = productRepository.findByProductCategory_ProductCategoryId(categoryId);
        List<ProductVO> productVOS = products.stream()
                .map(p -> new ProductVO(p.getProductId(),p.getProductName(),p.getProductCategory().toDto(),p.getProductPrice(),p.getProductImage(),p.getProductDescription()))
                .collect(Collectors.toList());

        return productVOS;
    }

    @Override
    public void buyProduct(int productId, String userId) {
        User user = userRepository.findUserByUserId(userId);
        if(user == null){
            throw new BaseException(NOT_EXIST_USER);
        }
        Product product = productRepository.findById(productId).orElseThrow(()-> new BaseException(NOT_EXIST_PRODUCT));
        inventoryRepository.findProductByUserIdAndProductId(productId, userId).ifPresent(e ->{
            throw new BaseException(DUPLICATE_PURCHASE_ITEM);
        });
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
            throw new BaseException(NOT_EXIST_USER);
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
        user.usePoint(price);
        userRepository.save(user);
    }
}
