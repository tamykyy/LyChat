package edu.tamykyy.lychat.domain.models;

import com.google.firebase.Timestamp;

public class MessageDomainModel {

    private String senderUid;
    private String text;
    private Timestamp time;
    private String status;

    public MessageDomainModel() {
    }

    public MessageDomainModel(String senderUid, String text, Timestamp time, String status) {
        this.senderUid = senderUid;
        this.text = text;
        this.time = time;
        this.status = status;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MessageDomainModel{" +
                "senderUid='" + senderUid + '\'' +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
