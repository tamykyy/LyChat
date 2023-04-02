package edu.tamykyy.lychat.domain.models;

import android.net.Uri;

public class UserDomainModel {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Uri profilePicture;
    private String userUID;

    public UserDomainModel() {
    }

    public UserDomainModel(String firstName, String lastName, String phoneNumber,
                           String email, String password, Uri profilePicture,
                           String userUID
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
        this.userUID = userUID;
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

    public Uri getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Uri profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    @Override
    public String toString() {
        return "UserDomainModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profilePicture=" + profilePicture +
                ", userUID='" + userUID + '\'' +
                '}';
    }
}
