package tn.uma.boutiti.bouzidi.ing.projet.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.exceptions.EntityNotFoundException;
import tn.uma.boutiti.bouzidi.ing.projet.mapper.TaskMapper;
import tn.uma.boutiti.bouzidi.ing.projet.models.Task;
import tn.uma.boutiti.bouzidi.ing.projet.repository.TaskRepository;

@Service
@Transactional(readOnly = true)
public class TaskDisplayService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskDisplayService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskDTO getTaskWithLabels(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        return taskMapper.toTaskDTO(task);
    }
}
