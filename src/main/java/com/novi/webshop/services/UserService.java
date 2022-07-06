package com.novi.webshop.services;

import org.springframework.lang.NonNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface UserService {
    boolean doesUsernameAlreadyExist(String username);

    Long findIdFromUsername(String username);

    String findRoleFromUsername(String username);
}
