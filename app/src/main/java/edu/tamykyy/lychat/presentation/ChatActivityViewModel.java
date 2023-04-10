package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.ChatDomainModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.usecase.GetUserChatsUseCase;
import edu.tamykyy.lychat.domain.usecase.GetUserUpdatesUseCase;
import edu.tamykyy.lychat.domain.usecase.LogoutUseCase;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class ChatActivityViewModel extends ViewModel {

    private final GetUserUpdatesUseCase getUserUpdatesUseCase;

    private final GetUserChatsUseCase getUserChatsUseCase;

    private final LogoutUseCase logoutUseCase;

    @Inject
    public ChatActivityViewModel(GetUserUpdatesUseCase getUserUpdatesUseCase, GetUserChatsUseCase getUserChatsUseCase, LogoutUseCase logoutUseCase) {
        this.getUserUpdatesUseCase = getUserUpdatesUseCase;
        this.getUserChatsUseCase = getUserChatsUseCase;
        this.logoutUseCase = logoutUseCase;
    }

    public Observable<UserDomainModel> getUserProfileUpdates(String userUid) {
        return getUserUpdatesUseCase.execute(userUid);
    }

    public Observable<ChatDomainModel> getUsersChats(String userUid) {
        return getUserChatsUseCase.execute(userUid);
    }

    public void logout() {
        logoutUseCase.execute();
    }
}
