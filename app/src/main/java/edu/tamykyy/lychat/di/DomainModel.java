package edu.tamykyy.lychat.di;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.components.SingletonComponent;
import edu.tamykyy.lychat.domain.usecase.CreatePhoneAuthCredentialUseCase;
import edu.tamykyy.lychat.domain.usecase.SendVerificationCodeUseCase;
import edu.tamykyy.lychat.domain.usecase.SignInWithPhoneAuthCredentialUseCase;

@Module
@InstallIn(ViewModelComponent.class)
public final class DomainModel {

    @Provides
    public SendVerificationCodeUseCase provideSendVerificationCodeUseCase(FirebaseAuth auth) {
        return new SendVerificationCodeUseCase(auth);
    }

    @Provides
    public CreatePhoneAuthCredentialUseCase provideCreatePhoneAuthCredentialUseCase() {
        return new CreatePhoneAuthCredentialUseCase();
    }

    @Provides
    public SignInWithPhoneAuthCredentialUseCase provideSignInWithPhoneAuthCredentialUseCase(FirebaseAuth auth) {
        return new SignInWithPhoneAuthCredentialUseCase(auth);
    }

}
