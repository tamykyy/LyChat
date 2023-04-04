package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivityChatBinding;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.usecase.LogoutUseCase;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding myBinding;
    private ChatActivityViewModel myViewModel;

    private UserDomainModel userProfile;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        myViewModel = new ViewModelProvider(this).get(ChatActivityViewModel.class);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, AuthenticationActivity.class));
        } else {
            Log.d("AAA", currentUser.getUid());
            // my view model get user profile
            Single<UserDomainModel> userProfileSingle = myViewModel.getUserProfile(currentUser);

            Disposable disposable = userProfileSingle.subscribe(
                    this::setUIUserData,
                    throwable -> Log.d("AAA", "error: " + throwable.getMessage())
            );
            compositeDisposable.add(disposable);
        }

        myBinding.drawerLayout.setOnTouchListener(new SwipeListener(this) {
            @Override
            public void onSwipeRight() {
                myBinding.drawerLayout.open();
            }

            @Override
            public void onSwipeLeft() {
                myBinding.drawerLayout.close();
            }
        });

        myBinding.toolbar.setNavigationOnClickListener(v -> myBinding.drawerLayout.open());

        myBinding.navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.item1) {
                Log.d("AAA", "settings");
            } else if (itemId == R.id.logout) {
                // Logout method
                Log.d("AAA", "logout");
                myViewModel.logout();
                startActivity(new Intent(this, AuthenticationActivity.class));
            } else {
                Log.d("AAA", "unknown");
            }

            return false;
        });
    }

    private void setUIUserData(UserDomainModel userProfile) {
        View header = myBinding.navigationView.getHeaderView(0);

        String nameFirstLast = userProfile.getFirstName() + " " + userProfile.getLastName();
        Glide.with(this)
                .load(userProfile.getProfilePicture())
                .into(((ImageView) header.findViewById(R.id.avatarImageView)));
        ((TextView) header.findViewById(R.id.nameTextView)).setText(nameFirstLast);
        ((TextView) header.findViewById(R.id.phoneTextView)).setText(userProfile.getPhoneNumber());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}