package com.example.logindemo;

public class UserProfile {
    public String userAge;
    public String userEmail;
    public String userName;


    public UserProfile(){

    }

    public UserProfile(String userAge, String userEmail, String userName) {
        this.userAge = userAge;
        this.userEmail = userEmail;
        this.userName = userName;
    }


    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
/*public class userProfile2 {
    public String userAge2;
    public String userEmail2;
    public String userName2;
    public String userBalance;

    public userProfile2() {

    }

    public UserProfile2(String userAge2, String userEmail2, String userName2, String userBalance) {
        this.userAge2 = userAge2;
        this.userEmail2 = userEmail2;
        this.userName2 = userName2;
        this.userBalance = userBalance;
    }
}*/