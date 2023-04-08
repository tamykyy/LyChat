package edu.tamykyy.lychat.domain.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class UserDomainModel implements Parcelable {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String email;
    private String password;
    private Uri profilePicture;
    private String userUID;
    private String onlineInfo;

    public UserDomainModel() {
    }

    public UserDomainModel(String firstName, String lastName, String phoneNumber,
                           String username, String email, String password, Uri profilePicture,
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


    protected UserDomainModel(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        phoneNumber = in.readString();
        username = in.readString();
        email = in.readString();
        password = in.readString();
        profilePicture = Uri.parse(in.readString());
        userUID = in.readString();
        onlineInfo = in.readString();
    }

    public static final Creator<UserDomainModel> CREATOR = new Creator<UserDomainModel>() {
        @Override
        public UserDomainModel createFromParcel(Parcel in) {
            return new UserDomainModel(in);
        }

        @Override
        public UserDomainModel[] newArray(int size) {
            return new UserDomainModel[size];
        }
    };

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

    @Override
    public String toString() {
        return "UserDomainModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profilePicture=" + profilePicture +
                ", userUID='" + userUID + '\'' +
                ", onlineInfo='" + onlineInfo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDomainModel that = (UserDomainModel) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(profilePicture, that.profilePicture) && Objects.equals(userUID, that.userUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumber, username, email, password, profilePicture, userUID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(profilePicture.toString());
        dest.writeString(userUID);
        dest.writeString(onlineInfo);
    }
}
