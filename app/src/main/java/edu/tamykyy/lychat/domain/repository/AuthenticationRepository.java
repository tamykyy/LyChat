package edu.tamykyy.lychat.domain.repository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.PhoneAuthCredential;

import edu.tamykyy.lychat.domain.models.SignInWithCredentialResultModel;
import edu.tamykyy.lychat.domain.models.VerificationResultModel;

public interface AuthenticationRepository {

    boolean isUserAuthenticatedInFirebase();

//    boolean getFirebaseAuthState();

    LiveData<VerificationResultModel> firebaseSendVerificationCode(String phoneNumber, AppCompatActivity activity);

    PhoneAuthCredential getCredential(String verificationId, String code);

    LiveData<SignInWithCredentialResultModel> firebaseSignIn(PhoneAuthCredential credential);

    boolean firebaseSignOut();

}
