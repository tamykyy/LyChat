package edu.tamykyy.lychat.data.repository;

import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;


import edu.tamykyy.lychat.data.storage.ChatFirestoreImpl;
import edu.tamykyy.lychat.data.storage.models.ChatDataModel;
import edu.tamykyy.lychat.domain.models.ChatDomainModel;
import edu.tamykyy.lychat.domain.repository.ChatRepository;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class ChatRepositoryImpl implements ChatRepository {

    private final ChatFirestoreImpl firestore;
    private final MessageRepositoryImpl messageRepository;

    public ChatRepositoryImpl(ChatFirestoreImpl firestore, MessageRepositoryImpl messageRepository) {
        this.firestore = firestore;
        this.messageRepository = messageRepository;
    }

    @Override
    public Observable<ChatDomainModel> getUserChats(String uid) {
        return Observable.create(emitter -> firestore.getUserChatsQuery(uid)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult())
                            emitter.onNext(mapToDomain(document.toObject(ChatDataModel.class)));
                    } else {
                        emitter.onError(task.getException());
                    }
                })
        );
    }

    @Override
    public Single<ChatDomainModel> getChat(String uid1, String uid2) {
        return Single.create(emitter -> firestore.getChat(uid1, uid2)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult())
                            emitter.onSuccess(mapToDomain(document.toObject(ChatDataModel.class)));
                    } else {
                        emitter.onError(task.getException());
                    }
                })
        );
    }

    @Override
    public Single<ChatDomainModel> saveChat(ChatDomainModel chatDomainModel) {
        chatDomainModel.setId(firestore.getNewRef());

        return Single.create(emitter -> firestore.saveChat(mapToData(chatDomainModel))
                .addOnSuccessListener(unused -> emitter.onSuccess(chatDomainModel))
                .addOnFailureListener(emitter::onError)
        );
    }

    @Override
    public Completable setChatLastMessage(String chatId, String ref) {
        return Completable.create(emitter -> firestore.update(chatId, ref)
                .addOnSuccessListener(unused -> emitter.onComplete())
                .addOnFailureListener(emitter::onError)
        );
    }

    private ChatDomainModel mapToDomain(ChatDataModel chatDataModel) {
        ChatDomainModel chatDomainModel = new ChatDomainModel();

        chatDomainModel.setId(chatDataModel.getId());
        chatDomainModel.setUserUid1(chatDataModel.getUserUid1());
        chatDomainModel.setUserUid2(chatDataModel.getUserUid2());

        if (chatDataModel.getLastMessage() == null) return chatDomainModel;

        Disposable disposable = messageRepository.getMessage(chatDataModel.getLastMessage()).subscribe(
                chatDomainModel::setLastMessage,
                throwable -> Log.d("AAA", throwable.getMessage())
        );

        return chatDomainModel;
    }

    private ChatDataModel mapToData(ChatDomainModel chatDomainModel) {
        ChatDataModel chatDataModel = new ChatDataModel();

        chatDataModel.setId(chatDomainModel.getId());
        chatDataModel.setUserUid1(chatDomainModel.getUserUid1());
        chatDataModel.setUserUid2(chatDomainModel.getUserUid2());
        return chatDataModel;
    }


}
