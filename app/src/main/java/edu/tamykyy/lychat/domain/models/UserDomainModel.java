package edu.tamykyy.lychat.domain.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UserDomainModel implements Parcelable {

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

    protected UserDomainModel(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        password = in.readString();
        profilePicture = Uri.parse(in.readString());
        userUID = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(profilePicture.toString());
        dest.writeString(userUID);
    }
}
