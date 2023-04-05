package edu.tamykyy.lychat.data.storage.interfaces;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import edu.tamykyy.lychat.data.storage.models.UserDataModel;

public interface UserFirestore {

    Task<Void> save(UserDataModel user);

    Task<DocumentSnapshot> get(String uid);

    DocumentReference getDocumentRef(String uid);
}

