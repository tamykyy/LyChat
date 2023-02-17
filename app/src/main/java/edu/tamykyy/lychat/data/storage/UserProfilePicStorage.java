package edu.tamykyy.lychat.data.storage;

import android.net.Uri;

import com.google.android.gms.tasks.Task;


public interface UserProfilePicStorage {

    Task<Uri> save(Uri uri, String userUID);
}
