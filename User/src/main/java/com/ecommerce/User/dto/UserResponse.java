package com.ecommerce.User.dto;

import com.ecommerce.User.Modals.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String  id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole role;
    private AddressDTO address;
}
