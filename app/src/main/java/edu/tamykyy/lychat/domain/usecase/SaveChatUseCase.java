package edu.tamykyy.lychat.domain.usecase;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.models.ChatDomainModel;
import edu.tamykyy.lychat.domain.repository.ChatRepository;
import io.reactivex.rxjava3.core.Single;

public class SaveChatUseCase {

    private final ChatRepository chatRepository;

    @Inject
    public SaveChatUseCase(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Single<ChatDomainModel> execute(ChatDomainModel chatDomainModel) {
        return chatRepository.saveChat(chatDomainModel);
    }
}
