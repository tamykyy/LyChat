package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.PhoneAuthController;

@HiltViewModel
public class SignInViewModel extends ViewModel {

    private final PhoneAuthController phoneAuthController;
    private final LiveData<Map.Entry<Boolean, String>> signInResultWithMessage;

    @Inject
    public SignInViewModel(PhoneAuthController phoneAuthController) {
        this.phoneAuthController = phoneAuthController;
        this.signInResultWithMessage = phoneAuthController.getSignInResultWithMessage();
    }

    public void createPhoneAuthCredential(String code) {
        phoneAuthController.createPhoneAuthCredential(code);
    }

    public LiveData<Map.Entry<Boolean, String>> getSignInResultWithMessage() {
        return signInResultWithMessage;
    }
}
