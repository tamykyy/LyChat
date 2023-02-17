package edu.tamykyy.lychat.data.storage;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.tamykyy.lychat.data.storage.models.UserDataModel;

public class UserFirestoreImpl implements UserStorage {

    private static final String COLLECTION_NAME = "users";
    private final FirebaseFirestore firestore;

    public UserFirestoreImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Task<DocumentReference> save(UserDataModel user) {
        return firestore.collection(COLLECTION_NAME).add(user);
    }
}
