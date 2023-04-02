package edu.tamykyy.lychat.data.repository;

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

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.models.VerificationResultModel;
import edu.tamykyy.lychat.domain.repository.AuthenticationRepository;
import edu.tamykyy.lychat.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Single;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {

    private FirebaseAuth auth;
    private UserRepository userRepository;


    @Inject
    public AuthenticationRepositoryImpl(FirebaseAuth auth, UserRepository userRepository) {
        this.auth = auth;
        this.userRepository = userRepository;
    }

    @Override
    public boolean isUserAuthenticatedInFirebase() {
        return auth.getCurrentUser() != null;
    }

//    @Override
//    public boolean getFirebaseAuthState() {
//        FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
//        };
//    }

    @Override
    public LiveData<VerificationResultModel> sendVerificationCode(
            String phoneNumber, AppCompatActivity activity
    ) {
        MutableLiveData<VerificationResultModel> result = new MutableLiveData<>();
        auth.useAppLanguage();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(callbacks(result))          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        return result;
    }

    @Override
    public PhoneAuthCredential getCredential(String verificationId, String code) {
        return PhoneAuthProvider.getCredential(verificationId, code);
    }

    @Override
    public Single<Boolean> signIn(PhoneAuthCredential credential) {
        return Single.create(emitter -> auth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    if (Objects.requireNonNull(authResult.getAdditionalUserInfo()).isNewUser())
                        // TODO create user in firebase
                        // new user sign in
                        emitter.onSuccess(true);
                    else
                        // old user sign in
                        emitter.onSuccess(false);
                })
                .addOnFailureListener(emitter::onError));
    }

    @Override
    public boolean signOut() {
        return false;
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks(
            MutableLiveData<VerificationResultModel> verificationResultLiveData
    ) {
        return new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

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
                        new VerificationResultModel(false, "ok code sent", verificationId, null));
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }
        };
    }
}
