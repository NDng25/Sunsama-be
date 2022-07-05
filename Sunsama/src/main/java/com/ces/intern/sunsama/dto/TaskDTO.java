package com.ces.intern.sunsama.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TaskDTO extends BaseDTO{
    private Long parentId;
    private String title;
    private String describe;
    private Date date;
    private Date dueDate;
    private boolean status;
    private UserDTO user;
    private Collection<HashtagDTO> hashtags;
}
