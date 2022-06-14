package com.novi.webshop.controller;

import com.novi.webshop.dto.ProductDto;
import com.novi.webshop.model.Product;
import com.novi.webshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<ProductDto> getProductByName(@PathVariable String productName) {
        return ResponseEntity.ok(productService.getProductByName(productName));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @GetMapping("/price/{minimumPrice}/{maximumPrice}")
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(@PathVariable int minimumPrice, @PathVariable int maximumPrice) {
        return ResponseEntity.ok(productService.getProductsPriceRange(minimumPrice, maximumPrice));
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
        public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        final URI location = URI.create("/product" + productDto.getId());
        return ResponseEntity.created(location).body(productService.createProduct(productDto));
        }

    @PutMapping(value = "/change/{id}/{type}",
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductDto> updateTelevision(@PathVariable Long id , @PathVariable String type, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.changeProduct(id, type, productDto));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
