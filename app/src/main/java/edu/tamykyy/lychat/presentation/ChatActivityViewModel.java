package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.usecase.GetUserUpdatesUseCase;
import edu.tamykyy.lychat.domain.usecase.GetUserUseCase;
import edu.tamykyy.lychat.domain.usecase.LogoutUseCase;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class ChatActivityViewModel extends ViewModel {

    private final GetUserUpdatesUseCase getUserUpdatesUseCase;
    private final LogoutUseCase logoutUseCase;

    @Inject
    public ChatActivityViewModel(GetUserUpdatesUseCase getUserUpdatesUseCase, LogoutUseCase logoutUseCase) {
        this.getUserUpdatesUseCase = getUserUpdatesUseCase;
        this.logoutUseCase = logoutUseCase;
    }

    public Observable<UserDomainModel> getUserProfileUpdates(String userUid) {
        return getUserUpdatesUseCase.execute(userUid);
    }

    public void logout() {
        logoutUseCase.execute();
    }
}
