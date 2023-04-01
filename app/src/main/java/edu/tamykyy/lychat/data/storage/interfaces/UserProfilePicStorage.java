package edu.tamykyy.lychat.data.storage.interfaces;

import android.net.Uri;

import com.google.android.gms.tasks.Task;


public interface UserProfilePicStorage {

    Task<Uri> save(Uri uri, String userUID);
}
