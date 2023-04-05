package edu.tamykyy.lychat.data.storage;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import edu.tamykyy.lychat.data.storage.interfaces.UserStorage;


public class UserStorageImpl implements UserStorage {

    private final static String REF = "profilePictures";
    private final StorageReference storageRef;


    public UserStorageImpl(FirebaseStorage storage) {
        this.storageRef = storage.getReference(REF);
    }

    @Override
    public Task<Uri> save(Uri uri, String userUID) {
        StorageReference picRef = storageRef.child(userUID);
        Log.d("AAA", "start saving picture");

        return picRef.putFile(uri).continueWithTask(task1 -> {
            if (!task1.isSuccessful()) {
                throw task1.getException();
            }
            return picRef.getDownloadUrl();
        });
    }
}
