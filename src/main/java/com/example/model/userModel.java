package com.example.model;

public class userModel {
    private String firstName;
    private String lastName;
    private Integer memberShipId;
    private String createTime;

    public userModel(){

    }
    public userModel(String firstName, String lastName, int memberShipId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberShipId = memberShipId;
    }

    public userModel(String firstName, String lastName, int memberShipId, String createTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberShipId = memberShipId;
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getMemberShipId() {
        return memberShipId;
    }

    public void setMemberShipId(Integer memberShipId) {
        this.memberShipId = memberShipId;
    }
}
