package edu.tamykyy.lychat.domain.usecase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import edu.tamykyy.lychat.domain.models.VerificationResultModel;
import edu.tamykyy.lychat.domain.repository.AuthenticationRepository;

public class SendVerificationCodeUseCase {


    private final AuthenticationRepository authenticationRepository;

    public SendVerificationCodeUseCase(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public LiveData<VerificationResultModel> execute(String phoneNumber, AppCompatActivity activity) {
        return authenticationRepository.sendVerificationCode(phoneNumber, activity);
    }

}
