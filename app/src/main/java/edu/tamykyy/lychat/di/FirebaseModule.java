package edu.tamykyy.lychat.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public final class FirebaseModule {

    @Provides
    @Singleton
    public FirebaseAuth provideFirebaseAuthInstance() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    public FirebaseUser provideFirebaseCurrentUser(FirebaseAuth auth) {
        return auth.getCurrentUser();
    }
}
