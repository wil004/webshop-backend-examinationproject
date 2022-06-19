package com.novi.webshop.repository;

import com.novi.webshop.model.Admin;
import com.novi.webshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
