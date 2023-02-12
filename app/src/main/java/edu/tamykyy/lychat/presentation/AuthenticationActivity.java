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
                myViewModel.sendVerifyingNumber(countryCodePicker.getFullNumberWithPlus(), AuthenticationActivity.this);

                myViewModel.getVerificationResultWithMessage().observe(AuthenticationActivity.this, verificationResult -> {
                    if (verificationResult.getKey())
                        startActivity(new Intent(AuthenticationActivity.this, SignInActivity.class));
                    else
                        phoneEditText.setError(verificationResult.getValue());
                });
            } else {
                phoneEditText.setError("Phone number isn't valid");
            }
        });
    }
}