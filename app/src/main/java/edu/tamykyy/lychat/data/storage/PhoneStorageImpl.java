package edu.tamykyy.lychat.data.storage;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Objects;

import edu.tamykyy.lychat.data.storage.interfaces.PhoneStorage;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PhoneStorageImpl implements PhoneStorage {

    @Override
    public Completable saveImageToGallery(Uri uri, Bitmap bitmap, ContentResolver contentResolver) {
        return Completable.create(emitter -> {
            try {
                OutputStream outputStream = contentResolver.openOutputStream(Objects.requireNonNull(uri));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                Objects.requireNonNull(outputStream);
                emitter.onComplete();
            } catch (FileNotFoundException e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io());
    }
}
