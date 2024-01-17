package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.uma.boutiti.bouzidi.ing.projet.dto.LabelDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;

import tn.uma.boutiti.bouzidi.ing.projet.dto.UpdateTaskRequest;
import tn.uma.boutiti.bouzidi.ing.projet.services.LabelService;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;

/**
 * Controller class handling task-related endpoints.
 */

import tn.uma.boutiti.bouzidi.ing.projet.services.LabelService;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {

	 /**  the TaskService */
    private final TaskService taskService;
    /**
     * Autowired service for managing labels.
     */
    @Autowired
    private LabelService labelService;
    
    /**
     * Constructor for TaskController.
     *
     * @param taskService The service for managing tasks.
     */
    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }
    /**
     * Endpoint for creating a new task.
     *
     * @param taskDTO The data transfer object representing the task to be created.
     * @return ResponseEntity containing the created task.
     */
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(final @RequestBody TaskDTO taskDTO) {
        final TaskDTO task = taskService.save(taskDTO);
        return ResponseEntity.ok().body(task);
    }


    /**
     * Endpoint for retrieving all tasks.
     *
     * @return ResponseEntity containing a list of all tasks.
     */


    @GetMapping
    public ResponseEntity<List<TaskDTO>> findAllTasks() {
        final List<TaskDTO> tasks = taskService.findAll();
        return ResponseEntity.ok().body(tasks);
    }

    /**
     * Endpoint for retrieving a specific task by ID.
     *
     * @param idTask The ID of the task to retrieve.
     * @return ResponseEntity containing the requested task.
     */



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
    public ResponseEntity<TaskDTO> findOneTask(final @PathVariable Long idTask) {
        final TaskDTO task = taskService.findOne(idTask);
        return ResponseEntity.ok().body(task);
    }
    /**
     * Endpoint for deleting a specific task by ID.
     *
     * @param idTask The ID of the task to delete.
     * @return ResponseEntity indicating the success of the deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(final @PathVariable Long idTask) {
        taskService.delete(idTask);
        return ResponseEntity.ok().build();
    }
    /**
     * Endpoint for updating a task. 
     * If the task ID is null,
     *  a new task will be created.
     *
     * @param taskDTO The data transfer object representing the task to be updated.
     * @return ResponseEntity containing the updated or created task.
     */
    @PutMapping
    public ResponseEntity<TaskDTO> updateTask(final @RequestBody TaskDTO taskDTO) {
    	 ResponseEntity<TaskDTO> response; 
        if (taskDTO.getId() == null) {
        	response= createTask(taskDTO);
        }else {
           final TaskDTO task = taskService.save(taskDTO);
            response= ResponseEntity.ok().body(task);
        }
        return response;
    }

    /**
     * Endpoint for assigning a label to a specific task.
     *
     * @param taskId  The ID of the task.
     * @param labelId The ID of the label to assign.
     * @return ResponseEntity containing the updated task.
     */
    @PutMapping("/{taskId}/assignLabel/{labelId}")
    public ResponseEntity<TaskDTO> assignLabelToTask(final @PathVariable Long taskId,final @PathVariable Long labelId) {
        final TaskDTO task = taskService.findOne(taskId);
        final LabelDTO label = labelService.findOne(labelId);

        if (task != null && label != null) {
            final List<LabelDTO> taskLabels = task.getLabels();
            taskLabels.add(label);
            task.setLabels(taskLabels);
            taskService.save(task);
            return ResponseEntity.ok().body(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Endpoint for marking a task as completed.
     *
     * @param taskId The ID of the task to mark as completed.
     * @return ResponseEntity containing the updated task.
     */
    @PutMapping("/{taskId}/markAsCompleted")
    public ResponseEntity<TaskDTO> markTaskAsCompleted(final @PathVariable Long taskId) {
        final TaskDTO task = taskService.findOne(taskId);

        if (task != null) {
            task.setCompleted(true);
            taskService.save(task);
            return ResponseEntity.ok().body(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
    * Endpoint for retrieving tasks by project ID.
    *
    * @param projectId The ID of the project.
    * @return ResponseEntity containing the list of tasks for the specified project.
    */
    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByProject(final @PathVariable Long projectId) {
        final List<TaskDTO> tasks = taskService.getTasksByProject(projectId);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(tasks);
        }
    }
    
    /**
     * Retrieves tasks associated with the specified label ID.
     *
     * @param labelId The ID of the label for which tasks are retrieved.
     * @return ResponseEntity containing the list of tasks for the specified label.
     */


    @GetMapping("/label/{labelId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByLabel(final @PathVariable Long labelId) {
        final List<TaskDTO> tasks = taskService.getTasksByLabel(labelId);

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

    @GetMapping("/getByMemberId")
    public ResponseEntity<List<TaskDTO>> getTasksByMember(


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
            List<TaskDTO> tasks = taskService.getTasksByMembersId(memberId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
 
    @PutMapping("/updateTaskLabels")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody UpdateTaskRequest request) { 
        TaskDTO task = taskService.updateTaskLabels(request.getTaskId(), request.getLabels());
        return ResponseEntity.ok().body(task);
    }
    @GetMapping("/getUserTasks")
    public ResponseEntity<List<TaskDTO>> getUserTasks(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<TaskDTO> tasks = taskService.getUserTasks(username);
        return ResponseEntity.ok(tasks);
    }
    
    @PutMapping("/updateTaskStatus")
    public ResponseEntity<TaskDTO> updateTaskStatus(@RequestBody UpdateTaskRequest request) { 
        TaskDTO task = taskService.updateTaskStatus(request.getTaskId(), request.getStatus());
        return ResponseEntity.ok().body(task);
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
 
