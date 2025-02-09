package com.translate.service;

import com.translate.domain.User;
import com.translate.domain.response.user.ResCreateUserDTO;
import com.translate.domain.response.user.ResUpdateUserDTO;
import com.translate.domain.response.user.ResUserDTO;
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

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
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
            currentUser.setPhone(user.getPhone());
            currentUser.setAge(user.getAge());
            currentUser.setAddress(user.getAddress());
            currentUser = userRepository.save(currentUser);
        }
        return currentUser;
    }

    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    public User handleGetUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User user){
        ResCreateUserDTO res = new ResCreateUserDTO();

        res.setId(user.getId());
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setPhone(user.getPhone());
        res.setAge(user.getAge());
        res.setAddress(user.getAddress());

        return res;
    }

    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO res = new ResUserDTO();

        res.setId(user.getId());
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setPhone(user.getPhone());
        res.setAge(user.getAge());
        res.setAddress(user.getAddress());

        return res;
    }

    public ResUpdateUserDTO convertToResUpdateUserDTO(User user){
        ResUpdateUserDTO res = new ResUpdateUserDTO();

        res.setId(user.getId());
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setPhone(user.getPhone());
        res.setAge(user.getAge());
        res.setAddress(user.getAddress());

        return res;
    }

    public void updateUserToken(String token, String email) {
        User currentUser = this.handleGetUserByUsername(email);
        if(currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }

    public User getUserByRefreshTokenAndEmail(String token, String email) {
        return this.userRepository.findByRefreshTokenAndEmail(token, email);
    }
}
