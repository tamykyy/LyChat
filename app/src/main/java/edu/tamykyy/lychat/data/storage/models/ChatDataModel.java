package edu.tamykyy.lychat.data.storage.models;

import java.util.List;

import edu.tamykyy.lychat.domain.models.MessageDomainModel;

public class ChatDataModel {

    private String userUid1;
    private String userUid2;
    private List<MessageDomainModel> messages;
    private String lastMessage;

    public ChatDataModel() {
    }

    public ChatDataModel(String userUid1, String userUid2, List<MessageDomainModel> messages, String lastMessage) {
        this.userUid1 = userUid1;
        this.userUid2 = userUid2;
        this.messages = messages;
        this.lastMessage = lastMessage;
    }

    public String getUserUid1() {
        return userUid1;
    }

    public void setUserUid1(String userUid1) {
        this.userUid1 = userUid1;
    }

    public String getUserUid2() {
        return userUid2;
    }

    public void setUserUid2(String userUid2) {
        this.userUid2 = userUid2;
    }

    public List<MessageDomainModel> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDomainModel> messages) {
        this.messages = messages;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public String toString() {
        return "ChatDomainModel{" +
                "userUid1='" + userUid1 + '\'' +
                ", userUid2='" + userUid2 + '\'' +
                ", messages=" + messages +
                ", lastMessage=" + lastMessage +
                '}';
    }
}
