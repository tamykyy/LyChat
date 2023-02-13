package edu.tamykyy.lychat.domain.usecase;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import javax.inject.Inject;

public class CreatePhoneAuthCredentialUseCase {

    public PhoneAuthCredential execute(String verificationId, String code) {
        return PhoneAuthProvider.getCredential(verificationId, code);
    }
}
