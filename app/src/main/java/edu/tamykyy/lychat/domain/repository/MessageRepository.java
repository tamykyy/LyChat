package edu.tamykyy.lychat.domain.repository;

import java.util.List;

import edu.tamykyy.lychat.domain.models.MessageDomainModel;
import io.reactivex.rxjava3.core.Single;

public interface MessageRepository {

    Single<MessageDomainModel> getMessage(String ref);

    Single<String> sendMessage(String chatId, MessageDomainModel messageDomainModel);

    Single<List<MessageDomainModel>> getMessagesUpdates(String chatId);
}
