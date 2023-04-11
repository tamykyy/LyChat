package edu.tamykyy.lychat.domain.usecase;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.models.MessageDomainModel;
import edu.tamykyy.lychat.domain.repository.MessageRepository;
import io.reactivex.rxjava3.core.Single;

public class SendMessageUseCase {

    private final MessageRepository messageRepository;

    @Inject
    public SendMessageUseCase(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Single<String> execute(String chatId, MessageDomainModel messageDomainModel) {
        return messageRepository.sendMessage(chatId, messageDomainModel);
    }
}
