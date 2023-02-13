package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;


import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivityAuthenticationBinding;

@AndroidEntryPoint
public class AuthenticationActivity extends AppCompatActivity {

    private static final String VERIFICATION_ID_KEY = "verificationId";
    private ActivityAuthenticationBinding myBinding;
    private AuthenticationViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);
        myViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);

        CountryCodePicker countryCodePicker = myBinding.countryCodePicker;
        EditText phoneEditText = myBinding.editTextPhone;
        countryCodePicker.registerCarrierNumberEditText(phoneEditText);

        myBinding.signInButton.setOnClickListener(v -> {
            if (countryCodePicker.isValidFullNumber()) {
                myViewModel.sendVerificationCode(countryCodePicker.getFullNumberWithPlus(), AuthenticationActivity.this);

                myViewModel.getVerificationResultModel().observe(AuthenticationActivity.this, verificationResult -> {
                    if (verificationResult.isCodeSent())
                        startActivity(new Intent(AuthenticationActivity.this, SignInActivity.class)
                                .putExtra(VERIFICATION_ID_KEY , verificationResult.getVerificationId()));
                    else if (verificationResult.isVerificationComplete()) {
                        // TODO chats intent
                    } else
                        phoneEditText.setError(verificationResult.getMessage());
                });
            } else {
                phoneEditText.setError("Phone number isn't valid");
            }
        });
    }
}