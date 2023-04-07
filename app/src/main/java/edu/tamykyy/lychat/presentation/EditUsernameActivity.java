package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivityEditUsernameBinding;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class EditUsernameActivity extends AppCompatActivity {

    private ActivityEditUsernameBinding myBinding;
    private EditUsernameActivityViewModel myViewModel;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable usernameAvailabilityDisposable = Disposable.disposed();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_username);
        myViewModel = new ViewModelProvider(this).get(EditUsernameActivityViewModel.class);

        String userUid = getIntent().getExtras().getString("userUid");
        String userUsername = getIntent().getExtras().getString("userUsername");

        myBinding.usernameEditText.setText(userUsername);

        myBinding.toolbar.setNavigationOnClickListener(v -> finish());

        myBinding.toolbar.setOnMenuItemClickListener(item -> {
            if (myBinding.usernameEditText.getText().toString().equals(userUsername)) {
                Log.d("AAA", "finish 1");
                finish();
            } else {
                Disposable disposable = myViewModel.setUsername(userUid, myBinding.usernameEditText.getText().toString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::finish,
                                throwable -> Toast.makeText(EditUsernameActivity.this,
                                        throwable.getMessage(), Toast.LENGTH_LONG).show()
                        );

                compositeDisposable.add(disposable);
            }
            return true;
        });

        myBinding.usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                compositeDisposable.remove(usernameAvailabilityDisposable);
                Log.d("AAA", "username check");

                if (s.toString().isEmpty()) {
                    setAppleEnabled();
                    return;
                }
                if (s.toString().equals(userUsername)) {
                    setAppleEnabled();
                    return;
                } else {
                    myBinding.toolbar.getMenu().findItem(R.id.applyItem).setEnabled(false);
                }
                usernameAvailabilityDisposable = myViewModel.checkUsernameAvailability(s.toString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isContains -> {
                                    Log.d("AAA", "ok");
                                    if (isContains) {
                                        setAppleDisabled();
                                        myBinding.usernameEditText.setError("This username is already taken");
                                    } else {
                                        setAppleEnabled();
                                        Log.d("AAA", "username is free");
                                        // TODO: 07.04.2023 tell user its okay
                                    }
                                },
                                throwable -> Toast.makeText(EditUsernameActivity.this,
                                        throwable.getMessage(), Toast.LENGTH_LONG).show()
                        );
                compositeDisposable.add(usernameAvailabilityDisposable);
            }
        });
    }

    private void setAppleEnabled() {
        myBinding.toolbar.getMenu().findItem(R.id.applyItem).setEnabled(true);
        myBinding.toolbar.getMenu().findItem(R.id.applyItem).setIcon(R.drawable.baseline_check_white_24);
    }

    private void setAppleDisabled() {
        myBinding.toolbar.getMenu().findItem(R.id.applyItem).setEnabled(false);
        myBinding.toolbar.getMenu().findItem(R.id.applyItem).setIcon(R.drawable.baseline_check_black_24);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}