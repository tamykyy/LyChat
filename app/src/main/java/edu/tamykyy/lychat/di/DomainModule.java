package edu.tamykyy.lychat.di;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import edu.tamykyy.lychat.domain.repository.AuthenticationRepository;
import edu.tamykyy.lychat.domain.repository.UserRepository;
import edu.tamykyy.lychat.domain.usecase.CreateUserUseCase;
import edu.tamykyy.lychat.domain.usecase.GetUserUseCase;
import edu.tamykyy.lychat.domain.usecase.SendVerificationCodeUseCase;
import edu.tamykyy.lychat.domain.usecase.SignInWithPhoneUseCase;

@Module
@InstallIn(ViewModelComponent.class)
public final class DomainModule {

    @Provides
    public SendVerificationCodeUseCase provideSendVerificationCodeUseCase(AuthenticationRepository authenticationRepository) {
        return new SendVerificationCodeUseCase(authenticationRepository);
    }

    @Provides
    public SignInWithPhoneUseCase provideSignInWithPhoneUseCase(AuthenticationRepository authenticationRepository) {
        return new SignInWithPhoneUseCase(authenticationRepository);
    }

    @Provides
    public CreateUserUseCase provideCreateUserUseCase(UserRepository userRepository) {
        return new CreateUserUseCase(userRepository);
    }

    @Provides
    public GetUserUseCase provideGetUserUseCase(UserRepository userRepository) {
        return new GetUserUseCase(userRepository);
    }

}
