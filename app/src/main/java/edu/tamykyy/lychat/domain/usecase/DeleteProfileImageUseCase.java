package edu.tamykyy.lychat.domain.usecase;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Completable;

public class DeleteProfileImageUseCase {

    private UserRepository userRepository;

    @Inject
    public DeleteProfileImageUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Completable execute(String uid) {
        return userRepository.deleteProfilePhoto(uid);
    }
}
