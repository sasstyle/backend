package com.sasstyle.orderservice.client;

import com.sasstyle.orderservice.client.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

    @GetMapping("/products/{productId}")
    ProductResponse findByProductId(@PathVariable Long productId);
}
