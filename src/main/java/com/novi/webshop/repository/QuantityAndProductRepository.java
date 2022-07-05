package com.novi.webshop.repository;

import com.novi.webshop.model.QuantityAndProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityAndProductRepository extends JpaRepository<QuantityAndProduct, Long> {
}
