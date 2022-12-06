package com.example.demo.service;

import com.example.demo.common.Response;
import com.example.demo.entity.User;

public interface UserService {
    Response<?> login(User user);

    Response<?> register(User user);

    Response<?> getUserById(Integer id);
}
