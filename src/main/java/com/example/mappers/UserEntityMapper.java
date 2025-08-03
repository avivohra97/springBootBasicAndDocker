package com.example.mappers;

import com.example.data.UserEntity;
import com.example.model.userModel;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper implements EntityMapper<UserEntity, userModel> {
   public UserEntityMapper(){

   }

    @Override
    public userModel map(UserEntity userEntity){
        if (userEntity == null) {
            return null;
        }

        return new userModel(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getMemberShipId(),
                userEntity.getCreateTime()
        );
    }

    @Override
    public UserEntity reverseMap(userModel userModel){
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userModel.getFirstName());
        userEntity.setLastName(userModel.getLastName());
        userEntity.setMemberShipId(userModel.getMemberShipId());
        userEntity.setCreateTime(userModel.getCreateTime());
        return userEntity;
    }


}
