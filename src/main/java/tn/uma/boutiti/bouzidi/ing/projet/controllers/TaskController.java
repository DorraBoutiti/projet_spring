package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskDisplayService;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskDisplayService taskDisplayService;




    public TaskController(TaskService taskService, TaskDisplayService taskDisplayService) {
        this.taskService = taskService;
        this.taskDisplayService = taskDisplayService;

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

    
  /*  @PostMapping("/labels/add")
    public ResponseEntity<String> addLabelToTask(@RequestParam Long taskId, @RequestParam Long labelId) {
        taskService.addLabelToTask(taskId, labelId);
        return ResponseEntity.ok("Label added to the task successfully");
    }*/
    
   /* @GetMapping("/{taskId}/with-labels")
    public ResponseEntity<TaskDTO> getTaskWithLabels(@PathVariable Long taskId) {
      //  TaskDTO taskDTO = taskDisplayService.getTaskWithLabels(taskId);
      //  return ResponseEntity.ok(taskDTO);
        return null;
    }*/


    
}
