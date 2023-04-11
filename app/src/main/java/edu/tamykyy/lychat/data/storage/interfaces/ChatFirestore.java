package edu.tamykyy.lychat.data.storage.interfaces;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import edu.tamykyy.lychat.data.storage.models.ChatDataModel;

public interface ChatFirestore {

    Task<QuerySnapshot> getUserChatsQuery(String userUid);

    Task<QuerySnapshot> getChat(String uid1, String uid2);

    String getNewRef();

    Task<Void> saveChat(ChatDataModel chatDomainModel);

    Task<Void> update(String chatId, String ref);
}
