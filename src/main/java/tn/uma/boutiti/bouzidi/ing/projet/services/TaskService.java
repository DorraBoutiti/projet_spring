package tn.uma.boutiti.bouzidi.ing.projet.services;

import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;

import java.util.List;
import java.util.Set;


public interface TaskService {

    TaskDTO save (TaskDTO taskDTO);

    List<TaskDTO> findAll();

    TaskDTO findOne(Long id);

    void delete(Long id);

    
   
    
   /* public void addLabelToTask(Long taskId, Long labelId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        Label label = labelRepository.findById(labelId).orElseThrow(() -> new EntityNotFoundException("Label not found"));
        
        task.getLabels().add(label);
        label.getTasks().add(task);
    }
    public void addProjectToTask(Long projectID, Long taskID) {
    	Task task = taskRepository.findById(taskID).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        Project project = projectRepository.findById(projectID).orElseThrow(() -> new EntityNotFoundException("Label not found"));
        
        task.setProject(project);
        project.getTasks().add(task);
    }
    public Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElse(null);
    }*/


}
