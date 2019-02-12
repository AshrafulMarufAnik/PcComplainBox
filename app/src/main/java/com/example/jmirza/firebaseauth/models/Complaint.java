package com.example.jmirza.firebaseauth.models;

public class Complaint {
    public String complainUserId;
    public String complainUserName;
    public String complainUserDept;
    public String pcNumber;
    public String roomNo;
    public String description;
    public String complainStatus;
    public String complainDate;

    public Complaint() {

    }

    public Complaint(String complainUserId,String complainUserName,String complainUserDept,String pcNumber,String roomNo,String description,String complainStatus,String complainDate) {
        this.complainUserId = complainUserId;
        this.complainUserName = complainUserName;
        this.complainUserDept = complainUserDept;
        this.pcNumber = pcNumber;
        this.roomNo = roomNo;
        this.description = description;
        this.complainStatus=complainStatus;
        this.complainDate=complainDate;

    }


}
