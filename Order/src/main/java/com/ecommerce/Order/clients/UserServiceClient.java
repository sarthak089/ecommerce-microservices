package com.ecommerce.Order.clients;

import com.ecommerce.Order.dto.ProductResponse;
import com.ecommerce.Order.dto.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient {
    @GetExchange("/api/users/{id}")
    UserResponse getUserDetails(@PathVariable String id);
}
