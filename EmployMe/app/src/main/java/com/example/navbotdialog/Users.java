package com.example.navbotdialog;

public class Users {
    String userID,Name, Profile;

    public Users(String _userID, String _name, String _profile){
        this.userID=_userID;
        this.Name=_name;
        this.Profile=_profile;
    }
    public Users(){
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }
}
