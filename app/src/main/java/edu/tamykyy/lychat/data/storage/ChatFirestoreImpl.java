package edu.tamykyy.lychat.data.storage;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import edu.tamykyy.lychat.data.storage.interfaces.ChatFirestore;

public class ChatFirestoreImpl implements ChatFirestore {

    private static final String COLLECTION_NAME = "chats";

    private final FirebaseFirestore firestore;

    public ChatFirestoreImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public Task<QuerySnapshot> getUserChatsQuery(String userUid) {
        return firestore.collection(COLLECTION_NAME)
                .where(Filter.or(
                    Filter.equalTo("userUid1", userUid),
                    Filter.equalTo("userUid1", userUid)
                )).get();
    }
}
