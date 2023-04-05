package edu.tamykyy.lychat.domain.usecase;

import java.util.HashMap;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Completable;

public class UpdateUserProfileUseCase {

    private final UserRepository userRepository;

    @Inject
    public UpdateUserProfileUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Completable execute(String uid, HashMap<String, Object> userMap) {
        return userRepository.updateUserProfile(uid, userMap);
    }
}
