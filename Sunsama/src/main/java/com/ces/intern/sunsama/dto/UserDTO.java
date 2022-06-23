package com.ces.intern.sunsama.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends BaseDTO{
    private String name;
    private String password;
    private Collection<TaskDTO> tasks;
}
