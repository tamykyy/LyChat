package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.VerificationResultModel;
import edu.tamykyy.lychat.domain.usecase.SendVerificationCodeUseCase;

@HiltViewModel
public class AuthenticationViewModel extends ViewModel {

    private final SendVerificationCodeUseCase sendVerificationCodeUseCase;

    @Inject
    public AuthenticationViewModel(SendVerificationCodeUseCase sendVerificationCodeUseCase) {
        this.sendVerificationCodeUseCase = sendVerificationCodeUseCase;
    }

    protected LiveData<VerificationResultModel> sendVerificationCode(String phoneNumber, AuthenticationActivity activity) {
        return sendVerificationCodeUseCase.execute(phoneNumber, activity);
    }

}
