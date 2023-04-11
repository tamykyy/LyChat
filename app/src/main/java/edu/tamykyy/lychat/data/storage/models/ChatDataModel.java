package edu.tamykyy.lychat.data.storage.models;

import java.util.List;

import edu.tamykyy.lychat.domain.models.MessageDomainModel;

public class ChatDataModel {

    private String id;
    private String userUid1;
    private String userUid2;
    private String lastMessage;

    public ChatDataModel() {
    }

    public ChatDataModel(String id, String userUid1, String userUid2, String lastMessage) {
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

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
