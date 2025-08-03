package com.example.controller;

import com.example.model.userModel;
import com.example.services.UserService;
import com.example.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private UserService service;
    public UserController(@Qualifier("primary") UserService service){
        this.service = service;
    }

    @GetMapping("/getUser")
    public userModel getUser(){
        return new userModel("avi","vohra",125788);
    }

    @GetMapping("/getUser/{username}")
    public ResponseEntity<userModel> getUserByUsername(@PathVariable String username){
        System.out.println("Looking for records");
        return ResponseEntity.of(Optional.ofNullable(service.getUserByUsername(username)));
    }

    @PostMapping("/addUser/{username}")
    public ResponseEntity<String> postUser(@RequestBody userModel user) {
        // Correctly get the username from the user object in the request body.
        String username = user.getFirstName(); // Assuming firstName is the username.
        ResponseEntity<userModel> userExists = getUserByUsername(String.valueOf(user));
        System.err.println(userExists+ "Status for user existence "+userExists.getStatusCode());
        // Delegate the duplicate check to a service method.
        // It's good practice to handle business logic in the service layer.
        if (userExists.getStatusCode().is2xxSuccessful()) {
            // If the user already exists, return a 409 Conflict status with a message.
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: User with username '" + username + "' already exists.");
        } else {
            // If the user does not exist, post the user.
            service.postUserByUsername(user);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("User '" + username + "' added successfully.");
        }
    }

    @DeleteMapping("/deleteUser/{username}")
    public  ResponseEntity<HttpStatus> deleteUser(@PathVariable String username){
        service.deleteUser(username);
        return ResponseEntity.accepted().build();
    }
}
