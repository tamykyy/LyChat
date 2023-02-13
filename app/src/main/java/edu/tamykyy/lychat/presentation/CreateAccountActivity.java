package edu.tamykyy.lychat.presentation;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity {

    private ActivityCreateAccountBinding myBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Create profile");
        }

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_account);

        myBinding.avatarImageView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            galleryResultLauncher.launch(intent);
        });
    }

    private ActivityResultLauncher<Intent> galleryResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri selectedImageUri = result.getData().getData();
                    myBinding.avatarImageView.setImageURI(selectedImageUri);
                    Log.d("AAA", "OKKKK");
                }
            }
    );
}