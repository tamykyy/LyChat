package edu.tamykyy.lychat.app;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import edu.tamykyy.lychat.domain.usecase.UpdateUserProfileUseCase;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltAndroidApp
public class App extends Application {

    @Inject
    public UpdateUserProfileUseCase updateUserProfileUseCase;

    @Override
    public void onCreate() {
        super.onCreate();

        HashMap<String, Object> onlineMap = new HashMap<>();
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currUser == null) return;

        ProcessLifecycleOwner.get().getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                DefaultLifecycleObserver.super.onResume(owner);

                onlineMap.put("onlineInfo", "online");
                Disposable ignore = updateUserProfileUseCase.execute(currUser.getUid(), onlineMap).subscribe(
                        () -> Log.d("AAA", "onResume"),
                        throwable -> Log.d("AAA", throwable.getMessage())
                );
            }

            @Override
            public void onPause(@NonNull LifecycleOwner owner) {
                DefaultLifecycleObserver.super.onPause(owner);

                onlineMap.put("onlineInfo", "offline");
                Disposable ignore = updateUserProfileUseCase.execute(currUser.getUid(), onlineMap).subscribe(
                        () -> Log.d("AAA", "onPause"),
                        throwable -> Log.d("AAA", throwable.getMessage())
                );
            }


        });
    }
}
