package com.ces.intern.sunsama.util;

public enum ExceptionMessage {
    NOT_FOUND_RECORD("Not Found Record"),
    Hashtag_ALREADY_EXIST("Hashtag Already Exist");

    private String message;
    ExceptionMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
