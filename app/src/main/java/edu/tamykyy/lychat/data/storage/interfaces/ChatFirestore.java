package edu.tamykyy.lychat.data.storage.interfaces;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

public interface ChatFirestore {

    Task<QuerySnapshot> getUserChatsQuery(String userUid);
}
