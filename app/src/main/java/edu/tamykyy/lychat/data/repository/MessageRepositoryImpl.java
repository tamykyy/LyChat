package edu.tamykyy.lychat.data.repository;


import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import edu.tamykyy.lychat.data.storage.MessageFirestoreImpl;
import edu.tamykyy.lychat.data.storage.models.MessageDataModel;
import edu.tamykyy.lychat.domain.models.MessageDomainModel;
import edu.tamykyy.lychat.domain.repository.MessageRepository;
import io.reactivex.rxjava3.core.Single;

public class MessageRepositoryImpl implements MessageRepository {

    private final MessageFirestoreImpl firestore;

    @Inject
    public MessageRepositoryImpl(MessageFirestoreImpl firestore) {
        this.firestore = firestore;
    }

    @Override
    public Single<MessageDomainModel> getMessage(String ref) {
        return Single.create(emitter -> firestore.getMessage(ref)
                .addOnSuccessListener(doc -> {
                    if (doc.exists())
                        emitter.onSuccess(doc.toObject(MessageDomainModel.class));
                    else emitter.onError(new FileNotFoundException("message is absent"));
                }).addOnFailureListener(emitter::onError)
        );
    }

    @Override
    public Single<String> sendMessage(String chatId, MessageDomainModel messageDomainModel) {
        return Single.create(emitter -> firestore.sendMessage(chatId, mapToData(messageDomainModel))
                .addOnSuccessListener(doc -> emitter.onSuccess(doc.getId()))
                .addOnFailureListener(emitter::onError)
        );
    }

    @Override
    public Single<List<MessageDomainModel>> getMessagesUpdates(String chatId) {
        return Single.create(emitter -> firestore.getMessageCollectionRef(chatId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.w("AAA", "Listen failed.", error);
                        emitter.onError(error);
                        return;
                    }

                    List<MessageDomainModel> massagesList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : value) {
                        massagesList.add(mapToDomain(doc.toObject(MessageDomainModel.class)));
                    }
                    emitter.onSuccess(massagesList);
                })
        );
    }

    private MessageDomainModel mapToDomain(MessageDomainModel messageDomainModel) {
        return new MessageDomainModel(
                messageDomainModel.getSenderUid(),
                messageDomainModel.getText(),
                messageDomainModel.getTime(),
                messageDomainModel.getStatus()
        );
    }

    private MessageDataModel mapToData(MessageDomainModel messageDomainModel) {
        return new MessageDataModel(
                messageDomainModel.getSenderUid(),
                messageDomainModel.getText(),
                messageDomainModel.getTime(),
                messageDomainModel.getStatus()
        );
    }
}
