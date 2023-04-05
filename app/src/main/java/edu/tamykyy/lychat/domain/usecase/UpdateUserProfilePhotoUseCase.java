package edu.tamykyy.lychat.domain.usecase;

import android.net.Uri;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Completable;

public class UpdateUserProfilePhotoUseCase {

    private final UserRepository userRepository;

    @Inject
    public UpdateUserProfilePhotoUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Completable execute(String uid, Uri imageUri) {
        return userRepository.updateUserImage(uid, imageUri);
    }
}
