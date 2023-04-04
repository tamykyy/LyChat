package edu.tamykyy.lychat.domain.usecase;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.repository.AuthenticationRepository;

public class LogoutUseCase {

    private final AuthenticationRepository authenticationRepository;

    @Inject
    public LogoutUseCase(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public void execute() {
        authenticationRepository.signOut();
    }
}
