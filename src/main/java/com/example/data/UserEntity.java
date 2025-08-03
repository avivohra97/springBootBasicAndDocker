package com.example.data;



import jakarta.persistence.*;

import java.io.Serializable;


// Serializable as this needs to match db
@Entity
@Table(name="user_entity")
public class UserEntity implements Serializable {

    // A unique primary key for the database table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Use @Column to map Java camelCase field names to database snake_case column names
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "membership_id")
    private int memberShipId;

    @Column(name = "creation_time")
    private String createTime;

    // A constructor for the mapper, excluding the ID
    public UserEntity(String firstName, String lastName, int memberShipId, String createTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberShipId = memberShipId;
        this.createTime = createTime;
    }
    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, int memberShipId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberShipId = memberShipId;
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
