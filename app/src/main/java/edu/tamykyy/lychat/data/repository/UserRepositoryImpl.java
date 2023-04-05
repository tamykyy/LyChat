package edu.tamykyy.lychat.data.repository;

import android.net.Uri;
import android.util.Log;


import com.google.android.gms.tasks.Task;

import java.io.FileNotFoundException;
import java.util.Objects;

import javax.inject.Inject;

import edu.tamykyy.lychat.data.storage.UserFirestoreImpl;
import edu.tamykyy.lychat.data.storage.UserStorageImpl;
import edu.tamykyy.lychat.data.storage.models.UserDataModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.repository.UserRepository;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class UserRepositoryImpl implements UserRepository {

    private final UserFirestoreImpl firebase;
    private final UserStorageImpl storage;

    private static final Uri DEFAULT_PICTURE_URI = Uri.parse("https://firebasestorage.googleapis.com/v0/b/lychat-me.appspot.com/o/profilePictures%2Fdefault_img.png?alt=media&token=d213a51d-d799-48f0-af7b-fa3ca826f20c");

    @Inject
    public UserRepositoryImpl(UserFirestoreImpl firebase, UserStorageImpl storage) {
        this.firebase = firebase;
        this.storage = storage;
    }

    @Override
    public Single<UserDomainModel> get(String uid) {
        return Single.create(emitter -> firebase.get(uid)
                .addOnSuccessListener(doc -> {
                    if (doc.exists())
                        emitter.onSuccess(mapToDomain(Objects.requireNonNull(doc.toObject(UserDataModel.class))));
                    else emitter.onError(new FileNotFoundException("user is absent"));
                }).addOnFailureListener(emitter::onError));
    }

    @Override
    public Completable save(UserDomainModel user) {
        Log.d("AAA", "saving starts");
        return Completable.create(emitter -> {
            if (user.getProfilePicture() == null) {
                user.setProfilePicture(DEFAULT_PICTURE_URI);
                saveUser(user)
                        .addOnSuccessListener(unused -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError);
            } else {
                saveImage(user)
                        .addOnSuccessListener(uri -> {
                            user.setProfilePicture(uri);
                            saveUser(user)
                                    .addOnSuccessListener(unused -> emitter.onComplete())
                                    .addOnFailureListener(emitter::onError);
                        })
                        .addOnFailureListener(emitter::onError);
            }
        });
    }

    @Override
    public Observable<UserDomainModel> getUpdates(String uid) {
        return Observable.create(emitter -> firebase.getDocumentRef(uid)
                .addSnapshotListener((value, error) -> {
                    if (error != null) emitter.onError(error);

                    if (value != null && value.exists())
                        emitter.onNext(mapToDomain(Objects.requireNonNull(value.toObject(UserDataModel.class))));
                }));
    }

    private Task<Uri> saveImage(UserDomainModel user) {
        return storage.save(user.getProfilePicture(), user.getUserUID());
    }

    private Task<Void> saveUser(UserDomainModel user) {
        return firebase.save(mapToData(user));
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
