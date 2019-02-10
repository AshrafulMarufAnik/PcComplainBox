package com.example.jmirza.firebaseauth.models;

public class User {

    public String key,name,department,phone,email,password,occupation,deviceToken,status,approval;

    public User(){

    }

    public User(String name, String department, String phone, String email, String password, String occupation) {
        this.occupation=occupation;
        this.name = name;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.password=password;
    }


    public User(String key,String name, String department, String phone, String email, String password, String occupation, String deviceToken, String status, String approval) {
        this.key = key;
        this.occupation=occupation;
        this.name = name;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.password=password;
        this.deviceToken=deviceToken;
        this.status=status;
        this.approval=approval;
    }
}
