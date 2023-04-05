package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivitySettingsBinding;
import edu.tamykyy.lychat.domain.models.UserDomainModel;

@AndroidEntryPoint
public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding myBinding;

    private SettingsActivityViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        myViewModel = new ViewModelProvider(this).get(SettingsActivityViewModel.class);

        UserDomainModel userProfile = (UserDomainModel) getIntent().getExtras().get("userProfile");
        Log.d("AAA", userProfile.toString());

        myBinding.topAppBar.setNavigationOnClickListener(v -> finish());
    }
}