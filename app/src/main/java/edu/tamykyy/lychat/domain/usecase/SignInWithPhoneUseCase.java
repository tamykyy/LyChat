package edu.tamykyy.lychat.domain.usecase;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.PhoneAuthCredential;

import edu.tamykyy.lychat.domain.models.SignInWithCredentialResultModel;
import edu.tamykyy.lychat.domain.repository.AuthenticationRepository;

public class SignInWithPhoneUseCase {

    private final AuthenticationRepository authenticationRepository;

    public SignInWithPhoneUseCase(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public LiveData<SignInWithCredentialResultModel> execute(String verificationId, String code) {
        PhoneAuthCredential credential = authenticationRepository.getCredential(verificationId, code);
        return authenticationRepository.signIn(credential);
    }
}
