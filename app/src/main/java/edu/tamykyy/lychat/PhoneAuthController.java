package edu.tamykyy.lychat;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.tamykyy.lychat.presentation.AuthenticationActivity;

@Singleton
public class PhoneAuthController {

    private final FirebaseAuth auth;
    private String verificationId;
    private final MutableLiveData<Map.Entry<Boolean, String>> verificationResultWithMessage = new MutableLiveData<>();
    private final MutableLiveData<Map.Entry<Boolean, String>> signInResultWithMessage = new MutableLiveData<>();

    @Inject
    public PhoneAuthController(FirebaseAuth auth) {
        this.auth = auth;
    }

    public void sendVerifyingNumber(String phoneNumber, AuthenticationActivity activity) {
        auth.useAppLanguage();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void createPhoneAuthCredential(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Log.d("AAA", "signInWithCredential:success");

        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//                         Sign in success, update UI with the signed-in user's information
                        Log.d("AAA", "signInWithCredential:success");
                        Log.d("AAA", "" + task.getResult().getAdditionalUserInfo().isNewUser());
                        signInResultWithMessage.setValue(new AbstractMap.SimpleEntry<>(true, "ok"));

                    } else {
//                         Sign in failed, display a message and update the UI
                        Log.w("AAAA", "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            signInResultWithMessage.setValue(new AbstractMap.SimpleEntry<>(false,
                                    "The verification code entered was invalid"));
                        }
                    }
                });
    }

    public LiveData<Map.Entry<Boolean, String>> getVerificationResultWithMessage() {
        return verificationResultWithMessage;
    }

    public LiveData<Map.Entry<Boolean, String>> getSignInResultWithMessage() {
        return signInResultWithMessage;
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                    Log.d("AAA", "onVerificationCompleted:" + credential);
                    verificationResultWithMessage.setValue(new AbstractMap.SimpleEntry<>(true, "ok"));
                    signInWithPhoneAuthCredential(credential);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Log.w("AAA", "onVerificationFailed", e);

                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        verificationResultWithMessage.setValue(
                                new AbstractMap.SimpleEntry<>(false, "Phone number verification failed, number is invalid"));
                        Log.d("AAA", "Phone number is invalid");
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        verificationResultWithMessage.setValue(
                                new AbstractMap.SimpleEntry<>(false, "Verification failed"));
                        Log.d("AAA", "Verification failed");
                    }
                    Log.d("AAA", "smth bad");
                }

                @Override
                public void onCodeSent(@NonNull String verificationId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    Log.d("AAA", "onCodeSent:" + verificationId);
                    verificationResultWithMessage.setValue(new AbstractMap.SimpleEntry<>(true, "ok"));

                    PhoneAuthController.this.verificationId = verificationId;
//                    resendToken = token;
                }

                @Override
                public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                    super.onCodeAutoRetrievalTimeOut(s);
                }
            };
}
