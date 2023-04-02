package edu.tamykyy.lychat.domain.usecase;

import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Completable;

public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Completable execute(UserDomainModel userDomainModel) {
        return userRepository.save(userDomainModel);
    }
}
