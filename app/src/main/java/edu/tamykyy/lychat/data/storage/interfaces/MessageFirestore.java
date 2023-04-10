package edu.tamykyy.lychat.data.storage.interfaces;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public interface MessageFirestore {

    Task<DocumentSnapshot> getMessage(String ref);
}
