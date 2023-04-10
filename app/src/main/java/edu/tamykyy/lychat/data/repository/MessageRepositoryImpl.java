package edu.tamykyy.lychat.data.repository;


import java.io.FileNotFoundException;

import edu.tamykyy.lychat.data.storage.MessageFirestoreImpl;
import edu.tamykyy.lychat.domain.models.MessageDomainModel;
import edu.tamykyy.lychat.domain.repository.MessageRepository;
import io.reactivex.rxjava3.core.Single;

public class MessageRepositoryImpl implements MessageRepository {

    private final MessageFirestoreImpl messageFirestore;

    public MessageRepositoryImpl(MessageFirestoreImpl messageFirestore) {
        this.messageFirestore = messageFirestore;
    }

    @Override
    public Single<MessageDomainModel> getMessage(String ref) {
        return Single.create(emitter -> messageFirestore.getMessage(ref)
                .addOnSuccessListener(doc -> {
                    if (doc.exists())
                        emitter.onSuccess(doc.toObject(MessageDomainModel.class));
                    else emitter.onError(new FileNotFoundException("message is absent"));
                }).addOnFailureListener(emitter::onError)
        );
    }
}
