package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.PhoneAuthController;

@HiltViewModel
public class AuthenticationViewModel extends ViewModel {

    private final PhoneAuthController phoneAuthController;
    private final LiveData<Map.Entry<Boolean, String>> verificationResultWithMessage;

    @Inject
    public AuthenticationViewModel(PhoneAuthController phoneAuthController) {
        this.phoneAuthController = phoneAuthController;
        this.verificationResultWithMessage = phoneAuthController.getVerificationResultWithMessage();
    }

    protected void sendVerifyingNumber(String phoneNumber, AuthenticationActivity activity) {
        phoneAuthController.sendVerifyingNumber(phoneNumber, activity);
    }

    public LiveData<Map.Entry<Boolean, String>> getVerificationResultWithMessage() {
        return verificationResultWithMessage;
    }
}
