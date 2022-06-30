package com.ces.intern.sunsama.util;

public enum ExceptionMessage {
    NOT_FOUND_HASHTAG("Not Found Hashtag"),
    TASK_NOT_EXIST("This task does not exist"),
    HASHTAG_ALREADY_EXIST("Hashtag Already Exist");
    private String message;
    ExceptionMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
