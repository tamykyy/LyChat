package edu.tamykyy.lychat.di;

import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import edu.tamykyy.lychat.domain.usecase.CreateCredentialUseCase;
import edu.tamykyy.lychat.domain.usecase.SendVerificationCodeUseCase;
import edu.tamykyy.lychat.domain.usecase.SignInWithCredentialUseCase;

@Module
@InstallIn(ViewModelComponent.class)
public final class DomainModel {

    @Provides
    public SendVerificationCodeUseCase provideSendVerificationCodeUseCase(FirebaseAuth auth) {
        return new SendVerificationCodeUseCase(auth);
    }

    @Provides
    public CreateCredentialUseCase provideCreatePhoneAuthCredentialUseCase() {
        return new CreateCredentialUseCase();
    }

    @Provides
    public SignInWithCredentialUseCase provideSignInWithPhoneAuthCredentialUseCase(FirebaseAuth auth) {
        return new SignInWithCredentialUseCase(auth);
    }

}
