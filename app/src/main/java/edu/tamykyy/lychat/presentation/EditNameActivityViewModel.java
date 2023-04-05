package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.usecase.UpdateUserProfileUseCase;
import io.reactivex.rxjava3.core.Completable;

@HiltViewModel
public class EditNameActivityViewModel extends ViewModel {

    private final UpdateUserProfileUseCase updateUserProfileUseCase;

    @Inject
    public EditNameActivityViewModel(UpdateUserProfileUseCase updateUserProfileUseCase) {
        this.updateUserProfileUseCase = updateUserProfileUseCase;
    }

    public Completable updateUserProfile(String firstName, String lastName) {
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", firstName);
        userMap.put("lastName", lastName);
        String uid = FirebaseAuth.getInstance().getUid();
        return updateUserProfileUseCase.execute(uid, userMap);
    }
}
