package com.ces.intern.sunsama.http.response;

import com.ces.intern.sunsama.dto.HashtagDTO;
import com.ces.intern.sunsama.dto.TaskDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponse {
    private long id;
    private long parentId;
    private String title;
    private String describe;
    private Date date;
    private Date dueDate;
    private boolean isStatus;
    private Collection<TaskDTO> subTasks = new ArrayList<>();
    private long userId;
    private Collection<HashtagDTO> hashtags = new ArrayList<>();
}
