package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.exceptions.ProjectNotFoundException;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;
import tn.uma.boutiti.bouzidi.ing.projet.models.Task;
import tn.uma.boutiti.bouzidi.ing.projet.services.LabelService;
import tn.uma.boutiti.bouzidi.ing.projet.services.ProjectService;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskDisplayService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskDisplayService taskDisplayService;

    public TaskController(TaskService taskService, TaskDisplayService taskDisplayService) {
        this.taskService = taskService;
        this.taskDisplayService = taskDisplayService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTask(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam LocalDate dueDate,
            @RequestParam boolean completed
    ) {
        Task task = taskService.createTask(title, description, dueDate, completed);
        return ResponseEntity.ok("Task created with ID: " + task.getId());
    }

    
    @PostMapping("/labels/add")
    public ResponseEntity<String> addLabelToTask(@RequestParam Long taskId, @RequestParam Long labelId) {
        taskService.addLabelToTask(taskId, labelId);
        return ResponseEntity.ok("Label added to the task successfully");
    }
    
    @GetMapping("/{taskId}/with-labels")
    public ResponseEntity<TaskDTO> getTaskWithLabels(@PathVariable Long taskId) {
        TaskDTO taskDTO = taskDisplayService.getTaskWithLabels(taskId);
        return ResponseEntity.ok(taskDTO);
    }    
    
}
