package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.PhoneAuthCredential;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.SignInWithCredentialResultModel;
import edu.tamykyy.lychat.domain.usecase.CreatePhoneAuthCredentialUseCase;
import edu.tamykyy.lychat.domain.usecase.SignInWithPhoneAuthCredentialUseCase;

@HiltViewModel
public class SignInViewModel extends ViewModel {

    private final CreatePhoneAuthCredentialUseCase createPhoneAuthCredentialUseCase;
    private final SignInWithPhoneAuthCredentialUseCase signInWithPhoneAuthCredentialUseCase;
    private final MutableLiveData<PhoneAuthCredential> credentialLiveData = new MutableLiveData<>();
    private final LiveData<SignInWithCredentialResultModel> getSignInWithCredentialResultModelLiveData;

    @Inject
    public SignInViewModel(CreatePhoneAuthCredentialUseCase createPhoneAuthCredentialUseCase,
    SignInWithPhoneAuthCredentialUseCase signInWithPhoneAuthCredentialUseCase) {
        this.createPhoneAuthCredentialUseCase = createPhoneAuthCredentialUseCase;
        this.signInWithPhoneAuthCredentialUseCase = signInWithPhoneAuthCredentialUseCase;
        this.getSignInWithCredentialResultModelLiveData = signInWithPhoneAuthCredentialUseCase.getSignInWithCredentialResultModelLiveData();
    }

    public void createPhoneAuthCredential(String verificationId, String code) {
        PhoneAuthCredential credential = createPhoneAuthCredentialUseCase.execute(verificationId, code);
        credentialLiveData.setValue(credential);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        signInWithPhoneAuthCredentialUseCase.execute(credential);
    }

    public LiveData<PhoneAuthCredential> getCredentialLiveData() {
        return credentialLiveData;
    }

    public LiveData<SignInWithCredentialResultModel> getGetSignInWithCredentialResultModelLiveData() {
        return getSignInWithCredentialResultModelLiveData;
    }
}
