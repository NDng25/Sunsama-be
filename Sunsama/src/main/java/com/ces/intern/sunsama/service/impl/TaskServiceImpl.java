package com.ces.intern.sunsama.service.impl;

import com.ces.intern.sunsama.dto.TaskDTO;
import com.ces.intern.sunsama.entity.HashtagEntity;
import com.ces.intern.sunsama.entity.TaskEntity;
import com.ces.intern.sunsama.entity.UserEntity;
import com.ces.intern.sunsama.http.request.TaskRequest;
import com.ces.intern.sunsama.repository.HashtagRepository;
import com.ces.intern.sunsama.repository.TaskRepository;
import com.ces.intern.sunsama.repository.UserRepository;
import com.ces.intern.sunsama.service.TaskService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper, UserRepository userRepository, HashtagRepository hashtagRepository) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.hashtagRepository = hashtagRepository;
    }

    @Override
    public List<TaskDTO> getAllTask() {
        List<TaskDTO> tasks = taskRepository.findAll()
                .stream().map((t) -> modelMapper.map(t,TaskDTO.class)).collect(Collectors.toList());
        return tasks;
    }

    @Override
    @Transactional
    public TaskDTO createTask(TaskRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Missing required information"));
        Collection<HashtagEntity> hashtagEntities = new ArrayList<>();
        if(request.getHashtagsId().size() > 0){
            hashtagEntities = request.getHashtagsId().stream()
                    .map((id) -> hashtagRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Missing required information")))
                    .toList();
        }
        TaskEntity newTask = modelMapper.map(request,TaskEntity.class);
        System.out.println("newTask" + newTask.toString());
        newTask.setUser(user);
        newTask.setHashtags(hashtagEntities);
        TaskEntity taskCreated = taskRepository.save(newTask);
        log.debug("Task created: "+taskCreated);
        Collection<TaskEntity> userTasks = user.getTasks();
        if(userTasks == null){
            Collection<TaskEntity> tasks = new ArrayList<>();
            tasks.add(taskCreated);
            user.setTasks(tasks);
        }
        else{
            Collection<TaskEntity> tasks = user.getTasks();
            tasks.add(taskCreated);
        }
        return modelMapper.map(taskCreated, TaskDTO.class);
    }
}
