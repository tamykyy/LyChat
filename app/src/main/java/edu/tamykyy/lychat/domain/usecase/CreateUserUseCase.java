package edu.tamykyy.lychat.domain.usecase;

import androidx.lifecycle.LiveData;

import edu.tamykyy.lychat.data.storage.models.Response;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.repository.UserRepository;

public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Response> execute(UserDomainModel userDomainModel) {
        return userRepository.save(userDomainModel);
    }
}
