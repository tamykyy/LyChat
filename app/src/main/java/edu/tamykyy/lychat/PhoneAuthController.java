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
    private final MutableLiveData<Map.Entry<Boolean, String>> signInResultWithMessage = new MutableLiveData<>();

    @Inject
    public PhoneAuthController(FirebaseAuth auth) {
        this.auth = auth;
    }

    public LiveData<Map.Entry<Boolean, String>> getSignInResultWithMessage() {
        return signInResultWithMessage;
    }
}
