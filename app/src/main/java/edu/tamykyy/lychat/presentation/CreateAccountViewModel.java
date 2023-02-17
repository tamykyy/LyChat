package edu.tamykyy.lychat.presentation;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.data.storage.models.Response;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.usecase.CreateUserUseCase;

@HiltViewModel
public class CreateAccountViewModel extends ViewModel {

    private final CreateUserUseCase createUserUseCase;

    @Inject
    public CreateAccountViewModel(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    public LiveData<Response> createUser(
            Uri imageUri, String firstName,
            String lastName, String phoneNumber, String uid
    ) {
        UserDomainModel userDomainModel = new UserDomainModel();
        userDomainModel.setFirstName(firstName);
        userDomainModel.setLastName(lastName);
        userDomainModel.setProfilePicture(imageUri);
        userDomainModel.setPhoneNumber(phoneNumber);
        userDomainModel.setUserUID(uid);
        return createUserUseCase.execute(userDomainModel);
    }
}
