package edu.tamykyy.lychat.presentation;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AuthenticationViewModel extends ViewModel {

    @Inject
    public AuthenticationViewModel() {
    }

    protected void signInButtonListener() {
        Log.d("AAA", "Okay");
    }
}
