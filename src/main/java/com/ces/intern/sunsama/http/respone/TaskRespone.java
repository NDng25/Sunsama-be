package com.ces.intern.sunsama.http.respone;

import com.ces.intern.sunsama.dto.HashtagDTO;
import com.ces.intern.sunsama.dto.TaskDTO;
import com.ces.intern.sunsama.dto.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TaskRespone {
    private long id;
    private long parentId;
    private String title;
    private String describe;
    private Date date;
    private Date dueDate;
    private boolean isStatus;
    private Collection<TaskDTO> subTasks;
    private long userId;
    private Collection<HashtagDTO> hashtags;
}
