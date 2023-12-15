package com.example.b2chat.service.impl;

import com.example.b2chat.entity.User;
import com.example.b2chat.excepciones.BusinessUserException;
import com.example.b2chat.repository.UserRepository;
import com.example.b2chat.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;


    //TODO try catch errores

    public User createUser(User request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail()).build();

        return userRepository.save(user);
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(BusinessUserException.Type.USER_NOT_FOUND::build);
    }

    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(BusinessUserException.Type.USER_NOT_FOUND::build);

        return userRepository.save(buildUser(userId, existingUser, updatedUser));
    }

    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(BusinessUserException.Type.USER_NOT_FOUND::build);

        userRepository.deleteById(userId);
    }


    private User buildUser(Long userId, User existingUser, User updatedUser) {
        existingUser.setId(userId);
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());

        return existingUser;
    }
}
