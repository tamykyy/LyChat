package edu.tamykyy.lychat.presentation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
    private UserDomainModel userProfile;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        myViewModel = new ViewModelProvider(this).get(SettingsActivityViewModel.class);

        userProfile = (UserDomainModel) getIntent().getExtras().get("userProfile");
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

        myBinding.topAppBar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.logoutItem) {
                showLogoutDialogAlert();
                return true;
            } else if (itemId == R.id.editNameItem) {
                startActivity(new Intent(SettingsActivity.this, EditNameActivity.class)
                        .putExtra("firstName", userProfile.getFirstName())
                        .putExtra("lastName", userProfile.getLastName()));
                return true;
            } else if (itemId == R.id.setProfilePhotoItem) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                galleryResultLauncher.launch(intent);
            }
            return false;
        });
    }

    private void updateUI(UserDomainModel userProfile) {
        Log.d("AAA", "update ui");
        this.userProfile = userProfile;

        Glide.with(this)
                .load(userProfile.getProfilePicture())
                .into(myBinding.avatarImageView);

        String nameFirstLast = userProfile.getFirstName() + " " + userProfile.getLastName();
        myBinding.phoneTextView.setText(userProfile.getPhoneNumber());
        myBinding.nameFirstLastTextView.setText(nameFirstLast);
    }

    private void showLogoutDialogAlert() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("You want to logout?")
                .setNegativeButton("Decline", (dialog, which) -> dialog.cancel())
                .setPositiveButton("Logout", (dialog, which) -> {
                    myViewModel.logout();
                    dialog.dismiss();
                    startActivity(new Intent(this, AuthenticationActivity.class));
                }).show();
    }

    private final ActivityResultLauncher<Intent> galleryResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    assert result.getData() != null;
                    Uri imageUri = result.getData().getData();
                    Disposable disposable = myViewModel.updateUserProfilePhoto(imageUri).subscribe(
                            () -> Log.d("AAA", "profile photo updated"),
                            throwable -> Log.d("AAA", throwable.getMessage())
                    );
                    myBinding.avatarImageView.setImageURI(imageUri);
                }
            }
    );

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}