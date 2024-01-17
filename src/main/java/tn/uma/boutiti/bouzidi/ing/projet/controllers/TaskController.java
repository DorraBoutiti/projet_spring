package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.uma.boutiti.bouzidi.ing.projet.dto.LabelDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.services.LabelService;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:3000")
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


    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByProject(
            @PathVariable Long projectId) {
        List<TaskDTO> tasks = taskService.getTasksByProject(projectId);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(tasks);
        }
    }

    @GetMapping("/{projectId}/tasks-paginated")
    public ResponseEntity<Page<TaskDTO>> getTasksByProjectPaginated(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TaskDTO> tasksPage = taskService.getTasksByProject(projectId, pageable);

        if (tasksPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(tasksPage);
        }
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
    @GetMapping("/in-trash")
    public ResponseEntity<List<TaskDTO>> getTasksInTrash() {
        List<TaskDTO> tasksInTrash = taskService.getTasksInTrash();

        if (tasksInTrash.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(tasksInTrash);
        }
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
    
    @GetMapping("/search")
    public ResponseEntity<List<TaskDTO>> searchTaskByName(
            @RequestParam(required = false) String keyword) {
	  List<TaskDTO> tasks = taskService.searchTasksByName(keyword);
      return ResponseEntity.ok().body(tasks);
}
    
    @GetMapping("/filter")
    public ResponseEntity<List<TaskDTO>> filter(
            @RequestParam(required = false) List<Long> labelIds,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minStartDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maxStartDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minDueDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maxDueDate) {

        List<TaskDTO> tasks = taskService.filter(
                labelIds,
                projectId,
                keyword,
                completed,
                minStartDate,
                maxStartDate,
                minDueDate,
                maxDueDate
        );
        return ResponseEntity.ok().body(tasks);
    }
    @GetMapping("/countLabelsAndTasks")
    public ResponseEntity<Map<String, Long>> countLabelsAndTasks(@RequestParam Long projectId) {
        Map<String, Long> labelCounts = taskService.countLabelsForProject(projectId);

        Long tasksInProgressAndOverdue = taskService.getCountOfTasksInProgressAndOverdue();
        labelCounts.put("tasksInProgressAndOverdue", tasksInProgressAndOverdue);

        if (labelCounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(labelCounts);
        }
    }


    @GetMapping("/countLabelsForProject")
    public ResponseEntity<Map<String, Long>> countLabelsForProject(@RequestParam Long projectId) {
        Map<String, Long> labelCounts = taskService.countLabelsForProject(projectId);

        if (labelCounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(labelCounts);
        }
    }
    @GetMapping("/countInProgressAndOverdue")
    public ResponseEntity<Long> getCountOfTasksInProgressAndOverdue() {
        Long count = taskService.getCountOfTasksInProgressAndOverdue();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/getByStatusAndMemberId")
    public ResponseEntity<List<TaskDTO>> getTasksByStatusAndMember(
            @RequestParam String status,
            @RequestParam Long memberId) {
        try {
            List<TaskDTO> tasks = taskService.getTasksByStatusAndMembers_Id(status, memberId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/trash")
    public ResponseEntity<Page<TaskDTO>> getTasksInTrash(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TaskDTO> tasksPage = taskService.getTasksInTrash(pageable);

        if (tasksPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(tasksPage);
        }
    }
}