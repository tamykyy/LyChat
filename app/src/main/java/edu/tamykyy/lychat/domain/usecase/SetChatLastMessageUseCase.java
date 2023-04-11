package edu.tamykyy.lychat.domain.usecase;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.repository.ChatRepository;
import io.reactivex.rxjava3.core.Completable;

public class SetChatLastMessageUseCase {

    private final ChatRepository chatRepository;

    @Inject
    public SetChatLastMessageUseCase(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Completable execute(String chatId, String messRef) {
        return chatRepository.setChatLastMessage(chatId, messRef);
    }
}
