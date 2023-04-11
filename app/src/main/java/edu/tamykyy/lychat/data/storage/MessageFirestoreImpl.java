package edu.tamykyy.lychat.data.storage;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import edu.tamykyy.lychat.data.storage.interfaces.MessageFirestore;
import edu.tamykyy.lychat.data.storage.models.MessageDataModel;

public class MessageFirestoreImpl implements MessageFirestore {

    private static final String COLLECTION_NAME = "chats";
    private static final String SUB_COLLECTION_NAME = "messages";

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

    @Override
    public Task<DocumentReference> sendMessage(String chatId, MessageDataModel messageDataModel) {
        return firestore
                .collection(COLLECTION_NAME)
                .document(chatId)
                .collection(SUB_COLLECTION_NAME)
                .add(messageDataModel);
    }

    public CollectionReference getMessageCollectionRef(String chatId) {
        return firestore.collection(COLLECTION_NAME)
                .document(chatId)
                .collection(SUB_COLLECTION_NAME);
    }
}
