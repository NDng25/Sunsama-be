package com.ces.intern.sunsama.service.impl;

import com.ces.intern.sunsama.SunsamaApplication;
import com.ces.intern.sunsama.dto.HashtagDTO;
import com.ces.intern.sunsama.dto.TaskDTO;
import com.ces.intern.sunsama.entity.HashtagEntity;
import com.ces.intern.sunsama.entity.TaskEntity;
import com.ces.intern.sunsama.entity.UserEntity;
import com.ces.intern.sunsama.http.exception.NotFoundException;
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
    private final Logger log = LoggerFactory.getLogger(SunsamaApplication.class);
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
        List<TaskEntity> tasks = taskRepository.findAll();
        return tasks.stream().map(task -> modelMapper.map(task, TaskDTO.class)).collect(Collectors.toList());
    }

    @Override
    public TaskDTO getTaskById(long id) {
        TaskEntity task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Invalid id"));
        return modelMapper.map(task,TaskDTO.class);
    }

    @Override
    @Transactional
    public TaskDTO createTask(TaskRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Invalid userId"));
        Collection<HashtagEntity> hashtagEntities = new ArrayList<>();
        if(request.getHashtagsId().size() > 0){
            System.out.println("hashtag_id"+request.getHashtagsId());
            hashtagEntities = request.getHashtagsId().stream()
                    .map((id) -> hashtagRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Invalid hashtag id "+ id.toString())))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        TaskEntity newTask = modelMapper.map(request,TaskEntity.class);
        newTask.setUser(user);
        newTask.setHastags(new ArrayList<>());
        hashtagEntities.forEach(tag -> newTask.getHastags().add(tag));
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

    @Override
    public TaskDTO updateTask(long id, TaskRequest request) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invalid id"));
        task.setTitle(request.getTitle());
        task.setDescribe(request.getDescribe());
        task.setDate(request.getDate());
        task.setDueDate(request.getDueDate());
        task = taskRepository.save(task);
        return modelMapper.map(task, TaskDTO.class);
    }
}
