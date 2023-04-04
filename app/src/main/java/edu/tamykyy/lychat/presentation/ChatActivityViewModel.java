package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.usecase.GetUserUseCase;
import edu.tamykyy.lychat.domain.usecase.LogoutUseCase;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class ChatActivityViewModel extends ViewModel {

    private final GetUserUseCase getUserUseCase;
    private final LogoutUseCase logoutUseCase;

    @Inject
    public ChatActivityViewModel(GetUserUseCase getUserUseCase, LogoutUseCase logoutUseCase) {
        this.getUserUseCase = getUserUseCase;
        this.logoutUseCase = logoutUseCase;
    }

    public Single<UserDomainModel> getUserProfile(FirebaseUser user) {
        return getUserUseCase.execute(user.getUid());
    }

    public void logout() {
        logoutUseCase.execute();
    }
}
