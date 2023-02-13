package edu.tamykyy.lychat.domain.models;

import com.google.firebase.auth.PhoneAuthCredential;

public class VerificationResultModel {

    private final boolean isVerificationFailed;
    private final String message;
    private final String verificationId;
    private final PhoneAuthCredential credential;

    public VerificationResultModel(boolean isVerificationFailed, String message, String verificationId, PhoneAuthCredential credential) {
        this.isVerificationFailed = isVerificationFailed;
        this.message = message;
        this.verificationId = verificationId;
        this.credential = credential;
    }

    public boolean isCodeSent() {
        return !verificationId.isEmpty();
    }

    public boolean isVerificationComplete() {
        return credential != null;
    }

    public boolean isVerificationFailed() {
        return isVerificationFailed;
    }

    public String getMessage() {
        return message;
    }

    public String getVerificationId() {
        return verificationId;
    }

    public PhoneAuthCredential getCredential() {
        return credential;
    }
}
