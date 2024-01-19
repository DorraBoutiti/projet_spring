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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
//@RequestMapping("/api/tasks")
@RequestMapping("/api")
 
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
    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> createTask(final @RequestBody TaskDTO taskDTO) {
        final TaskDTO task = taskService.save(taskDTO);
        return ResponseEntity.ok().body(task);
    }


    /**
     * Endpoint for retrieving all tasks.
     *
     * @return ResponseEntity containing a list of all tasks.
     */


    @GetMapping("/tasks")
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



    @GetMapping("/tasks/{projectId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByProject(
            @PathVariable Long projectId) {
        List<TaskDTO> tasks = taskService.getTasksByProject(projectId);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(tasks);
        }
    }

    @GetMapping("/tasks/{projectId}/tasks-paginated")
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


    @GetMapping("/tasks/{id}")
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
    @DeleteMapping("/tasks/{id}")
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
    @PutMapping("/tasks")
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
    @PutMapping("/tasks/{taskId}/assignLabel/{labelId}")
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
    @PutMapping("/tasks/{taskId}/markAsCompleted")
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
    /*@GetMapping("/tasks/{projectId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByProject(final @PathVariable Long projectId) {
        final List<TaskDTO> tasks = taskService.getTasksByProject(projectId);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(tasks);
        }
    }*/
    
    /**
     * Retrieves tasks associated with the specified label ID.
     *
     * @param labelId The ID of the label for which tasks are retrieved.
     * @return ResponseEntity containing the list of tasks for the specified label.
     */


    @GetMapping("/tasks/label/{labelId}/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByLabel(final @PathVariable Long labelId) {
        final List<TaskDTO> tasks = taskService.getTasksByLabel(labelId);

        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(tasks);
        }
    }
    
    /**
     * Endpoint for moving a task to the trash.
     *
     * @param id The ID of the task to be moved to the trash.
     * @return ResponseEntity containing the updated task in the trash.
     */
    
    @PutMapping("/tasks/{id}/to-trash")
    public ResponseEntity<TaskDTO> toTrash(@PathVariable Long id) {
        TaskDTO task = taskService.toTrash(id);
        return ResponseEntity.ok().body(task);
    }
    /**
     * Endpoint for retrieving tasks that are currently in the trash.
     *
     * @return ResponseEntity containing a list of tasks in the trash.
     */
    @GetMapping("/tasks/in-trash")
    public ResponseEntity<List<TaskDTO>> getTasksInTrash() {
        List<TaskDTO> tasksInTrash = taskService.getTasksInTrash();

        if (tasksInTrash.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(tasksInTrash);
        }
    }

    /**
     * Endpoint for moving a task from the trash back to the task list.
     *
     * @param id The ID of the task to be moved back to the task list.
     * @return ResponseEntity containing the updated task in the task list.
     */
    @PutMapping("/tasks/{id}/to-list-task")
    public ResponseEntity<TaskDTO> toListTask(@PathVariable Long id) {
        TaskDTO task = taskService.toListTask(id);
        return ResponseEntity.ok().body(task);
    }
    /**
     * Endpoint for retrieving tasks filtered by a label and having a future due date.
     *
     * @param labelId The ID of the label for filtering tasks.
     * @return ResponseEntity containing the list of tasks filtered by label and future due date.
     */
    @GetMapping("/tasks/filterByLabel")
    public ResponseEntity<List<TaskDTO>> filterTasksByLabelAndFutureDueDate(@RequestParam Long labelId) {
        List<TaskDTO> filteredTasks = taskService.getTasksByLabelFiltred(labelId);

        if (filteredTasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(filteredTasks);
        }
    }
    /**
     * Endpoint for retrieving tasks filtered by both label and project.
     *
     * @param labelId   The ID of the label for filtering tasks.
     * @param projectId The ID of the project for filtering tasks.
     * @return ResponseEntity containing the list of tasks filtered by label and project.
     */
    @GetMapping("/tasks/filterByLabelAndProject")
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
    /**
     * Endpoint for retrieving tasks filtered by a specific due date.
     *
     * @param dueDate The due date for filtering tasks.
     * @return ResponseEntity containing the list of tasks filtered by the specified due date.
     */
    @GetMapping("/tasks/filterByDueDate")
    public ResponseEntity<List<TaskDTO>> filterTasksByDueDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        List<TaskDTO> filteredTasks = taskService.getTasksByDueDate(dueDate);

        if (filteredTasks.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } else {
            return ResponseEntity.ok().body(filteredTasks);
        }
    }
    
    /**
     * Endpoint for retrieving tasks filtered by due date and project, sorted by due date in descending order.
     *
     * @param dueDate   The due date for filtering tasks.
     * @param projectId The ID of the project for additional filtering.
     * @return ResponseEntity containing the list of tasks filtered by due date and project, sorted in descending order by due date.
     */
    @GetMapping("/tasks/filterByDueDateAndProject")
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
    /**
     * Endpoint for retrieving tasks filtered by start date and project, sorted by start date in ascending order.
     *
     * @param startDate The start date for filtering tasks.
     * @param projectId The ID of the project for additional filtering.
     * @return ResponseEntity containing the list of tasks filtered by start date and project, sorted in ascending order by start date.
     */
    
    @GetMapping("/tasks/filterByStartDateAndProject")
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
    /**
     * Endpoint for retrieving tasks filtered by completion status and project, sorted by due date in ascending order.
     *
     * @param completed  The completion status for filtering tasks.
     * @param projectId The ID of the project for additional filtering.
     * @return ResponseEntity containing the list of tasks filtered by completion status and project, sorted in ascending order by due date.
     */
    @GetMapping("/tasks/filterByCompletedAndProject")
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
    /**
     * Endpoint for searching tasks by name using a keyword.
     *
     * @param keyword The keyword to search for in task names.
     * @return ResponseEntity containing the list of tasks matching the search keyword.
     */
    @GetMapping("/tasks/search")
    public ResponseEntity<List<TaskDTO>> searchTaskByName(
            @RequestParam(required = false) String keyword) {
	  List<TaskDTO> tasks = taskService.searchTasksByName(keyword);
      return ResponseEntity.ok().body(tasks);
}
    /**
     * Endpoint for filtering tasks based on various parameters.
     *
     * @param labelIds       List of label IDs for filtering tasks.
     * @param projectId      The ID of the project for additional filtering.
     * @param keyword        Keyword for searching tasks.
     * @param completed      Completion status for filtering tasks.
     * @param minStartDate   Minimum start date for filtering tasks.
     * @param maxStartDate   Maximum start date for filtering tasks.
     * @param minDueDate     Minimum due date for filtering tasks.
     * @param maxDueDate     Maximum due date for filtering tasks.
     * @return ResponseEntity containing the list of tasks filtered by the specified parameters.
     */
    @GetMapping("/tasks/filter")
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
    /**
     * Endpoint for counting labels and tasks for a specific project.
     *
     * @param projectId The ID of the project for which labels and tasks are counted.
     * @return ResponseEntity containing a map of label names and their respective counts, along with the count of tasks in progress and overdue.
     */
    @GetMapping("/tasks/countLabelsAndTasks")
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

    /**
     * Endpoint for counting labels and their occurrences for a specific project.
     *
     * @param projectId The ID of the project for which label counts are requested.
     * @return ResponseEntity containing a map of label names and their respective counts.
     */
    @GetMapping("/tasks/countLabelsForProject")
    public ResponseEntity<Map<String, Long>> countLabelsForProject(@RequestParam Long projectId) {
        Map<String, Long> labelCounts = taskService.countLabelsForProject(projectId);

        if (labelCounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(labelCounts);
        }
    }
    /**
     * Endpoint for retrieving tasks assigned to a specific member.
     *
     * @param memberId The ID of the member for whom tasks are requested.
     * @return ResponseEntity containing the list of tasks assigned to the specified member.
     */
    @GetMapping("/tasks/getByMemberId")
    public ResponseEntity<List<TaskDTO>> getTasksByMember(

            @RequestParam Long memberId) {
        try {
            List<TaskDTO> tasks = taskService.getTasksByMembersId(memberId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint for counting tasks that are in progress or overdue.
     *
     * @return ResponseEntity containing the count of tasks that are in progress or overdue.
     */
    @GetMapping("/tasks/countInProgressAndOverdue")
    public ResponseEntity<Long> getCountOfTasksInProgressAndOverdue() {
        Long count = taskService.getCountOfTasksInProgressAndOverdue();
        return ResponseEntity.ok(count);
    }
    /**
     * Endpoint for retrieving tasks assigned to a specific member based on their status.
     *
     * @param status    The status of tasks to retrieve.
     * @param memberId  The ID of the member for whom tasks are requested.
     * @return ResponseEntity containing the list of tasks assigned to the specified member based on the given status.
     */
    @GetMapping("/tasks/getByStatusAndMemberId")
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
    /**
     * Endpoint for updating task labels based on the provided request.
     *
     * @param request The request containing task ID and labels to update.
     * @return ResponseEntity containing the updated task.
     */
    @PutMapping("/user/tasks/updateTaskLabels")
    public ResponseEntity<TaskDTO> updateTask(@RequestBody UpdateTaskRequest request) { 
        TaskDTO task = taskService.updateTaskLabels(request.getTaskId(), request.getLabels());
        return ResponseEntity.ok().body(task);
    }
    /**
     * Endpoint for retrieving tasks assigned to the authenticated user.
     *
     * @param userDetails The authenticated user details.
     * @return ResponseEntity containing the list of tasks assigned to the authenticated user.
     */
    @GetMapping("/user/tasks/getUserTasks")
    public ResponseEntity<List<TaskDTO>> getUserTasks(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<TaskDTO> tasks = taskService.getUserTasks(username);
        return ResponseEntity.ok(tasks);
    }
    /**
     * Endpoint for updating the status of a task based on the provided request.
     *
     * @param request The request containing task ID and status to update.
     * @return ResponseEntity containing the updated task.
     */
    
    @PutMapping("/user/tasks/updateTaskStatus")
    public ResponseEntity<TaskDTO> updateTaskStatus(@RequestBody UpdateTaskRequest request) { 
        TaskDTO task = taskService.updateTaskStatus(request.getTaskId(), request.getStatus());
        return ResponseEntity.ok().body(task);
    }
    /**
     * Endpoint for retrieving tasks that are in the trash, paginated.
     *
     * @param page The page number for pagination (default is 0).
     * @param size The number of tasks per page (default is 10).
     * @return ResponseEntity containing a paginated list of tasks in the trash.
     */
 
    @GetMapping("/tasks/trash")
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
 
