package edu.tamykyy.lychat.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.models.MessageDomainModel;
import edu.tamykyy.lychat.domain.repository.MessageRepository;
import io.reactivex.rxjava3.core.Single;

public class GetMessageUpdatesUseCase {

    private MessageRepository messageRepository;

    @Inject
    public GetMessageUpdatesUseCase(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Single<List<MessageDomainModel>> execute(String chatId) {
        return messageRepository.getMessagesUpdates(chatId);
    }

}
