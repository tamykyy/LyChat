package edu.tamykyy.lychat.domain.repository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.PhoneAuthCredential;

import edu.tamykyy.lychat.domain.models.VerificationResultModel;
import io.reactivex.rxjava3.core.Single;

public interface AuthenticationRepository {

    boolean isUserAuthenticatedInFirebase();

//    boolean getFirebaseAuthState();

    LiveData<VerificationResultModel> sendVerificationCode(String phoneNumber, AppCompatActivity activity);

    PhoneAuthCredential getCredential(String verificationId, String code);

    Single<Boolean> signIn(PhoneAuthCredential credential);

    boolean signOut();

}
