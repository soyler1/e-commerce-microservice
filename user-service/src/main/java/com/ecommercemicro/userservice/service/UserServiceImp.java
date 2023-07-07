package com.ecommercemicro.userservice.service;

import com.ecommercemicro.userservice.data.entities.Address;
import com.ecommercemicro.userservice.dto.AddressRequest;
import com.ecommercemicro.userservice.dto.UserCreateRequest;
import com.ecommercemicro.userservice.data.entities.Users;
import com.ecommercemicro.userservice.data.repository.UserRepository;
import com.ecommercemicro.userservice.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    @Override
    public String createUser(UserCreateRequest serviceInput) {
        Users user = mapToDto(serviceInput);
        userRepository.save(user);
        return "User created successfully";
    }

    @Override
    public Long login(String email, String password) {
        Optional<Users> optionalUser = userRepository.findByMailAddress(email);
        if (optionalUser.isPresent()){
            Users user = optionalUser.get();
            if (user.getPassword().equals(password))
                return user.getId();
            return -1L;
        }
        return -1L;
    }

    @Override
    public String addAddress(AddressRequest addressRequest) {
        Optional<Users> optionalUser = userRepository.findById(addressRequest.getUserId());
        if (optionalUser.isPresent()){
            Users users = optionalUser.get();
            List<Address> addressesOfUser = users.getAddresses();
            Address address = mapToDto(addressRequest);
            addressesOfUser.add(address);
            users.setAddresses(addressesOfUser);
            userRepository.save(users);
            return "Address added succesfully";
        }
        return "User does not exists.";
    }

    @Override
    public UserResponse findUserById(Long id) {
        Optional<Users> optionalUsers = userRepository.findById(id);
        if (optionalUsers.isPresent()){
            Users users = optionalUsers.get();
            return mapToDto(users);
        }
        return null;
    }

    private Users mapToDto(UserCreateRequest userCreateRequest){
        Users user = new Users();
        user.setMailAddress(userCreateRequest.getMailAddress());
        user.setPassword(userCreateRequest.getPassword());
        user.setName(userCreateRequest.getName());
        user.setLastName(userCreateRequest.getLastName());
        user.setPhoneNumber(userCreateRequest.getPhoneNumber());
        return user;
    }

    private Address mapToDto(AddressRequest addressRequest){
        Address address = new Address();
        address.setAddressBody(addressRequest.getAddressBody());
        address.setZipCode(addressRequest.getZipCode());
        address.setDistrict(addressRequest.getDistrict());
        address.setCity(addressRequest.getCity());
        address.setCountry(addressRequest.getCountry());
        return address;
    }

    private UserResponse mapToDto(Users users){
        return UserResponse.builder()
                .id(users.getId())
                .name(users.getName())
                .lastName(users.getLastName())
                .mailAddress(users.getMailAddress())
                .phoneNumber(users.getPhoneNumber())
                .addresses(users.getAddresses()).build();
    }
}
