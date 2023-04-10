package edu.tamykyy.lychat.data.storage;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import edu.tamykyy.lychat.data.storage.interfaces.MessageFirestore;

public class MessageFirestoreImpl implements MessageFirestore {

    private static final String COLLECTION_NAME = "messages";

    private final FirebaseFirestore firestore;

    public MessageFirestoreImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Task<DocumentSnapshot> getMessage(String ref) {
        return firestore.collection(COLLECTION_NAME)
                .document(ref)
                .get();
    }
}
