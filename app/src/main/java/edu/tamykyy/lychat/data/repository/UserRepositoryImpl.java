package edu.tamykyy.lychat.data.repository;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.FileNotFoundException;
import java.util.Objects;

import javax.inject.Inject;

import edu.tamykyy.lychat.data.storage.UserFirestoreImpl;
import edu.tamykyy.lychat.data.storage.UserProfilePicStorageImpl;
import edu.tamykyy.lychat.data.storage.models.Response;
import edu.tamykyy.lychat.data.storage.models.UserDataModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Single;

public class UserRepositoryImpl implements UserRepository {

    private final UserFirestoreImpl firebase;
    private final UserProfilePicStorageImpl storage;

    private static final Uri DEFAULT_PICTURE_URI = Uri.parse("https://firebasestorage.googleapis.com/v0/b/lychat-me.appspot.com/o/profilePictures%2Fdefault_img.png?alt=media&token=d213a51d-d799-48f0-af7b-fa3ca826f20c");

    @Inject
    public UserRepositoryImpl(UserFirestoreImpl firebase, UserProfilePicStorageImpl storage) {
        this.firebase = firebase;
        this.storage = storage;
    }

    @Override
    public Single<UserDomainModel> get(String uid) {
        return Single.create(emitter -> firebase.get(uid)
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) emitter.onSuccess(mapToDomain(Objects.requireNonNull(doc.toObject(UserDataModel.class))));
                    else emitter.onError(new FileNotFoundException("user is absent"));
                }).addOnFailureListener(emitter::onError));
    }

    @Override
    public LiveData<Response> save(UserDomainModel user) {
        MutableLiveData<Response> response = new MutableLiveData<>();

        Log.d("AAA", "saving starts");
        if (user.getProfilePicture() == null) {
            user.setProfilePicture(DEFAULT_PICTURE_URI);
            saveUser(user, response);
        } else {
            storage.save(user.getProfilePicture(), user.getUserUID())
                    .addOnCompleteListener(savePicTask -> {
                        if (savePicTask.isSuccessful()) {
                            user.setProfilePicture(savePicTask.getResult());
                            saveUser(user, response);
                        } else {
                            response.postValue(new Response(false, true, savePicTask.getException().getMessage()));
                        }
                    });
        }
        return response;
    }

    private void saveUser(UserDomainModel user, MutableLiveData<Response> response) {
        firebase.save(mapToData(user))
                .addOnSuccessListener(saveUserTask ->
                        response.postValue(new Response(true, false, "success")))
                .addOnFailureListener(e ->
                        response.postValue(new Response(false, true, e.getMessage())));
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
