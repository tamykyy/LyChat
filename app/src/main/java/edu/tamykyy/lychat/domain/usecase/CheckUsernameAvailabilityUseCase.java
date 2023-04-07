package edu.tamykyy.lychat.domain.usecase;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Single;

public class CheckUsernameAvailabilityUseCase {

    private final UserRepository userRepository;

    @Inject
    public CheckUsernameAvailabilityUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Single<Boolean> execute(String usernameVal) {
        return userRepository.containsQuery("username", usernameVal);
    }
}
