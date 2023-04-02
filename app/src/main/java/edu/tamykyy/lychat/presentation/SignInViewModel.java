package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.ViewModel;


import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.usecase.SignInWithPhoneUseCase;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class SignInViewModel extends ViewModel {

    private final SignInWithPhoneUseCase signInWithPhoneUseCase;

    @Inject
    public SignInViewModel(SignInWithPhoneUseCase signInWithPhoneUseCase) {
        this.signInWithPhoneUseCase = signInWithPhoneUseCase;
    }

    public Single<Boolean> signIn(String verificationId, String code) {
        return signInWithPhoneUseCase.execute(verificationId, code);
    }

}
