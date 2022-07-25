package com.ces.intern.sunsama.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Collection;
@Getter
@Setter
@NoArgsConstructor
public class HashtagDTO extends BaseDTO{
    private String name;
    private Collection<TaskDTO> tasks;
}
