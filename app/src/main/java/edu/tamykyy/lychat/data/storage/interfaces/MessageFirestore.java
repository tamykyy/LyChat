package edu.tamykyy.lychat.data.storage.interfaces;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import edu.tamykyy.lychat.data.storage.models.MessageDataModel;

public interface MessageFirestore {

    Task<DocumentSnapshot> getMessage(String ref);

    Task<DocumentReference> sendMessage(String chatId, MessageDataModel messageDataModel);
}
