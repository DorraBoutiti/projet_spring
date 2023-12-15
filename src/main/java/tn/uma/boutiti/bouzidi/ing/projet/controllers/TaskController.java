package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.models.Task;
import tn.uma.boutiti.bouzidi.ing.projet.services.LabelService;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final LabelService labelService; 

    @Autowired
    public TaskController(TaskService taskService, LabelService labelService) {
        this.taskService = taskService;
        this.labelService = labelService;
    }

    // Créer une tâche avec des labels
    @PostMapping("/create")
    public Task createTask(@RequestBody TaskDTO taskDTO) {
        Task newTask = new Task(taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getDueDate(), taskDTO.isCompleted());

        // Récupération des labels depuis les IDs fournis dans le DTO
        List<Label> labels = labelService.findLabelsByIds(taskDTO.getLabelIds());
        newTask.setLabels(new HashSet<>(labels));

        return taskService.createTask(newTask);
    }
    //Afficher la liste des tâches à accomplir.

}
