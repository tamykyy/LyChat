package edu.tamykyy.lychat.domain.models;

import java.util.List;

public class ChatDomainModel {

    private String id;
    private String userUid1;
    private String userUid2;
    private MessageDomainModel lastMessage;

    public ChatDomainModel() {
    }

    public ChatDomainModel(String id, String userUid1, String userUid2, MessageDomainModel lastMessage) {
        this.id = id;
        this.userUid1 = userUid1;
        this.userUid2 = userUid2;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public MessageDomainModel getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(MessageDomainModel lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public String toString() {
        return "ChatDomainModel{" +
                "id='" + id + '\'' +
                ", userUid1='" + userUid1 + '\'' +
                ", userUid2='" + userUid2 + '\'' +
                ", lastMessage=" + lastMessage +
                '}';
    }
}
