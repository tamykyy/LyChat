package edu.tamykyy.lychat.data.storage;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import edu.tamykyy.lychat.data.storage.models.UserDataModel;

public interface UserStorage {

    Task<DocumentReference> save(UserDataModel user);
}
