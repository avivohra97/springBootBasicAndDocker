package com.example.services;

import com.example.model.userModel;

public interface UserService {
    public userModel getUser();
    public userModel getUserByUsername(String username);
    public void postUserByUsername(userModel userName);
    public void deleteUser(String userName);
}
