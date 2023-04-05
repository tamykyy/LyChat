package edu.tamykyy.lychat.domain.usecase;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Observable;

public class GetUserUpdatesUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetUserUpdatesUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Observable<UserDomainModel> execute(String userUid) {
        return userRepository.getUpdates(userUid);
    }
}
