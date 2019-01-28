package com.example.jmirza.firebaseauth.models;

public class Personnel {

    public String key,name,gender,phone,email,password,occupation,deviceToken;

    public Personnel(){

    }
    public Personnel(String name, String gender, String phone, String email, String password, String occupation) {
        this.occupation=occupation;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.password=password;

    }
    public Personnel(String name, String gender, String phone, String email, String password, String occupation,String deviceToken) {
        this.occupation=occupation;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.password=password;
        this.deviceToken=deviceToken;
    }

    public Personnel(String key,String name, String gender, String phone, String email, String password, String occupation,String deviceToken) {
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