package com.example.jmirza.firebaseauth.models;

public class Notification {

    public String senderId;
    public String title;
    public String body;


    public Notification() {
    }

    public Notification(String senderId, String title, String body) {
        this.senderId = senderId ;
        this.title = title;
        this.body = body;
    }
}
