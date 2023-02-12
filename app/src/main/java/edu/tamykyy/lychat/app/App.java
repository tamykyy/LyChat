package edu.tamykyy.lychat.app;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import dagger.hilt.android.HiltAndroidApp;
import edu.tamykyy.lychat.presentation.AuthenticationActivity;

@HiltAndroidApp
public class App extends Application {
}
