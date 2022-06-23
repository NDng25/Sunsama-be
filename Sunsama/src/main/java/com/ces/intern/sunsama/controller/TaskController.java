package com.ces.intern.sunsama.controller;

import com.ces.intern.sunsama.dto.TaskDTO;
import com.ces.intern.sunsama.http.exception.BadRequestException;
import com.ces.intern.sunsama.http.request.TaskRequest;
import com.ces.intern.sunsama.http.respone.TaskRespone;
import com.ces.intern.sunsama.service.TaskService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public List<TaskRespone> getAllTasks(){
        List<TaskDTO> taskDTOS = taskService.getAllTask();
        return taskDTOS.stream()
                .map(task -> modelMapper.map(task,TaskRespone.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public TaskRespone createTask(@RequestBody TaskRequest request){
        if(request.getTitle() == null) throw new BadRequestException("Request missing task's title");
        TypeMap<TaskDTO, TaskRespone> propertyMapper = this.modelMapper.createTypeMap(TaskDTO.class, TaskRespone.class);
        propertyMapper.addMappings(mapper -> mapper.map(src -> src.getUser().getId(), TaskRespone::setUserId));
        TaskDTO createdTask = taskService.createTask(request);
        return modelMapper.map(createdTask,TaskRespone.class);
    }

}
