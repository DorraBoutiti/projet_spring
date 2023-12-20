package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.uma.boutiti.bouzidi.ing.projet.dto.LabelDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.services.LabelService;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(tasks);
        }
    }
    @GetMapping("/label/{labelId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByLabel(@PathVariable Long labelId) {
        List<TaskDTO> tasks = taskService.getTasksByLabel(labelId);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(tasks);
        }
    }

    @PutMapping("/{id}/to-trash")
    public ResponseEntity<TaskDTO> toTrash(@PathVariable Long id) {
        TaskDTO task = taskService.toTrash(id);
        return ResponseEntity.ok().body(task);
    }

    @PutMapping("/{id}/to-list-task")
    public ResponseEntity<TaskDTO> toListTask(@PathVariable Long id) {
        TaskDTO task = taskService.toListTask(id);
        return ResponseEntity.ok().body(task);
    }
    @GetMapping("/filterByLabel")
    public ResponseEntity<List<TaskDTO>> filterTasksByLabelAndFutureDueDate(@RequestParam Long labelId) {
        List<TaskDTO> filteredTasks = taskService.getTasksByLabelFiltred(labelId);

        if (filteredTasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(filteredTasks);
        }
    }
    @GetMapping("/filterByLabelAndProject")
    public ResponseEntity<List<TaskDTO>> filterTasksByLabelAndProject(
            @RequestParam Long labelId,
            @RequestParam Long projectId
    ) {
        List<TaskDTO> filteredTasks = taskService.getTasksByLabelAndProject(labelId, projectId);

        if (filteredTasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(filteredTasks);
        }
    }
    @GetMapping("/filterByDueDate")
    public ResponseEntity<List<TaskDTO>> filterTasksByDueDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        List<TaskDTO> filteredTasks = taskService.getTasksByDueDate(dueDate);

        if (filteredTasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(filteredTasks);
        }
    }
    @GetMapping("/filterByDueDateAndProject")
    public ResponseEntity<List<TaskDTO>> filterTasksByDueDateAndProject(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
            @RequestParam Long projectId
    ) {
        List<TaskDTO> filteredTasks = taskService.getTasksByDueDateAndProjectSortedByDueDateDescending(dueDate, projectId);

        if (filteredTasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(filteredTasks);
        }
    }
    @GetMapping("/filterByStartDateAndProject")
    public ResponseEntity<List<TaskDTO>> filterTasksByStartDateAndProject(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam Long projectId
    ) {
        List<TaskDTO> filteredTasks = taskService.getTasksByStartDateAndProjectSortedByStartDateAscending(startDate, projectId);

        if (filteredTasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(filteredTasks);
        }
    }
    @GetMapping("/filterByCompletedAndProject")
    public ResponseEntity<List<TaskDTO>> filterTasksByCompletedAndProject(
            @RequestParam Boolean completed,
            @RequestParam Long projectId
    ) {
        List<TaskDTO> filteredTasks = taskService.getTasksByCompletedAndProjectSortedByDueDateAscending(completed, projectId);

        if (filteredTasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(filteredTasks);
        }
    }
    @GetMapping("/labels")
    public ResponseEntity<Map<Label, Long>> getTaskCountByLabelInProject(@PathVariable Long projectId) {
        Map<Label, Long> taskCounts = taskService.countTasksByProjectId(projectId);
        return ResponseEntity.ok().body(taskCounts);
    }
}