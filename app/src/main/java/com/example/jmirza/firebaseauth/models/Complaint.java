package com.example.jmirza.firebaseauth.models;

public class Complaint {
    public String complainId;
    public String complainUserId;
    public String complainUserName;
    public String complainUserDept;
    public String complainUserDeviceToken;
    public String deviceNumber;
    public String roomNo;
    public String description;
    public String complainStatus;
    public String complainNote;
    public String complainDate;

    public Complaint() {

    }

    public Complaint(String complainId, String complainUserId, String complainUserName, String complainUserDept, String complainUserDeviceToken, String deviceNumber, String roomNo, String description, String complainStatus, String complainDate, String complainNote) {
        this.complainId = complainId;
        this.complainUserId = complainUserId;
        this.complainUserName = complainUserName;
        this.complainUserDept = complainUserDept;
        this.complainUserDeviceToken = complainUserDeviceToken;
        this.deviceNumber = deviceNumber;
        this.roomNo = roomNo;
        this.description = description;
        this.complainStatus = complainStatus;
        this.complainDate = complainDate;
        this.complainNote = complainNote;

    }


}
