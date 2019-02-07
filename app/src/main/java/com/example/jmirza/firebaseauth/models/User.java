package com.example.jmirza.firebaseauth.models;

public class User {

    public String key,name,gender,phone,email,password,occupation,deviceToken;


    public User() {
    }

    public User(String key,String name, String gender, String phone, String email, String password, String occupation, String deviceToken) {
        this.key = key;
        this.occupation=occupation;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.password=password;
        this.deviceToken=deviceToken;
    }
}
