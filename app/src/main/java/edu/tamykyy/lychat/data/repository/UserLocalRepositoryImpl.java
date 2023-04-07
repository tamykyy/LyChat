package edu.tamykyy.lychat.data.repository;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;

import javax.inject.Inject;

import edu.tamykyy.lychat.data.storage.interfaces.PhoneStorage;
import edu.tamykyy.lychat.domain.repository.UserLocalRepository;
import io.reactivex.rxjava3.core.Completable;

public class UserLocalRepositoryImpl implements UserLocalRepository {

    private final PhoneStorage phoneStorage;

    @Inject
    public UserLocalRepositoryImpl(PhoneStorage phoneStorage) {
        this.phoneStorage = phoneStorage;
    }

    @Override
    public Completable saveImageToGallery(Uri uri, Bitmap bitmap, ContentResolver contentResolver) {
        return phoneStorage.saveImageToGallery(uri, bitmap, contentResolver);
    }
}
