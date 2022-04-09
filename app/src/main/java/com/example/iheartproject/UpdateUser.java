package com.example.iheartproject;

// TODO: this is a simple class for user, u gotta add ur hospital info into it
// I have moved this user out as a single class, easier for any java activity to access it
// If u follow the steps in usersignupactivity on parsing the value to object, u gotta go to proguard-rules.pro, and add ur class there
// https://stackoverflow.com/questions/41650103/no-setter-field-for-found-android-firebase
public class UpdateUser {
    public String UserID;
    public String updateFullName;
    public String updateDonorUserName;
    public String updateEmail;
    public String HospitalName;
    public boolean isHospital = false;
    public String FullName;

    public UpdateUser() {

    }

    public UpdateUser(String UserID, String updateFullName, String updateDonorUserName, String updateEmail, String HospitalName, boolean isHospital) {
        this.UserID = UserID;
        this.updateFullName = updateFullName;
        this.updateDonorUserName = updateDonorUserName;
        this.updateEmail = updateEmail;
        this.HospitalName = HospitalName;
        this.isHospital = isHospital;
    }
}
