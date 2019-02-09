package com.example.jmirza.firebaseauth.models;

public class Student {

   public String key,name,department,phone,email,password,occupation,deviceToken;

    public Student(){

    }


    public Student(String key,String name, String department, String phone, String email, String password, String occupation, String deviceToken) {
        this.key = key;
        this.occupation=occupation;
        this.name = name;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.password=password;
        this.deviceToken=deviceToken;
    }
}
