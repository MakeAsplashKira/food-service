package com.example.foodservice.brandservice;

import com.example.foodservice.brandservice.entity.Product;
import com.example.foodservice.brandservice.exception.SomeProductsNotFoundException;
import com.example.foodservice.brandservice.repository.ProductRepository;
import com.example.foodservice.storeservice.ProductCatalog;
import com.example.foodservice.storeservice.dto.ProductDTO.ProductInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCatalogProvider implements ProductCatalog {
    private final ProductRepository productRepository;

    @Override
    public List<ProductInfo> getProductsByIds(List<Long> productIds) {

        List<Product> products = productRepository.findAllById(productIds);

        if(products.size() != productIds.size()) {
            throw new SomeProductsNotFoundException(); //TODO: передавать id-шники, которые не нашлись
        }

        return products.stream().map(p -> new ProductInfo(p.getId(), p.getName(), p.getCategory(), p.getImageUrl())).toList();
    }
}
