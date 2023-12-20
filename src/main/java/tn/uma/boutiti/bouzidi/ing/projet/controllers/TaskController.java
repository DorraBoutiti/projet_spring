package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.uma.boutiti.bouzidi.ing.projet.dto.LabelDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.services.LabelService;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskDisplayService;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;    
    @Autowired
    private LabelService labelService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;   
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO task = taskService.save(taskDTO);
        return ResponseEntity.ok().body(task);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> findAllTasks() {
        List<TaskDTO> tasks = taskService.findAll();
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> findOneTask(@PathVariable Long id) {
        TaskDTO task = taskService.findOne(id);
        return ResponseEntity.ok().body(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDTO) {
        if (taskDTO.getId() == null) {
            return createTask(taskDTO);
        }else {
            TaskDTO task = taskService.save(taskDTO);
            return ResponseEntity.ok().body(task);
        }
    } 
    
    @PutMapping("/{taskId}/assignLabel/{labelId}")
    public ResponseEntity<TaskDTO> assignLabelToTask(@PathVariable Long taskId, @PathVariable Long labelId) {
        TaskDTO task = taskService.findOne(taskId);
        LabelDTO label = labelService.findOne(labelId);

        if (task != null && label != null) {            
            List<LabelDTO> taskLabels = task.getLabels();
            taskLabels.add(label);            
            task.setLabels(taskLabels);
            taskService.save(task);
            return ResponseEntity.ok().body(task);
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }
    @PutMapping("/{taskId}/markAsCompleted")
    public ResponseEntity<TaskDTO> markTaskAsCompleted(@PathVariable Long taskId) {
        TaskDTO task = taskService.findOne(taskId);

        if (task != null) {            
            task.setCompleted(true);
            taskService.save(task); 
            return ResponseEntity.ok().body(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByProject(@PathVariable Long projectId) {
        List<TaskDTO> tasks = taskService.getTasksByProject(projectId);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build(); // Aucune tâche trouvée pour ce projet
        } else {
            return ResponseEntity.ok().body(tasks);
        }
    }
}
