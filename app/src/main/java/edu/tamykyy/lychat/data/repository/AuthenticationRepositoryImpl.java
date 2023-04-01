package edu.tamykyy.lychat.data.repository;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.JdkFutureAdapters;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.models.SignInWithCredentialResultModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
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
    public LiveData<SignInWithCredentialResultModel> signIn(PhoneAuthCredential credential) {
        MutableLiveData<SignInWithCredentialResultModel> result = new MutableLiveData<>();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//                         Sign in success, update UI with the signed-in user's information
                        Log.d("AAA", "signInWithCredential:success");
                        String uid = task.getResult().getUser().getUid();
                        UserDomainModel userDomainModel = new UserDomainModel();
                        userDomainModel.setUserUID(uid);

                        if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                            userRepository.save(userDomainModel);
                            result.setValue(
                                    new SignInWithCredentialResultModel(true, true, "success: new user"));
                        } else {
                            result.setValue(
                                    new SignInWithCredentialResultModel(true, false, "success: old user"));
                        }
//)
//                        userRepository.contains(uid).subscribe(isNewUser -> {
//                            result.setValue(new SignInWithCredentialResultModel(true, isNewUser, "ok"));
//                            Log.d("AAA", isNewUser +"");
//                        });
                    } else {
//                         Sign in failed, display a message and update the UI
                        Log.w("AAA", "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            result.setValue(
                                    new SignInWithCredentialResultModel(false, false,
                                            "The verification code entered was invalid"));
                        }
                    }
                });
        return result;
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
