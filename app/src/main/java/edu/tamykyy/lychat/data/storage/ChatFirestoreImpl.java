package edu.tamykyy.lychat.data.storage;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import edu.tamykyy.lychat.data.storage.interfaces.ChatFirestore;
import edu.tamykyy.lychat.data.storage.models.ChatDataModel;

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

    @Override
    public Task<QuerySnapshot> getChat(String uid1, String uid2) {
        return firestore.collection(COLLECTION_NAME)
                .where(Filter.or(
                        Filter.equalTo("userUid1", uid1),
                        Filter.equalTo("userUid1", uid2)
                )).where(Filter.or(
                        Filter.equalTo("userUid2", uid1),
                        Filter.equalTo("userUid2", uid2)
                )).get();
    }

    @Override
    public String getNewRef() {
        return firestore.collection(COLLECTION_NAME).document().getId();
    }

    @Override
    public Task<Void> saveChat(ChatDataModel chatDataModel) {
        return firestore.collection(COLLECTION_NAME)
                .document(chatDataModel.getId())
                .set(chatDataModel);
    }

    @Override
    public Task<Void> update(String chatId, String ref) {
        return firestore.collection(COLLECTION_NAME)
                .document(chatId)
                .update("lastMessage", ref);
    }
}
