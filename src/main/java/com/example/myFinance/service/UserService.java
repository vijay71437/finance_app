package com.example.myFinance.service;



import com.example.myFinance.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    User getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    boolean existsByEmail(String email);

    List<User> getAllUsers();

    void deleteUser(Long id);
}

