package edu.tamykyy.lychat.data.storage.models;

public class UserDataModel {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String email;
    private String password;
    private String profilePicture;
    private String userUID;

    private String onlineInfo;

    public UserDataModel() {
    }

    public UserDataModel(String firstName, String lastName, String phoneNumber,
                         String username, String email, String password, String profilePicture,
                         String userUID,
                         String onlineInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
        this.userUID = userUID;
        this.onlineInfo = onlineInfo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOnlineInfo() {
        return onlineInfo;
    }

    public void setOnlineInfo(String onlineInfo) {
        this.onlineInfo = onlineInfo;
    }
}
