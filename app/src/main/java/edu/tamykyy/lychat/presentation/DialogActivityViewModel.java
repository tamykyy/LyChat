package edu.tamykyy.lychat.presentation;

import androidx.lifecycle.ViewModel;

import com.google.firebase.Timestamp;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.tamykyy.lychat.domain.models.ChatDomainModel;
import edu.tamykyy.lychat.domain.models.MessageDomainModel;
import edu.tamykyy.lychat.domain.models.UserDomainModel;
import edu.tamykyy.lychat.domain.usecase.GetChatUseCase;
import edu.tamykyy.lychat.domain.usecase.GetMessageUpdatesUseCase;
import edu.tamykyy.lychat.domain.usecase.GetUserUpdatesUseCase;
import edu.tamykyy.lychat.domain.usecase.SaveChatUseCase;
import edu.tamykyy.lychat.domain.usecase.SendMessageUseCase;
import edu.tamykyy.lychat.domain.usecase.SetChatLastMessageUseCase;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class DialogActivityViewModel extends ViewModel {

    private final GetUserUpdatesUseCase getUserUpdatesUseCase;
    private final GetChatUseCase getChatUseCase;
    private final SaveChatUseCase saveChatUseCase;
    private final SendMessageUseCase sendMessageUseCase;
    private final GetMessageUpdatesUseCase getMessageUpdatesUseCase;
    private final SetChatLastMessageUseCase setChatLastMessageUseCase;

    @Inject
    public DialogActivityViewModel(GetUserUpdatesUseCase getUserUpdatesUseCase, GetChatUseCase getChatUseCase, SaveChatUseCase saveChatUseCase, SendMessageUseCase sendMessageUseCase, GetMessageUpdatesUseCase getMessageUpdatesUseCase, SetChatLastMessageUseCase setChatLastMessageUseCase) {
        this.getUserUpdatesUseCase = getUserUpdatesUseCase;
        this.getChatUseCase = getChatUseCase;
        this.saveChatUseCase = saveChatUseCase;
        this.sendMessageUseCase = sendMessageUseCase;
        this.getMessageUpdatesUseCase = getMessageUpdatesUseCase;
        this.setChatLastMessageUseCase = setChatLastMessageUseCase;
    }

    public Observable<UserDomainModel> getUserUpdates(String uid) {
        return getUserUpdatesUseCase.execute(uid);
    }

    public Single<ChatDomainModel> getChat(String uid1, String uid2) {
        return getChatUseCase.execute(uid1, uid2);
    }

    public Single<ChatDomainModel> createNewChat(String uid1, String uid2) {
        ChatDomainModel chatDomainModel = new ChatDomainModel();
        chatDomainModel.setUserUid1(uid1);
        chatDomainModel.setUserUid2(uid2);
        return saveChatUseCase.execute(chatDomainModel);
    }

    public Single<String> sendMessage(String chatId, String senderUid, String messageText) {
        MessageDomainModel messageDomainModel = new MessageDomainModel();
        messageDomainModel.setSenderUid(senderUid);
        messageDomainModel.setText(messageText);
        messageDomainModel.setTime(Timestamp.now());
        messageDomainModel.setStatus("send");

        return sendMessageUseCase.execute(chatId, messageDomainModel);
    }

    public Single<List<MessageDomainModel>> getMessageUpdates(String chatId) {
        return getMessageUpdatesUseCase.execute(chatId);
    }

    public Completable setChatLastMessage(String chatId, String messRef) {
        return setChatLastMessageUseCase.execute(chatId, messRef);
    }
}
