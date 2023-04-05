package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.usecase.GetUserUpdatesUseCase;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class SettingsActivityViewModel extends ViewModel {

    private final GetUserUpdatesUseCase getUserUpdatesUseCase;

    @Inject
    public SettingsActivityViewModel(GetUserUpdatesUseCase getUserUpdatesUseCase) {
        this.getUserUpdatesUseCase = getUserUpdatesUseCase;
    }

    public Observable<UserDomainModel> getUserProfileUpdates(String userUid) {
        return getUserUpdatesUseCase.execute(userUid);
    }
}
