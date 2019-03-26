package com.example.jmirza.firebaseauth.models;

public class User {

    public String uId, name, department, phone, email, password, occupation, deviceToken, status, approval;

    public User() {

    }

    public User(String name, String department, String phone, String email, String password, String occupation) {
        this.occupation = occupation;
        this.name = name;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }


    public User(String uId, String name, String department, String phone, String email, String password, String occupation, String deviceToken, String status, String approval) {
        this.uId = uId;
        this.name = name;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.occupation = occupation;
        this.deviceToken = deviceToken;
        this.status = status;
        this.approval = approval;
    }
}
