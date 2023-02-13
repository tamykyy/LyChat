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
    private final LiveData<VerificationResultModel> verificationResultLiveData;

    @Inject
    public AuthenticationViewModel(SendVerificationCodeUseCase sendVerificationCodeUseCase) {
        this.sendVerificationCodeUseCase = sendVerificationCodeUseCase;
        this.verificationResultLiveData = sendVerificationCodeUseCase.getVerificationResultLiveData();
    }

    protected void sendVerificationCode(String phoneNumber, AuthenticationActivity activity) {
        sendVerificationCodeUseCase.execute(phoneNumber, activity);
    }

    public LiveData<VerificationResultModel> getVerificationResultLiveData() {
        return verificationResultLiveData;
    }
}
