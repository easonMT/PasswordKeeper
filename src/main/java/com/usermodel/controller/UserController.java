package com.usermodel.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.usermodel.service.UserService;
// import org.springframework.web.bind.annotation.CrossOrigin;

import com.usermodel.model.UserModel;
import com.usermodel.repository.UserRepository;

// @CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usermodel")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIndieUser(@PathVariable long id) {
        Optional<UserModel> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with ID: " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new")
    public UserModel addNewUser(@RequestBody UserModel newUserModel) {
        return userRepository.save(newUserModel);
    }

    @PutMapping("updatePassword/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable long id, @RequestBody UserModel updatedPassword) {
        Optional<UserModel> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            user.setPassword(updatedPassword.getPassword());

            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with ID: " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}") 
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return new ResponseEntity<>("User successfully deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with ID: " + id + " was not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel newUserModel) {
        if(userRepository.existsByEmail(newUserModel.getEmail())) {
            return new ResponseEntity<>("Email is already registered.", HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = userService.hashPassword(newUserModel.getPassword());
        newUserModel.setHashedPassword(hashedPassword);

        userRepository.save(newUserModel);
        return new ResponseEntity<>(newUserModel, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel loginRequest) {
        Optional<UserModel> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        
        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
    
            // Check if the provided password matches the hashed password in the database
            if (userService.verifyPassword(loginRequest.getPassword(), user.getHashedPassword())) {
                return new ResponseEntity<>("Login successful.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Incorrect password.", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
    }

    
    

}
