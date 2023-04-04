package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivitySignInBinding;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class SignInActivity extends AppCompatActivity {

    private static final String VERIFICATION_ID_KEY = "verificationId";
    private ActivitySignInBinding myBinding;
    private SignInViewModel myViewModel;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        myViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        String verificationId = getIntent().getStringExtra(VERIFICATION_ID_KEY);

        myBinding.toolbar.setNavigationOnClickListener(v ->
                startActivity(new Intent(this, AuthenticationActivity.class)));

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
                    Single<Boolean> userSignIn = myViewModel.signIn(verificationId, code.toString());
                    Disposable disposable = userSignIn.subscribe(isNewUser -> {
                        if (isNewUser)
                            // create account intent
                            startActivity(new Intent(SignInActivity.this, CreateAccountActivity.class));
                        else
                            // open chats intent
                            startActivity(new Intent(SignInActivity.this, ChatActivity.class));
                    }, throwable -> myBinding.codeEditText.setError(throwable.getMessage()));
                    compositeDisposable.add(disposable);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}