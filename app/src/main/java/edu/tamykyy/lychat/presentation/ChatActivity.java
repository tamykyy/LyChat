package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivityChatBinding;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding myBinding;
    private ChatActivityViewModel myViewModel;
    @Inject
    public FirebaseUser currentUser;

    private UserDomainModel userProfile;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        myViewModel = new ViewModelProvider(this).get(ChatActivityViewModel.class);

        if (currentUser == null) {
            startActivity(new Intent(this, AuthenticationActivity.class));
        } else {
            // my view model get user profile
            Single<UserDomainModel> userProfileSingle = myViewModel.getUserProfile(currentUser);

            Disposable disposable = userProfileSingle.subscribe(
                    this::setUIUserData,
                    throwable -> Log.d("AAA", "error: " + throwable.getMessage())
            );
            compositeDisposable.add(disposable);
        }
    }

    private void setUIUserData(UserDomainModel userProfile) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}