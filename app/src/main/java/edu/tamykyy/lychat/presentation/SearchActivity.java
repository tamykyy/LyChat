package edu.tamykyy.lychat.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.HashSet;

import dagger.hilt.android.AndroidEntryPoint;
import edu.tamykyy.lychat.R;
import edu.tamykyy.lychat.databinding.ActivitySearchBinding;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.presentation.adapters.SearchRecyclerViewAdapter;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding myBinding;
    private SearchActivityViewModel myViewModel;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable searchDisposable = Disposable.disposed();

    private final HashSet<UserDomainModel> userSet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        myViewModel = new ViewModelProvider(this).get(SearchActivityViewModel.class);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(myBinding.searchEditText.getRootView(), InputMethodManager.SHOW_IMPLICIT);
        myBinding.searchEditText.requestFocus();

        myBinding.topAppBar.setNavigationOnClickListener(v -> finish());

        myBinding.clearSearchBarImageView.setOnClickListener(v -> myBinding.searchEditText.setText(""));

        myBinding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                compositeDisposable.remove(searchDisposable);
                userSet.clear();
                showUsers();

                if (s.length() == 0) {
                    hideCloseImageView();
                } else {
                    showCloseImageView();
                    searchDisposable = myViewModel.findUser(s.toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userSet::add,
                                    throwable -> Log.d("AAA", throwable.getMessage()),
                                    () -> showUsers()
                            );
                    compositeDisposable.add(searchDisposable);
                }
            }
        });
    }

    private void showUsers() {
        Log.d("AAA", Thread.currentThread().getName());
        myBinding.usersRecyclerView.setAdapter(new SearchRecyclerViewAdapter(new ArrayList<>(userSet)));
        myBinding.usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void hideCloseImageView() {
        myBinding.clearSearchBarImageView.setEnabled(false);
        myBinding.clearSearchBarImageView.setVisibility(View.INVISIBLE);
    }

    private void showCloseImageView() {
        myBinding.clearSearchBarImageView.setEnabled(true);
        myBinding.clearSearchBarImageView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}