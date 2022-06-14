package com.novi.webshop.repository;

import com.novi.webshop.model.ProductAndReturnCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAndReturnCartRepository extends JpaRepository<ProductAndReturnCart, Long> {
}
