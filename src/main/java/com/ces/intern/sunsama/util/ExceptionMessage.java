package com.ces.intern.sunsama.util;

public enum ExceptionMessage {
    NOT_FOUND_HASHTAG("Hashtag not found"),
    HASHTAG_ALREADY_EXIST("Hashtag Already Exist"),
    TASK_NOT_EXIST("This task does not exist");
    private String message;
    ExceptionMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
