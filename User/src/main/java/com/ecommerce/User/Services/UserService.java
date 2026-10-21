package com.ecommerce.User.Services;


import com.ecommerce.User.Modals.Address;
import com.ecommerce.User.Modals.User;
import com.ecommerce.User.Repositories.UserRepository;
import com.ecommerce.User.dto.AddressDTO;
import com.ecommerce.User.dto.UserRequest;
import com.ecommerce.User.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private Long userId = 1L;

    public List<UserResponse> fetchAllUser(){
        //return userRepository.findAll();
        return userRepository.findAll()
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest){
        User user = new User();
        updateUserFromRequest(user,userRequest);
        userRepository.save(user);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        if (userRequest.getAddress() != null) {
            Address address = new Address();

            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());

            user.setAddress(address);
        }


    }

    public Optional<UserResponse> fetchAUser (Long id){

        return userRepository.findById(id)
                .map(this :: mapToUserResponse);
         //return userList.stream().filter(user -> user.getId().equals(id)).findFirst();
//        for(User user : userList)
//        {
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
//
//        return null;

    }

    public boolean updateUser(Long id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                })
                .orElse(false);
    }


    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();

        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();

            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());

            response.setAddress(addressDTO);
        }

        return response;
    }



}
