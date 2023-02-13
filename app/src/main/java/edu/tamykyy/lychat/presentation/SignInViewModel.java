package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.PhoneAuthCredential;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.SignInWithCredentialResultModel;
import edu.tamykyy.lychat.domain.usecase.CreateCredentialUseCase;
import edu.tamykyy.lychat.domain.usecase.SignInWithCredentialUseCase;

@HiltViewModel
public class SignInViewModel extends ViewModel {

    private final CreateCredentialUseCase createCredentialUseCase;
    private final SignInWithCredentialUseCase signInWithCredentialUseCase;
    private final MutableLiveData<PhoneAuthCredential> credentialLiveData = new MutableLiveData<>();
    private final LiveData<SignInWithCredentialResultModel> signInWithCredentialResultLiveData;

    @Inject
    public SignInViewModel(CreateCredentialUseCase createCredentialUseCase,
                           SignInWithCredentialUseCase signInWithCredentialUseCase) {
        this.createCredentialUseCase = createCredentialUseCase;
        this.signInWithCredentialUseCase = signInWithCredentialUseCase;
        this.signInWithCredentialResultLiveData = signInWithCredentialUseCase.getSignInWithCredentialResultLiveData();
    }

    public void createCredential(String verificationId, String code) {
        PhoneAuthCredential credential = createCredentialUseCase.execute(verificationId, code);
        credentialLiveData.setValue(credential);
    }

    public void signInWithCredential(PhoneAuthCredential credential) {
        signInWithCredentialUseCase.execute(credential);
    }

    public LiveData<PhoneAuthCredential> getCredentialLiveData() {
        return credentialLiveData;
    }

    public LiveData<SignInWithCredentialResultModel> getSignInWithCredentialResultLiveData() {
        return signInWithCredentialResultLiveData;
    }
}
