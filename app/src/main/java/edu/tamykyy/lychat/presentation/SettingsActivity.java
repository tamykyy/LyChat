package edu.tamykyy.lychat.presentation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.stfalcon.imageviewer.StfalconImageViewer;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

import android.widget.Toast;

import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivitySettingsBinding;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class SettingsActivity extends AppCompatActivity {

    private static final Uri DEFAULT_IMAGE_URI = Uri.parse("https://firebasestorage.googleapis.com/v0/b/lychat-me.appspot.com/o/profilePictures%2Fdefault_img.png?alt=media&token=d213a51d-d799-48f0-af7b-fa3ca826f20c");
    private ActivitySettingsBinding myBinding;
    private SettingsActivityViewModel myViewModel;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UserDomainModel userProfile;

    private static final int REQUEST_CODE = 123;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        myViewModel = new ViewModelProvider(this).get(SettingsActivityViewModel.class);

        userProfile = (UserDomainModel) getIntent().getExtras().get("userProfile");
//        updateUI(userProfile);

        Disposable disposable = myViewModel.getUserProfileUpdates(userProfile.getUserUID())
                .observeOn(AndroidSchedulers.mainThread())
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
                openGalleryForResult();
            }
            return false;
        });

        myBinding.avatarImageView.setOnClickListener(view -> {
            if (userProfile.getProfilePicture().equals(DEFAULT_IMAGE_URI)) {
                return;
            }

            View profilePictureOverlay = View.inflate(this, R.layout.profile_photo_view, null);
            MaterialToolbar profilePictureOverlayToolbar = profilePictureOverlay.findViewById(R.id.toolbar);
            profilePictureOverlayToolbar.setTitle(userProfile.getFirstName() + " " + userProfile.getLastName());

            ArrayList<String> images = new ArrayList<>();
            images.add(userProfile.getProfilePicture().toString());

            StfalconImageViewer<String> viewer = new StfalconImageViewer.Builder<>(this, images, (imageView, image) ->
                    Glide.with(SettingsActivity.this).load(image).into(imageView)
            )
                    .withStartPosition(0)
                    .withOverlayView(profilePictureOverlay)
                    .withImageChangeListener(item -> Log.d("AAA", item + ""))
                    .withTransitionFrom(myBinding.avatarImageView)
                    .show();

            profilePictureOverlayToolbar.setNavigationOnClickListener(v -> viewer.close());
            profilePictureOverlayToolbar.setOnMenuItemClickListener(item -> {
                int itemId = item.getItemId();

                if (itemId == R.id.setProfilePhotoItem) {
                    viewer.close();
                    openGalleryForResult();
                    return true;
                } else if (itemId == R.id.savePhotoItem) {
                    // TODO: 06.04.2023 PERMISSIONS CHECK
//                    if (ContextCompat.checkSelfPermission(SettingsActivity.this,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    // Saving Image to Gallery
                    Disposable disposable1 = myViewModel.saveImageToGallery(
                                    myBinding.avatarImageView.getDrawable(),
                                    getContentResolver()
                            ).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> Toast.makeText(SettingsActivity.this, "Image saved", Toast.LENGTH_SHORT).show(),
                                    throwable -> Toast.makeText(SettingsActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show()
                            );
                    compositeDisposable.add(disposable1);
//                    } else {
//                        ActivityCompat.requestPermissions(SettingsActivity.this,
//                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
//                }
                    return true;
                } else if (itemId == R.id.deletePhotoItem) {
                    viewer.close();
                    Disposable disposable1 = myViewModel.deleteProfileImage()
                            .subscribe(
                                    () -> Log.d("AAA", "Image deleted"),
                                    throwable -> Log.d("AAA", throwable.getMessage())
                            );
                    compositeDisposable.add(disposable1);
                    return true;
                }
                return false;
            });
        });

        myBinding.usernameTextView.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, EditUsernameActivity.class);
            intent.putExtra("userUid", userProfile.getUserUID());
            intent.putExtra("userUsername", userProfile.getUsername());
            startActivity(intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                myViewModel.saveImageToGallery(myBinding.avatarImageView.getDrawable(), getContentResolver());
            } else {
                Toast.makeText(this, "Please provide required permission", Toast.LENGTH_LONG).show();
            }
        }
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

    private void openGalleryForResult() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        galleryResultLauncher.launch(intent);
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