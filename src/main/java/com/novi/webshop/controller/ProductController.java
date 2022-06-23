package com.novi.webshop.controller;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productServiceImpl.getAllProducts());

    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<ProductDto> getProductByName(@PathVariable String productName) {
        return ResponseEntity.ok(productServiceImpl.getProductByName(productName));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productServiceImpl.getProductsByCategory(category));
    }

    @GetMapping("/price/{minimumPrice}/{maximumPrice}")
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(@PathVariable int minimumPrice, @PathVariable int maximumPrice) {
        return ResponseEntity.ok(productServiceImpl.getProductsPriceRange(minimumPrice, maximumPrice));
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
        public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        final URI location = URI.create("/product" + productDto.getId());
        return ResponseEntity.created(location).body(productServiceImpl.createProduct(productDto));
        }

    @PutMapping(value = "/change/{id}/{type}",
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductDto> updateTelevision(@PathVariable Long id , @PathVariable String type, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productServiceImpl.changeProduct(id, type, productDto));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productServiceImpl.deleteProduct(id);
    }

}
