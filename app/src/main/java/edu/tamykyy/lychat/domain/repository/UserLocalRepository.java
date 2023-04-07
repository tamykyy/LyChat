package edu.tamykyy.lychat.domain.repository;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;

import io.reactivex.rxjava3.core.Completable;

public interface UserLocalRepository {

    Completable saveImageToGallery(Uri uri, Bitmap bitmap, ContentResolver contentResolver);
}
