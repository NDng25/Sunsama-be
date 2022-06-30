package com.ces.intern.sunsama.util;

public enum ExceptionMessage {
<<<<<<< HEAD
    NOT_FOUND_HASHTAG("Not Found Hashtag"),
    Hashtag_ALREADY_EXIST("Hashtag Already Exist");

=======
    NOT_FOUND_HASHTAG("Hashtag not found"),
    HASHTAG_ALREADY_EXIST("Hashtag Already Exist"),
    TASK_NOT_EXIST("This task does not exist");
>>>>>>> 9a46798 ([BE-4] Update Task- Fix code review)
    private String message;
    ExceptionMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
