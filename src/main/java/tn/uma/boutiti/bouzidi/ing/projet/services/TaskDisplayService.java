package tn.uma.boutiti.bouzidi.ing.projet.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.uma.boutiti.bouzidi.ing.projet.mapper.LabelMapper;
import tn.uma.boutiti.bouzidi.ing.projet.mapper.TaskMapper;
import tn.uma.boutiti.bouzidi.ing.projet.repository.TaskRepository;

@Service
@Transactional(readOnly = true)
public class TaskDisplayService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
  //  private final LabelMapper labelMapper;

    public TaskDisplayService(TaskRepository taskRepository, TaskMapper taskMapper, LabelMapper labelMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    //    this.labelMapper = labelMapper;
    }

    //public TaskDTO getTaskWithLabels(Long taskId) {
    //    Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
    //    return taskMapper.toTaskDTO(task);
    //}
   /* public TaskDTO getTaskWithLabels(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        // Assuming Task contains a Set of Label entities
        Set<LabelDTO> labelDTOs = task.getLabels()
                .stream()
                .map(labelMapper::toLabelDTO)
                .collect(Collectors.toSet());

        // Map Task to TaskDTO including labels
        return taskMapper.toTaskDTOWithLabels(task, labelDTOs);
    }*/
}
