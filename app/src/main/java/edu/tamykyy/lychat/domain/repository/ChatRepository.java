package edu.tamykyy.lychat.domain.repository;

import edu.tamykyy.lychat.domain.models.ChatDomainModel;
import io.reactivex.rxjava3.core.Observable;

public interface ChatRepository {

    Observable<ChatDomainModel> getUserChats(String uid);
}
