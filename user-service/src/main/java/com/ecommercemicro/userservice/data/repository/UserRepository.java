package com.ecommercemicro.userservice.data.repository;

import com.ecommercemicro.userservice.data.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByMailAddress(String mailAddress);
}
