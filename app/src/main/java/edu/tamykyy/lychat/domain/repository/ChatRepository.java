package edu.tamykyy.lychat.domain.repository;

import edu.tamykyy.lychat.domain.models.ChatDomainModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface ChatRepository {

    Observable<ChatDomainModel> getUserChats(String uid);

    Single<ChatDomainModel> getChat(String uid1, String uid2);

    Single<ChatDomainModel> saveChat(ChatDomainModel chatDomainModel);

    Completable setChatLastMessage(String chatId, String ref);
}
