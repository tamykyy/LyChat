package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.SignInWithCredentialResultModel;
import edu.tamykyy.lychat.domain.usecase.SignInWithPhoneUseCase;

@HiltViewModel
public class SignInViewModel extends ViewModel {

    private final SignInWithPhoneUseCase signInWithPhoneUseCase;

    @Inject
    public SignInViewModel(SignInWithPhoneUseCase signInWithPhoneUseCase) {
        this.signInWithPhoneUseCase = signInWithPhoneUseCase;
    }

    public LiveData<SignInWithCredentialResultModel> signIn(String verificationId, String code) {
        return signInWithPhoneUseCase.execute(verificationId, code);
    }

}
