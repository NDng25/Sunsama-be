package com.ces.intern.sunsama.service;

import com.ces.intern.sunsama.dto.TaskDTO;
import com.ces.intern.sunsama.http.request.TaskRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getAllTask();
    TaskDTO getTaskById(long id);
    TaskDTO createTask(TaskRequest request);
    TaskDTO updateTask(long id, TaskRequest request);
}
