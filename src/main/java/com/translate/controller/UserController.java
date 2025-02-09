package com.translate.controller;

import com.translate.domain.User;
import com.translate.domain.response.user.ResCreateUserDTO;
import com.translate.domain.response.user.ResUpdateUserDTO;
import com.translate.domain.response.user.ResUserDTO;
import com.translate.service.UserService;
import com.translate.util.annotation.ApiMessage;
import com.translate.util.error.IdInvalidException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    @ApiMessage("Create a new user")
    public ResponseEntity<ResCreateUserDTO> createUser(@Valid @RequestBody User user) throws IdInvalidException {
        // check email
        boolean isEmailExist = this.userService.isEmailExist(user.getEmail());
        if(isEmailExist) {
            throw new IdInvalidException(
                    "Email " + user.getEmail() + " is already in use. Please choose another one"
            );
        }

        //check phone
        String phone = user.getPhone();
        String regex = "^\\d{10}$"; // Regex chỉ cho phép 10 chữ số
        if(!phone.matches(regex)) {
            throw new IdInvalidException(
                    "Phone number " + phone + " is invalid. It should be 10 digits and not contain any letters or special characters."
            );
        }

        //check age
        int age = user.getAge();
        if(age < 0 || age > 122) {
            throw new IdInvalidException(
                    "Age " + age + " is invalid. It should be a positive number and less than 122."
            );
        }

        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        User newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(newUser));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) throws IdInvalidException {
        User currentUser = this.userService.getUserById(id);
        if(currentUser == null){
            throw new IdInvalidException("User with id = " + id + " does not exist");
        }
        this.userService.deleteUser(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/users")
    @ApiMessage("get all users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }

    @GetMapping("/users/{id}")
    @ApiMessage("get user by id")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable("id") int id) throws IdInvalidException {
        User fetchUser = this.userService.getUserById(id);
        if(fetchUser == null){
            throw new IdInvalidException("User with id = " + id + " does not exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUserDTO(fetchUser));
    }

    @PutMapping("/users")
    @ApiMessage("update a user")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User user) throws IdInvalidException {
        User updateUser = userService.updateUser(user);
        if(updateUser == null){
            throw new IdInvalidException("User with id = " + user.getId() + " does not exist");
        }

        //check phone
        String phone = user.getPhone();
        String regex = "^\\d{10}$"; // Regex chỉ cho phép 10 chữ số
        if(!phone.matches(regex)) {
            throw new IdInvalidException(
                    "Phone number " + phone + " is invalid. It should be 10 digits and not contain any letters or special characters."
            );
        }

        //check age
        int age = user.getAge();
        if(age < 0 || age > 122) {
            throw new IdInvalidException(
                    "Age " + age + " is invalid. It should be a positive number and less than 122."
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUpdateUserDTO(updateUser));
    }
}
