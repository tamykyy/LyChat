package edu.tamykyy.lychat.domain.usecase;

import com.google.firebase.auth.PhoneAuthCredential;

import edu.tamykyy.lychat.domain.repository.AuthenticationRepository;
import io.reactivex.rxjava3.core.Single;

public class SignInWithPhoneUseCase {

    private final AuthenticationRepository authenticationRepository;

    public SignInWithPhoneUseCase(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public Single<Boolean> execute(String verificationId, String code) {
        PhoneAuthCredential credential = authenticationRepository.getCredential(verificationId, code);
        return authenticationRepository.signIn(credential);
    }
}
