package tn.uma.boutiti.bouzidi.ing.projet.services.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.mapper.TaskMapper;
import tn.uma.boutiti.bouzidi.ing.projet.models.Task;
import tn.uma.boutiti.bouzidi.ing.projet.repository.TaskRepository;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    public List<TaskDTO> findAll() {
        return taskMapper.toDto(taskRepository.findAll());
        //        return taskRepository.findAll().stream().map(taskMapper::toDto).collect(java.util.stream.Collectors.toSet());
    }

    @Override
    public TaskDTO findOne(Long id) {
        Optional<Task> task =taskRepository.findById(id);
        return taskMapper.toDto(task.get());
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
