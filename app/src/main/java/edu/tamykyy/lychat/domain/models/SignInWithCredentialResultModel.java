package edu.tamykyy.lychat.domain.models;

public class SignInWithCredentialResultModel {

    private final boolean isSignInWithCredentialSuccess;
    private final boolean isNewUserSignIn;
    private final String message;

    public SignInWithCredentialResultModel(boolean isSignInWithCredentialSuccess, boolean isNewUserSignIn, String message) {
        this.isSignInWithCredentialSuccess = isSignInWithCredentialSuccess;
        this.isNewUserSignIn = isNewUserSignIn;
        this.message = message;
    }

    public boolean isSignInWithCredentialSuccess() {
        return isSignInWithCredentialSuccess;
    }

    public String getMessage() {
        return message;
    }

    public boolean isNewUserSignIn() {
        return isNewUserSignIn;
    }
}
