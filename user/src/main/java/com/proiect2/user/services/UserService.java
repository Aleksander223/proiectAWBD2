package com.proiect2.user.services;

import com.proiect2.user.model.User;

import java.util.List;


public interface UserService {
    User save(User user);
    List<User> findAll();
    User delete(Long id);
    User findById(Long id);
}
