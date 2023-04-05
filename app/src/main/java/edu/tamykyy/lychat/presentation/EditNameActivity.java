package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivityEditNameBinding;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@SuppressLint("ClickableViewAccessibility")
@AndroidEntryPoint
public class EditNameActivity extends AppCompatActivity {

    private ActivityEditNameBinding myBinding;
    private EditNameActivityViewModel myViewModel;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_name);
        myViewModel = new ViewModelProvider(this).get(EditNameActivityViewModel.class);

        String firstName = getIntent().getExtras().getString("firstName");
        String lastName = getIntent().getExtras().getString("lastName");

        myBinding.nameFirstEditText.setText(firstName);
        myBinding.nameLastEditText.setText(lastName);

        myBinding.toolbar.setNavigationOnClickListener(v -> finish());

        myBinding.base.setOnTouchListener(new SwipeListener(this) {
            @Override
            public void onSwipeRight() {
                finish();
            }
        });

        myBinding.toolbar.setOnMenuItemClickListener(item -> {
            Disposable disposable = myViewModel.updateUserProfile(
                    myBinding.nameFirstEditText.getText().toString(),
                    myBinding.nameLastEditText.getText().toString()
            ).subscribe(
                    this::finish,
                    throwable -> Log.d("AAA", throwable.getMessage())
            );
            compositeDisposable.add(disposable);
            return true;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}