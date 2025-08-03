package com.example.services;

import com.example.data.UserRepository;
import com.example.model.userModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;

//@Service("primary")
public class UserServiceImpl implements UserService {
    // This is supposed to do logic like calling services or computation
    private HashMap<String, userModel> userMap = new HashMap<>();
    private final TimeAPIService timeService;

    public UserServiceImpl(TimeAPIService timeService){
        this.timeService = timeService;
        userMap.put("avi",new userModel("avi","vohra",125789));
        userMap.put("ravi",new userModel("ravi","kohra",125739));
    }


    public userModel getUser(){
        return new userModel("avi","vohra",125788);
    }

    public userModel getUserByUsername(String username){
        return userMap.get(username);
    }

    public void postUserByUsername(userModel user){
        String currentTime = timeService.getCurrentTime("Amsterdam");

        // Check if the time service returned a valid time before setting it.
        if (currentTime != null) {
            user.setCreateTime(currentTime);
        } else {
            // Log a warning or handle the case where the time service failed.
            System.err.println("Failed to get current time. User will be saved without a timestamp.");
        }

        userMap.put(user.getFirstName(), user);
    }

    public void deleteUser(String username){
        userMap.remove(username);
    }

}
