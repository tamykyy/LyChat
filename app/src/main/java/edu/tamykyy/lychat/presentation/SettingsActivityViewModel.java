package edu.tamykyy.lychat.presentation;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.usecase.GetUserUpdatesUseCase;
import edu.tamykyy.lychat.domain.usecase.LogoutUseCase;
import edu.tamykyy.lychat.domain.usecase.UpdateUserProfilePhotoUseCase;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class SettingsActivityViewModel extends ViewModel {

    private final GetUserUpdatesUseCase getUserUpdatesUseCase;

    private final UpdateUserProfilePhotoUseCase updateUserProfilePhotoUseCase;
    private final LogoutUseCase logoutUseCase;

    @Inject
    public SettingsActivityViewModel(GetUserUpdatesUseCase getUserUpdatesUseCase,
                                     LogoutUseCase logoutUseCase,
                                     UpdateUserProfilePhotoUseCase updateUserProfilePhotoUseCase) {
        this.getUserUpdatesUseCase = getUserUpdatesUseCase;
        this.logoutUseCase = logoutUseCase;
        this.updateUserProfilePhotoUseCase = updateUserProfilePhotoUseCase;
    }

    public Observable<UserDomainModel> getUserProfileUpdates(String userUid) {
        return getUserUpdatesUseCase.execute(userUid);
    }

    public Completable updateUserProfilePhoto(Uri uri) {
        return updateUserProfilePhotoUseCase.execute(FirebaseAuth.getInstance().getUid(), uri);
    }

    public void logout() {
        logoutUseCase.execute();
    }
}
