package com.ces.intern.sunsama.service.impl;

import com.ces.intern.sunsama.dto.HashtagDTO;
import com.ces.intern.sunsama.dto.TaskDTO;
import com.ces.intern.sunsama.entity.HashtagEntity;
import com.ces.intern.sunsama.entity.TaskEntity;
import com.ces.intern.sunsama.entity.UserEntity;
import com.ces.intern.sunsama.http.exception.AlreadyExistException;
import com.ces.intern.sunsama.http.exception.BadRequestException;
import com.ces.intern.sunsama.http.exception.NotFoundException;
import com.ces.intern.sunsama.http.request.SubtaskRequest;
import com.ces.intern.sunsama.http.request.TaskRequest;
import com.ces.intern.sunsama.http.response.TaskResponse;
import com.ces.intern.sunsama.repository.HashtagRepository;
import com.ces.intern.sunsama.repository.TaskRepository;
import com.ces.intern.sunsama.repository.UserRepository;
import com.ces.intern.sunsama.service.TaskService;
import com.ces.intern.sunsama.util.DateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
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
        List<TaskEntity> tasks = taskRepository.getAllParentTask();
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
        if(!request.getHashtagsId().isEmpty()){
            hashtagEntities = request.getHashtagsId().stream()
                    .map((id) -> hashtagRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Invalid hashtag id "+ id)))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        TaskEntity newTask = modelMapper.map(request,TaskEntity.class);
        newTask.setUser(user);
        newTask.setHashtags(new ArrayList<>());
        hashtagEntities.forEach(tag -> newTask.getHashtags().add(tag));
        TaskEntity taskCreated = taskRepository.save(newTask);
        Collection<TaskEntity> userTasks = user.getTasks();
        if(userTasks == null){
            Collection<TaskEntity> tasks = new ArrayList<>();
            tasks.add(taskCreated);
            user.setTasks(tasks);
        }
        else{
            Collection<TaskEntity> tasks = user.getTasks();
            tasks.add(taskCreated);
            user.setTasks(tasks);
        }
        return modelMapper.map(taskCreated, TaskDTO.class);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void deleteTask(long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invalid id"));
        taskEntity.getUser().getTasks().remove(taskEntity);
        taskRepository.deleteAllSubtasksOfTask(taskEntity.getId());
        taskRepository.deleteById(taskEntity.getId());
    }

    @Override
    public List<HashtagDTO> getHashtagByTaskId(long id) {
        List<HashtagEntity> hashtagEntities = hashtagRepository.getHashtagByTaskId(id);
        return hashtagEntities.stream().map(hashtagEntity -> modelMapper.map(hashtagEntity,HashtagDTO.class)).toList();
    }

    @Override
    @Transactional
    public void addHashtagToTask(long taskId, long hashtagId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));
        HashtagEntity hashtag = hashtagRepository.findById(hashtagId)
                .orElseThrow(() -> new NotFoundException("Hashtag not found"));
        if(task.getHashtags().stream().anyMatch((hashtagEntity -> hashtagEntity.getId() == hashtag.getId())))
            throw new AlreadyExistException("Hashtag already exist in task");
        task.getHashtags().add(hashtag);
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void removeHashtagFromTask(long taskId, long hashtagId) {
        TaskEntity task = taskRepository.getTaskHasHashtag(taskId, hashtagId);
        if(task == null)
            throw new NotFoundException("Does not exist task have this hashtag");
        HashtagEntity hashtag = hashtagRepository.findById(hashtagId).get();
        task.getHashtags().remove(hashtag);
        taskRepository.save(task);
    }

    @Override
    public List<TaskDTO> getTasksByDate(String dateStr) {
        List<TaskEntity> taskEntities = taskRepository.getTasksByDate(dateStr);
        return taskEntities.stream().map(task -> modelMapper.map(task, TaskDTO.class)).toList();
    }

    @Override
    public List<TaskDTO> getTasksByDueDate(String dateStr) {
        List<TaskEntity> taskEntities = taskRepository.getTasksByDueDate(dateStr);
        return taskEntities.stream().map(task -> modelMapper.map(task, TaskDTO.class)).toList();
    }

    @Override
    public List<TaskDTO> getSubtasksOfTask(long taskId) {
        List<TaskEntity> taskEntities = taskRepository.getSubtaskOfTask(taskId);
        return taskEntities.stream().map(task -> modelMapper.map(task, TaskDTO.class)).toList();
    }

    @Override
    @Transactional
    public TaskDTO addSubtaskToTask(long taskId, SubtaskRequest request) {
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task does not exist"));
        if(taskEntity.getParentId()!=0) throw new BadRequestException("This task already is a subtask");
        if(request.getTitle()==null)
            throw new BadRequestException("Missing title");
        UserEntity user = userRepository.findById(taskEntity.getUser().getId()).get();
        List<HashtagEntity> hashtags = hashtagRepository.getHashtagByTaskId(taskEntity.getId());
        TaskEntity newSubtask = modelMapper.map(request, TaskEntity.class);
        newSubtask.setParentId(taskEntity.getId());
        newSubtask.setDate(taskEntity.getDate());
        newSubtask.setDueDate(taskEntity.getDueDate());
        newSubtask.setStatus(taskEntity.isStatus());
        newSubtask.setUser(user);
        newSubtask.setHashtags(hashtags);
        TaskEntity createdTask = taskRepository.save(newSubtask);
        return modelMapper.map(createdTask, TaskDTO.class);
    }

    @Override
    @Transactional
    public void setTaskComplete(long taskId) {
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not exist"));
        List<TaskEntity> subtasks = new ArrayList<>();
        if(task.getParentId() == 0){
            subtasks = taskRepository.findAll().stream().filter(st -> st.getParentId()==task.getId()).toList();
            if(!subtasks.isEmpty()){
                for (TaskEntity subtask :
                        subtasks) {
                    subtask.setStatus(true);
                }
                taskRepository.saveAll(subtasks);
            }
            task.setStatus(true);
        }
        else{
            task.setStatus(true);
        }
        taskRepository.save(task);
    }
}
