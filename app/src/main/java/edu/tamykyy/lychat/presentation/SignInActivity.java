package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.Map;

import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivitySignInBinding;

@AndroidEntryPoint
public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding myBinding;
    private SignInViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        myViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

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
                    myViewModel.createPhoneAuthCredential(code.toString());
                    myViewModel.getSignInResultWithMessage()
                            .observe(SignInActivity.this, signInResultWithMessage -> {
                                if (signInResultWithMessage.getKey()) {
                                    // TODO
                                    // new Intent
                                } else {
                                    myBinding.codeEditText.setError(signInResultWithMessage.getValue());
                                }
                            });
                }
            }
        });
    }
}