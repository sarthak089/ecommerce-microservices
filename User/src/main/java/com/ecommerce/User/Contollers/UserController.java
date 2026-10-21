package com.ecommerce.User.Contollers;


import com.ecommerce.User.Services.UserService;
import com.ecommerce.User.dto.UserRequest;
import com.ecommerce.User.dto.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser()
    {
      return new ResponseEntity<>(userService.fetchAllUser(),HttpStatus.OK) ;
      // return ResponseEntity.ok(userService.fetchAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getAUser(@PathVariable Long id)
    {
//        User user = userService.fetchAUser(id);
//        if(user != null){
//            return new ResponseEntity<>(userService.fetchAUser(id),HttpStatus.OK);
//        }
//        else{
//            return new ResponseEntity<>(userService.fetchAUser(id),HttpStatus.BAD_REQUEST);
//        }

        return userService.fetchAUser(id).map(ResponseEntity ::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<String> createAllUser(@RequestBody UserRequest userRequest)
    {
        userService.addUser(userRequest);
        return new ResponseEntity<>("List Added Successfully",HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody UserRequest updatedUserRequest)
    {
        boolean update = userService.updateUser(id,updatedUserRequest);

        if(update){
            return new ResponseEntity<>("User Updated Successfully",HttpStatus.OK);

        }
        else {
            return new ResponseEntity<>("User Updated UnSuccessfully",HttpStatus.NOT_FOUND);
        }

    }




}
