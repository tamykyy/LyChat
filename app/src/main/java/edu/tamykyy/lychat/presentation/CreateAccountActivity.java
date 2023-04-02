package edu.tamykyy.lychat.presentation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivityCreateAccountBinding;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class CreateAccountActivity extends AppCompatActivity {

    private ActivityCreateAccountBinding myBinding;
    private CreateAccountViewModel myViewModel;

    @Inject
    public FirebaseUser currentUser;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Create profile");
        }

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_account);
        myViewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        myBinding.avatarImageView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            galleryResultLauncher.launch(intent);
        });

        myBinding.buttonCreate.setOnClickListener(v -> {
            if (currentUser == null) {
                Toast.makeText(this, "Error accuride", Toast.LENGTH_LONG).show();
                return;
            }
            Log.d("AAA", "" + currentUser);

            Completable saveUser = myViewModel.createUser(imageUri,
                    myBinding.nameEditText.getText().toString(),
                    myBinding.lastNameEditText.getText().toString(),
                    currentUser.getPhoneNumber(),
                    currentUser.getUid());

            Disposable disposable = saveUser.subscribe(
                    () -> startActivity(new Intent(CreateAccountActivity.this, ChatActivity.class)),
                    throwable -> Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show()
            );
            compositeDisposable.add(disposable);
        });
    }

    private Uri imageUri;
    private final ActivityResultLauncher<Intent> galleryResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    imageUri = result.getData().getData();
                    myBinding.avatarImageView.setImageURI(imageUri);
                    Log.d("AAA", "OKKKK");
                }
            }
    );

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}