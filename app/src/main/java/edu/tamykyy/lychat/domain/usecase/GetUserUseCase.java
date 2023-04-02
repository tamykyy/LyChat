package edu.tamykyy.lychat.domain.usecase;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Single;

public class GetUserUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Single<UserDomainModel> execute(String userUid) {
        return userRepository.get(userUid);
    }
}
