package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.usecase.CheckUsernameAvailabilityUseCase;
import edu.tamykyy.lychat.domain.usecase.UpdateUserProfileUseCase;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class EditUsernameActivityViewModel extends ViewModel {

    private final CheckUsernameAvailabilityUseCase checkUsernameAvailabilityUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;

    @Inject
    public EditUsernameActivityViewModel(CheckUsernameAvailabilityUseCase checkUsernameAvailabilityUseCase,
                                         UpdateUserProfileUseCase updateUserProfileUseCase) {
        this.checkUsernameAvailabilityUseCase = checkUsernameAvailabilityUseCase;
        this.updateUserProfileUseCase = updateUserProfileUseCase;
    }

    public Single<Boolean> checkUsernameAvailability(String username) {
        return checkUsernameAvailabilityUseCase.execute(username);
    }

    public Completable setUsername(String uid, String usernameVal) {
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("username", usernameVal);
        return updateUserProfileUseCase.execute(uid, userMap);
    }
}
