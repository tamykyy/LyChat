package edu.tamykyy.lychat.data.repository;

import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;


import edu.tamykyy.lychat.data.storage.ChatFirestoreImpl;
import edu.tamykyy.lychat.data.storage.models.ChatDataModel;
import edu.tamykyy.lychat.domain.models.ChatDomainModel;
import edu.tamykyy.lychat.domain.repository.ChatRepository;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class ChatRepositoryImpl implements ChatRepository {

    private final ChatFirestoreImpl chatFirestore;
    private final MessageRepositoryImpl messageRepository;

    public ChatRepositoryImpl(ChatFirestoreImpl chatFirestore, MessageRepositoryImpl messageRepository) {
        this.chatFirestore = chatFirestore;
        this.messageRepository = messageRepository;
    }

    @Override
    public Observable<ChatDomainModel> getUserChats(String uid) {
        return Observable.create(emitter -> chatFirestore.getUserChatsQuery(uid)
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

    private ChatDomainModel mapToDomain(ChatDataModel chatDataModel) {
        ChatDomainModel chatDomainModel = new ChatDomainModel();

        chatDomainModel.setUserUid1(chatDataModel.getUserUid1());
        chatDomainModel.setUserUid2(chatDataModel.getUserUid2());
        chatDomainModel.setMessages(chatDataModel.getMessages());

        if (chatDataModel.getLastMessage().isEmpty()) return chatDomainModel;

        Disposable disposable = messageRepository.getMessage(chatDataModel.getLastMessage()).subscribe(
                chatDomainModel::setLastMessage,
                throwable -> Log.d("AAA", throwable.getMessage())
        );

        return chatDomainModel;
    }




}
