package edu.tamykyy.lychat.domain.usecase;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;

import java.util.AbstractMap;

import edu.tamykyy.lychat.domain.models.SignInWithCredentialResultModel;

public class SignInWithPhoneAuthCredentialUseCase {

    private final FirebaseAuth auth;
    private final MutableLiveData<SignInWithCredentialResultModel> signInWithCredentialResultModelLiveData =
            new MutableLiveData<>();

    public SignInWithPhoneAuthCredentialUseCase(FirebaseAuth auth) {
        this.auth = auth;
    }

    public void execute(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//                         Sign in success, update UI with the signed-in user's information
                        Log.d("AAA", "signInWithCredential:success");
                        boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                        signInWithCredentialResultModelLiveData.setValue(
                                new SignInWithCredentialResultModel(true, isNewUser, "ok"));

                    } else {
//                         Sign in failed, display a message and update the UI
                        Log.w("AAAA", "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            signInWithCredentialResultModelLiveData.setValue(
                                    new SignInWithCredentialResultModel(false, false,
                                            "The verification code entered was invalid"));
                        }
                    }
                });
    }

    public LiveData<SignInWithCredentialResultModel> getSignInWithCredentialResultModelLiveData() {
        return signInWithCredentialResultModelLiveData;
    }
}
