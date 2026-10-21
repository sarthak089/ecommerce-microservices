package com.ecommerce.Order.Services;


import com.ecommerce.Order.Modals.CartItem;
import com.ecommerce.Order.Repositories.CartItemRepository;
import com.ecommerce.Order.clients.ProductServiceClient;
import com.ecommerce.Order.clients.UserServiceClient;
import com.ecommerce.Order.dto.CartItemRequest;
import com.ecommerce.Order.dto.ProductResponse;
import com.ecommerce.Order.dto.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    //private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;


    //private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        // Look for product
        ProductResponse productResponse =
                productServiceClient.getProductDetails(request.getProductId());
        if(productResponse == null)
        {
            return false;
        }

        if(productResponse.getStockQuantity() < request.getQuantity()){
            return false;
        }

        UserResponse userResponse = userServiceClient.getUserDetails(userId);
        if(userResponse == null)
        {
            return false;
        }
//
//        if (productOpt.isEmpty()) {
//            return false;
//        }
//
//        Product product = productOpt.get();
//
//        if (product.getStockQuantity() < request.getQuantity()) {
//            return false;
//        }
//
//        Optional<User> userOpt =
//                userRepository.findById(Long.valueOf(userId));
//
//        if (userOpt.isEmpty()) {
//            return false;
//        }

      //  User user = userOpt.get();

        CartItem existingCartItem =
                cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        if (existingCartItem != null) {
            existingCartItem.setQuantity(
                    existingCartItem.getQuantity() + request.getQuantity()
            );

            existingCartItem.setPrice(BigDecimal.valueOf(1000));


            cartItemRepository.save(existingCartItem);
        } else {
            // Create new cart item
            CartItem cartItem = new CartItem();

            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());

            cartItem.setPrice(BigDecimal.valueOf(1000));

            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {
        CartItem cartItem =
                cartItemRepository.findByUserIdAndProductId(userId, productId);

        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            return true;
        }

        return false;
    }

    public List<CartItem> getCart(String userId) {

        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
         cartItemRepository.deleteByUserId(userId);

    }
}
