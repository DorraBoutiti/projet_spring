package tn.uma.boutiti.bouzidi.ing.projet.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tn.uma.boutiti.bouzidi.ing.projet.exceptions.EntityNotFoundException;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;
import tn.uma.boutiti.bouzidi.ing.projet.models.Task;
import tn.uma.boutiti.bouzidi.ing.projet.repository.LabelRepository;
import tn.uma.boutiti.bouzidi.ing.projet.repository.ProjectRepository;
import tn.uma.boutiti.bouzidi.ing.projet.repository.TaskRepository;

@Service
@Transactional
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final LabelRepository labelRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, LabelRepository labelRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.labelRepository = labelRepository;
        this.projectRepository = projectRepository;
    }
    
    public Task createTask(String title, String description, LocalDate dueDate, boolean completed) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setCompleted(completed);
        return taskRepository.save(task);
    }
    
   
    
    public void addLabelToTask(Long taskId, Long labelId) {
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
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
}
