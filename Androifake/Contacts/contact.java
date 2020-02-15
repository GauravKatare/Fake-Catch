package com.example.android.greetup.Contacts;

/**
 * Created by raj garg on 13-02-2020.
 */

public class contact {
    String name;
    String phoneNo;

    public contact(String name, String phoneNo) {
        this.name = name;
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
