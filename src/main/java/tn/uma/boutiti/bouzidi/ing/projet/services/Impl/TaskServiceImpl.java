package tn.uma.boutiti.bouzidi.ing.projet.services.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.mapper.TaskMapper;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;
import tn.uma.boutiti.bouzidi.ing.projet.models.Task;
import tn.uma.boutiti.bouzidi.ing.projet.repository.TaskRepository;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public List<TaskDTO> getTasksByProject(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);

        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByLabel(Long labelId) {
        List<Task> tasks = taskRepository.findByLabelsId(labelId); 

        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO toTrash(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setArchived(true);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    public TaskDTO toListTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setArchived(false);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }
    @Override
    public List<TaskDTO> getTasksByLabelFiltred(Long labelId) {
        LocalDate currentDate = LocalDate.now();
        List<Task> tasks = taskRepository.findByLabelsIdAndDueDateGreaterThan(labelId, currentDate); 
return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }
    @Override
    public List<TaskDTO> getTasksByLabelAndProject(Long labelId, Long projectId) {
        LocalDate currentDate = LocalDate.now();
        List<Task> tasks = taskRepository.findByLabelsIdAndDueDateGreaterThanAndProjectId(labelId, currentDate, projectId); 

        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }

	@Override
	public List<TaskDTO> getTasksByDueDate(LocalDate dueDate) {
        List<Task> tasks = taskRepository.findByDueDateGreaterThanEqual(dueDate); 

        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }
	@Override
    public List<TaskDTO> getTasksByDueDateAndProjectSortedByDueDateDescending(LocalDate dueDate, Long projectId) {
        List<Task> tasks = taskRepository.findByDueDateGreaterThanEqualAndProjectIdOrderByDueDateDesc(dueDate, projectId); // Supposons une méthode findByDueDateGreaterThanEqualAndProjectIdOrderByDueDateDesc dans le repository

        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }
	@Override
    public List<TaskDTO> getTasksByStartDateAndProjectSortedByStartDateAscending(LocalDate startDate, Long projectId) {
        List<Task> tasks = taskRepository.findByStartDateGreaterThanEqualAndProjectIdOrderByStartDateAsc(startDate, projectId); // Supposons une méthode findByStartDateGreaterThanEqualAndProjectIdOrderByStartDateAsc dans le repository

        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }

	@Override
    public List<TaskDTO> getTasksByCompletedAndProjectSortedByDueDateAscending(Boolean completed, Long projectId) {
        List<Task> tasks = taskRepository.findByCompletedAndProjectIdOrderByDueDateAsc(completed, projectId); // Supposons une méthode findByCompletedAndProjectIdOrderByDueDateAsc dans le repository

        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }
	@Override
    public Map<Label, Long> countTasksByProjectId(Long projectId) {
        return taskRepository.countTasksByProjectId(projectId);
    }
	
}
