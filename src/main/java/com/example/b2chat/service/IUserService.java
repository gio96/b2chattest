package com.example.b2chat.service;

import com.example.b2chat.entity.User;

public interface IUserService {

    User createUser(User user);

    User getUserById(Long userId);

    User updateUser(Long userId,User updatedUser);

    void deleteUser(Long userId);
}
