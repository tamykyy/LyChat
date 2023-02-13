package edu.tamykyy.lychat.domain.usecase;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class CreatePhoneAuthCredentialUseCase {

    public PhoneAuthCredential execute(String verificationId, String code) {
        return PhoneAuthProvider.getCredential(verificationId, code);
    }
}
