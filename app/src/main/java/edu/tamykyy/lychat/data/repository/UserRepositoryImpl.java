package edu.tamykyy.lychat.data.repository;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import javax.inject.Inject;

import edu.tamykyy.lychat.data.storage.UserFirestoreImpl;
import edu.tamykyy.lychat.data.storage.UserProfilePicStorageImpl;
import edu.tamykyy.lychat.data.storage.models.Response;
import edu.tamykyy.lychat.data.storage.models.UserDataModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

    private final UserFirestoreImpl firebase;
    private final UserProfilePicStorageImpl storage;

    @Inject
    public UserRepositoryImpl(UserFirestoreImpl firebase, UserProfilePicStorageImpl storage) {
        this.firebase = firebase;
        this.storage = storage;
    }

    @Override
    public LiveData<Response> save(UserDomainModel user) {
        MutableLiveData<Response> response = new MutableLiveData<>();

        Log.d("AAA", "saving starts");
        storage.save(user.getProfilePicture(), user.getUserUID())
                .addOnCompleteListener(savePicTask -> {
                    if (savePicTask.isSuccessful()) {
                        user.setProfilePicture(savePicTask.getResult());
                        firebase.save(mapToData(user))
                                .addOnSuccessListener(saveUserTask ->
                                        response.postValue(new Response(true, false, "success")))
                                .addOnFailureListener(e ->
                                        response.postValue(new Response(false, true, e.getMessage())));
                    } else {
                        response.postValue(new Response(false, true, savePicTask.getException().getMessage()));
                    }
                });
        return response;
    }

    private UserDataModel mapToData(UserDomainModel userDomain) {
        UserDataModel userData = new UserDataModel();
        userData.setFirstName(userDomain.getFirstName());
        userData.setLastName(userDomain.getLastName());
        userData.setPhoneNumber(userDomain.getPhoneNumber());
        userData.setEmail(userDomain.getEmail());
        userData.setPassword(userDomain.getPassword());
        Log.d("AAA", "userDomain profPic:" + userDomain.getProfilePicture());
        userData.setProfilePicture(userDomain.getProfilePicture().toString());
        userData.setUserUID(userDomain.getUserUID());
        return userData;
    }

    private UserDomainModel mapToDomain(UserDataModel userData) {
        UserDomainModel userDomain = new UserDomainModel();
        userDomain.setFirstName(userData.getFirstName());
        userDomain.setLastName(userData.getLastName());
        userDomain.setPhoneNumber(userData.getPhoneNumber());
        userDomain.setEmail(userData.getEmail());
        userDomain.setPassword(userData.getPassword());
        userDomain.setProfilePicture(Uri.parse(userData.getProfilePicture()));
        userDomain.setUserUID(userData.getUserUID());
        return userDomain;
    }
}
