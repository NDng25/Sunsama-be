package com.ces.intern.sunsama.util;

public enum ExceptionMessage {
    NOT_FOUND_HASHTAG("Not Found Hashtag"),
    Hashtag_ALREADY_EXIST("Hashtag Already Exist");

    private String message;
    ExceptionMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
