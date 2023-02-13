package edu.tamykyy.lychat.domain.usecase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import edu.tamykyy.lychat.domain.models.VerificationResultModel;

public class SendVerificationCodeUseCase {

    private final FirebaseAuth auth;
    private final MutableLiveData<VerificationResultModel> verificationResultLiveData = new MutableLiveData<>();

    public SendVerificationCodeUseCase(FirebaseAuth auth) {
        this.auth = auth;
    }

    public void execute(String phoneNumber, AppCompatActivity activity) {
        auth.useAppLanguage();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                    Log.d("AAA", "onVerificationCompleted:" + credential);
                    verificationResultLiveData.setValue(new VerificationResultModel(
                            false, "ok", null, credential));
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Log.w("AAA", "onVerificationFailed", e);

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        verificationResultLiveData.setValue(
                                new VerificationResultModel(true, "Phone number verification failed, number is invalid", null, null));
                        Log.d("AAA", "Phone number is invalid");
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        verificationResultLiveData.setValue(
                                new VerificationResultModel(true, "Verification failed", null, null));
                        Log.d("AAA", "Verification failed");
                    }
                    Log.d("AAA", "smth bad");
                }

                @Override
                public void onCodeSent(@NonNull String verificationId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    Log.d("AAA", "onCodeSent:" + verificationId);
                    verificationResultLiveData.setValue(
                            new VerificationResultModel(false, "ok", verificationId, null));
                }

                @Override
                public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                    super.onCodeAutoRetrievalTimeOut(s);
                }
            };

    public LiveData<VerificationResultModel> getVerificationResultLiveData() {
        return verificationResultLiveData;
    }
}
