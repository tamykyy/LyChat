package edu.tamykyy.lychat.domain.usecase;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.repository.UserLocalRepository;
import io.reactivex.rxjava3.core.Completable;

public class SaveImageToGalleryUseCase {

    private final UserLocalRepository userLocalRepository;

    @Inject
    public SaveImageToGalleryUseCase(UserLocalRepository userLocalRepository) {
        this.userLocalRepository = userLocalRepository;
    }

    public Completable execute(Drawable drawable, ContentResolver contentResolver) {
        Uri images;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            images = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "images/*");
        contentValues.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        contentValues.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        Uri uri = contentResolver.insert(images, contentValues);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();

        return userLocalRepository.saveImageToGallery(uri, bitmap, contentResolver);
    }
}
