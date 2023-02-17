package edu.tamykyy.lychat.data.storage.models;

public final class Response<T> {

    private boolean SUCCESS;
    private boolean FAIL;
    private String message;
    private T data = null;

    public Response() {
    }

    public Response(boolean SUCCESS, boolean FAIL, String message) {
        this.SUCCESS = SUCCESS;
        this.FAIL = FAIL;
        this.message = message;
    }

    public Response(boolean SUCCESS, boolean FAIL, String message, T data) {
        this.SUCCESS = SUCCESS;
        this.FAIL = FAIL;
        this.message = message;
        this.data = data;
    }

    public boolean SUCCESS() {
        return SUCCESS;
    }

    public boolean FAIL() {
        return FAIL;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setSUCCESS(boolean SUCCESS) {
        this.SUCCESS = SUCCESS;
    }

    public void setFAIL(boolean FAIL) {
        this.FAIL = FAIL;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
