package edu.tamykyy.lychat.domain.usecase;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.models.ChatDomainModel;
import edu.tamykyy.lychat.domain.repository.ChatRepository;
import io.reactivex.rxjava3.core.Single;

public class GetChatUseCase {

    private final ChatRepository chatRepository;

    @Inject
    public GetChatUseCase(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Single<ChatDomainModel> execute(String uid1, String uid2) {
        return chatRepository.getChat(uid1, uid2);
    }
}
