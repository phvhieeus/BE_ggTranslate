package com.translate.service;

import com.translate.domain.User;
import com.translate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public User updateUser(User user) {
        User currentUser = getUserById(user.getId());
        if(currentUser != null) {
            currentUser.setName(user.getName());
            currentUser.setEmail(user.getEmail());
            currentUser.setPassword(user.getPassword());
            currentUser = userRepository.save(currentUser);
        }
        return currentUser;
    }
}
