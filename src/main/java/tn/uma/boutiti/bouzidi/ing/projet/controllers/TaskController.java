package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.exceptions.ProjectNotFoundException;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;
import tn.uma.boutiti.bouzidi.ing.projet.models.Task;
import tn.uma.boutiti.bouzidi.ing.projet.services.LabelService;
import tn.uma.boutiti.bouzidi.ing.projet.services.ProjectService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final LabelService labelService; 
    private final ProjectService projectService;

   @Autowired
   public TaskController(TaskService taskService, LabelService labelService, ProjectService projectService) {
       this.taskService = taskService;
       this.labelService = labelService;
       this.projectService = projectService;
    }

    
    @PostMapping("/create")
    public Task createTask(@RequestBody TaskDTO taskDTO) {
        Optional<Project> optionalProject = projectService.getProjectById(taskDTO.getProjectId());

        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();

            Task newTask = new Task();
            newTask.setTitle(taskDTO.getTitle());
            newTask.setDescription(taskDTO.getDescription());
            newTask.setDueDate(taskDTO.getDueDate());
            newTask.setCompleted(taskDTO.isCompleted());
            newTask.setProject(project); 

            List<Label> labels = labelService.findLabelsByIds(taskDTO.getLabelIds());
            newTask.setLabels(new HashSet<>(labels));

            return taskService.createTask(newTask);
        } else {            
            throw new ProjectNotFoundException("Project with ID " + taskDTO.getProjectId() + " not found");
        }
    }


}
