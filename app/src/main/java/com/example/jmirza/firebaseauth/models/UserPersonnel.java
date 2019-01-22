package com.example.jmirza.firebaseauth.models;

public class UserPersonnel {

    public String key,name,gender,phone,email,password,occupation,messagingToken;

    public UserPersonnel(){

    }

    public UserPersonnel(String name, String gender, String phone, String email, String password, String occupation) {

        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.occupation = occupation;
    }


    public UserPersonnel(String key,String name, String gender, String phone, String email, String password, String occupation,String messagingToken) {
        this.key = key;
        this.occupation=occupation;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.password=password;
        this.messagingToken=messagingToken;
    }
}
