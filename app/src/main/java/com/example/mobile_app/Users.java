package com.example.mobile_app;

import com.google.firebase.database.DatabaseReference;

public class Users
{
    private String Name;
    private String EmailId;
    private String PhoneNumber;
    private String Gender;


    public Users (String Name, String EmailId, String PhoneNumber)
    {
        this.Name = Name;
        this.EmailId = EmailId;
        this.PhoneNumber = PhoneNumber;
        //this.Gender = Gender;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
    private DatabaseReference databaseReference;
    private String user;
    DatabaseReference userRef = databaseReference.child(user);
}
