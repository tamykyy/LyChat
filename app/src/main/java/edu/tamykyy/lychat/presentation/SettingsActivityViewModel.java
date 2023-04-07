package edu.tamykyy.lychat.presentation;

import android.content.ContentResolver;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.usecase.DeleteProfileImageUseCase;
import edu.tamykyy.lychat.domain.usecase.GetUserUpdatesUseCase;
import edu.tamykyy.lychat.domain.usecase.LogoutUseCase;
import edu.tamykyy.lychat.domain.usecase.SaveImageToGalleryUseCase;
import edu.tamykyy.lychat.domain.usecase.UpdateUserProfilePhotoUseCase;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class SettingsActivityViewModel extends ViewModel {

    private final GetUserUpdatesUseCase getUserUpdatesUseCase;

    private final UpdateUserProfilePhotoUseCase updateUserProfilePhotoUseCase;

    private final SaveImageToGalleryUseCase saveImageToGalleryUseCase;

    private final DeleteProfileImageUseCase deleteProfileImageUseCase;

    private final LogoutUseCase logoutUseCase;

    @Inject
    public SettingsActivityViewModel(GetUserUpdatesUseCase getUserUpdatesUseCase,
                                     LogoutUseCase logoutUseCase,
                                     UpdateUserProfilePhotoUseCase updateUserProfilePhotoUseCase,
                                     SaveImageToGalleryUseCase saveImageToGalleryUseCase, DeleteProfileImageUseCase deleteProfileImageUseCase) {
        this.getUserUpdatesUseCase = getUserUpdatesUseCase;
        this.logoutUseCase = logoutUseCase;
        this.updateUserProfilePhotoUseCase = updateUserProfilePhotoUseCase;
        this.saveImageToGalleryUseCase = saveImageToGalleryUseCase;
        this.deleteProfileImageUseCase = deleteProfileImageUseCase;
    }

    public Observable<UserDomainModel> getUserProfileUpdates(String userUid) {
        return getUserUpdatesUseCase.execute(userUid);
    }

    public Completable updateUserProfilePhoto(Uri uri) {
        return updateUserProfilePhotoUseCase.execute(FirebaseAuth.getInstance().getUid(), uri);
    }

    public Completable saveImageToGallery(Drawable drawable, ContentResolver contentResolver) {
        return saveImageToGalleryUseCase.execute(drawable, contentResolver);
    }

    public Completable deleteProfileImage() {
        return deleteProfileImageUseCase.execute(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public void logout() {
        logoutUseCase.execute();
    }
}
