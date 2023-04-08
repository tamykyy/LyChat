package edu.tamykyy.lychat.domain.usecase;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Observable;

public class FindUserUseCase {

    private UserRepository userRepository;

    private static final String[] FIELDS_TYPES = {"firstName", "lastName", "username", "phoneNumber"};

    @Inject
    public FindUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Observable<UserDomainModel> execute(String value) {
        return userRepository.findUserQuery(FIELDS_TYPES, value);
    }
}
