package edu.tamykyy.lychat.data.storage.interfaces;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;

import io.reactivex.rxjava3.core.Completable;

public interface PhoneStorage {

    Completable saveImageToGallery(Uri uri, Bitmap bitmap, ContentResolver contentResolver);
}
