package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.usecase.GetUserUseCase;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class ChatActivityViewModel extends ViewModel {

    private final GetUserUseCase getUserUseCase;

    @Inject
    public ChatActivityViewModel(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    public Single<UserDomainModel> getUserProfile(FirebaseUser user) {
        return getUserUseCase.execute(user.getUid());
    }
}
