package com.example.services;

import com.example.data.UserEntity;
import com.example.data.UserRepository;
import com.example.mappers.EntityMapper;
import com.example.model.userModel;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("primary")
public class UserServiceImplDB implements UserService {
    // This is supposed to do logic like calling services or computation
    private HashMap<String, userModel> userMap = new HashMap<>();
    private final TimeAPIService timeService;
    private UserRepository userRepository;
    private EntityMapper<UserEntity, userModel> entityMapper;

    public UserServiceImplDB(TimeAPIService timeService, UserRepository userRepository, EntityMapper<UserEntity, userModel> entityMapper){
        this.timeService = timeService;
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
    }


    public userModel getUser(){
        return new userModel("avi","vohra",125788);
    }

    public userModel getUserByUsername(String username){
        return entityMapper.map(userRepository.findByFirstName(username));
    }

    public void postUserByUsername(userModel user){
        String currentTime = timeService.getCurrentTime("Amsterdam");

        // Check if the time service returned a valid time before setting it.
        if (currentTime != null) {
            user.setCreateTime(currentTime);
            userRepository.save(entityMapper.reverseMap(user));
        } else {

            // Log a warning or handle the case where the time service failed.
            System.err.println("Failed to get current time. User will be saved without a timestamp.");
        }

//        userMap.put(user.getFirstName(), user);
    }

    @Transactional
    public void deleteUser(String username){
        userRepository.deleteByFirstName(username);
    }

}
