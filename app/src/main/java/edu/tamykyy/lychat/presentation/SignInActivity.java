package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivitySignInBinding;

@AndroidEntryPoint
public class SignInActivity extends AppCompatActivity {

    private static final String VERIFICATION_ID_KEY = "verificationId";
    private ActivitySignInBinding myBinding;
    private SignInViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        myViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        String verificationId = getIntent().getStringExtra(VERIFICATION_ID_KEY);

        myBinding.codeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable code) {
                if (code.length() == 6) {
                    myViewModel.createCredential(verificationId, code.toString());

                    myViewModel.getCredentialLiveData().observe(SignInActivity.this, credential -> {
                        myViewModel.signInWithCredential(credential);
                    });

                    myViewModel.getSignInWithCredentialResultLiveData().observe(SignInActivity.this,
                            signInWithCredentialResultModel -> {
                                if (signInWithCredentialResultModel.isSignInWithCredentialSuccess()) {
                                    if (signInWithCredentialResultModel.isNewUserSignIn()) {
                                        //TODO registration intent
                                        Log.d("AAA", "New user registration!!!!");
                                    } else {
                                        // TODO chats intent
                                        Log.d("AAA", "User login!!!!");
                                    }
                                } else
                                    myBinding.codeEditText.setError(signInWithCredentialResultModel.getMessage());
                            }
                    );
                }
            }
        });
    }
}