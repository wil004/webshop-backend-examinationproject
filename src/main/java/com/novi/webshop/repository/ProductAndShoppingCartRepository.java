package com.novi.webshop.repository;

import com.novi.webshop.model.ProductAndShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAndShoppingCartRepository extends JpaRepository<ProductAndShoppingCart, Long> {
}
