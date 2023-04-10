package edu.tamykyy.lychat.domain.usecase;

import javax.inject.Inject;

import edu.tamykyy.lychat.domain.models.ChatDomainModel;
import edu.tamykyy.lychat.domain.repository.ChatRepository;
import io.reactivex.rxjava3.core.Observable;

public class GetUserChatsUseCase {

    private final ChatRepository chatRepository;

    @Inject
    public GetUserChatsUseCase(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Observable<ChatDomainModel> execute(String uid) {
        return chatRepository.getUserChats(uid);
    }
}
