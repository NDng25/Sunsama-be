package com.ces.intern.sunsama.http.request;

import com.ces.intern.sunsama.http.response.HashtagResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TaskRequest {
    private String title;
    private String describe;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date dueDate;
    private Collection<HashtagResponse> hashtags;
    private boolean isStatus;
    private long parentId;
    private long userId;
    private List<Long> hashtagsId;

}
