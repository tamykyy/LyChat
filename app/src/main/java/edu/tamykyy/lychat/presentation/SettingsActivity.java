package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;

import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivitySettingsBinding;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding myBinding;

    private SettingsActivityViewModel myViewModel;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        myViewModel = new ViewModelProvider(this).get(SettingsActivityViewModel.class);

        UserDomainModel userProfile = (UserDomainModel) getIntent().getExtras().get("userProfile");
//        updateUI(userProfile);

        Disposable disposable = myViewModel.getUserProfileUpdates(userProfile.getUserUID())
                .subscribe(
                        this::updateUI,
                        throwable -> Log.d("AAA", throwable.toString())
                );
        compositeDisposable.add(disposable);

        myBinding.base.setOnTouchListener(new SwipeListener(this) {
            @Override
            public void onSwipeRight() {
                finish();
            }
        });

        myBinding.topAppBar.setNavigationOnClickListener(v -> finish());
    }

    private void updateUI(UserDomainModel userProfile) {
        Log.d("AAA", "update ui");

        Glide.with(this)
                .load(userProfile.getProfilePicture())
                .into(myBinding.avatarImageView);

        String nameFirstLast = userProfile.getFirstName() + " " + userProfile.getLastName();
        myBinding.phoneTextView.setText(userProfile.getPhoneNumber());
        myBinding.nameFirstLastTextView.setText(nameFirstLast);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}