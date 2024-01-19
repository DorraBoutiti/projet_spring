package tn.uma.boutiti.bouzidi.ing.projet.services.Impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.mapper.TaskMapper;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.models.Task;
import tn.uma.boutiti.bouzidi.ing.projet.repository.LabelRepository;
import tn.uma.boutiti.bouzidi.ing.projet.repository.TaskRepository;
import tn.uma.boutiti.bouzidi.ing.projet.services.TaskService;
 
/**
 * Service implementation for managing tasks.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskMapper taskMapper;
    
    /**
     * Saves a new task or updates an existing one.
     *
     * @param taskDTO The data transfer object representing the task to be saved or updated.
     * @return TaskDTO representing the saved or updated task.
     */
    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }
    /**
     * Retrieves all tasks.
     *
     * @return List of TaskDTOs representing all tasks.
     */

    @Override
    public List<TaskDTO> findAll() {
        return taskMapper.toDto(taskRepository.findAll());
        //        return taskRepository.findAll().stream().map(taskMapper::toDto).collect(java.util.stream.Collectors.toSet());
    }
    /**
     * Retrieves tasks associated with a specific project in a paginated manner.
     *
     * @param projectId The ID of the project for which tasks are retrieved.
     * @param pageable  The pagination information.
     * @return Page of TaskDTOs representing tasks associated with the project.
     */
    @Override
    public Page<TaskDTO> getTasksByProject(Long projectId, Pageable pageable) {
        Page<Task> tasksPage = taskRepository.findByProjectId(projectId, pageable);

        return tasksPage.map(taskMapper::toDto);
    }
    /**
     * Retrieves tasks that are in the trash in a paginated manner.
     *
     * @param pageable The pagination information.
     * @return Page of TaskDTOs representing tasks in the trash.
     */
    @Override
    public Page<TaskDTO> getTasksInTrash(Pageable pageable) {
        Page<Task> tasksPage = taskRepository.findByArchivedTrue(pageable);

        return tasksPage.map(taskMapper::toDto);
    }
    /**
     * Retrieves a specific task by its ID.
     *
     * @param id The ID of the task to retrieve.
     * @return TaskDTO representing the requested task.
     */
    @Override
    public TaskDTO findOne(Long id) {
        Optional<Task> task =taskRepository.findById(id);
        return taskMapper.toDto(task.get());
    }
    /**
     * Deletes a task with the specified ID.
     *
     * @param id The ID of the task to delete.
     */
    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
    /**
     * Retrieves tasks associated with a specific project.
     *
     * @param projectId The ID of the project for which tasks are retrieved.
     * @return List of TaskDTOs representing tasks associated with the project.
     */
    @Override
    public List<TaskDTO> getTasksByProject(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);

        return tasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }
    /**
     * Retrieves tasks associated with a specific label.
     *
     * @param labelId The ID of the label for which tasks are retrieved.
     * @return List of TaskDTOs representing tasks associated with the label.
     */
    @Override
    public List<TaskDTO> getTasksByLabel(Long labelId) {
        List<Task> tasks = taskRepository.findByLabelsId(labelId); 

        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }
    /**
     * Moves a task to the trash by setting the "archived" flag to true.
     *
     * @param id The ID of the task to move to the trash.
     * @return TaskDTO representing the task that has been moved to the trash.
     */
    @Override
    public TaskDTO toTrash(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setArchived(true);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }
    /**
     * Restores a task from the trash by setting the "archived" flag to false.
     *
     * @param id The ID of the task to restore.
     * @return TaskDTO representing the restored task.
     */
    @Override
    public TaskDTO toListTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setArchived(false);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }
    /**
     * Retrieves tasks associated with a specific label and due date greater than the current date.
     *
     * @param labelId The ID of the label for which tasks are retrieved.
     * @return List of TaskDTOs representing tasks associated with the label and due date greater than the current date.
     */
    @Override
    public List<TaskDTO> getTasksByLabelFiltred(Long labelId) {
        LocalDate currentDate = LocalDate.now();
        List<Task> tasks = taskRepository.findByLabelsIdAndDueDateGreaterThan(labelId, currentDate); 
        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }
    /**
     * Retrieves tasks associated with a specific label, due date greater than the current date, and a specific project.
     *
     * @param labelId   The ID of the label for which tasks are retrieved.
     * @param projectId The ID of the project for which tasks are retrieved.
     * @return List of TaskDTOs representing tasks associated with the label, due date greater than the current date, and the project.
     */
    @Override
    public List<TaskDTO> getTasksByLabelAndProject(Long labelId, Long projectId) {
        LocalDate currentDate = LocalDate.now();
        List<Task> tasks = taskRepository.findByLabelsIdAndDueDateGreaterThanAndProjectId(labelId, currentDate, projectId); 

        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }
    /**
     * Retrieves tasks with a due date greater than or equal to the specified due date.
     *
     * @param dueDate The due date for filtering tasks.
     * @return List of TaskDTOs representing tasks with a due date greater than or equal to the specified due date.
     */
	@Override
	public List<TaskDTO> getTasksByDueDate(LocalDate dueDate) {
        List<Task> tasks = taskRepository.findByDueDateGreaterThanEqual(dueDate); 

        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }
	 /**
     * Retrieves tasks with a due date greater than or equal to the specified due date and associated with a specific project,
     * sorted by due date in descending order.
     *
     * @param dueDate   The due date for filtering tasks.
     * @param projectId The ID of the project for which tasks are retrieved.
     * @return List of TaskDTOs representing tasks with a due date greater than or equal to the specified due date and the project.
     */
	@Override
    public List<TaskDTO> getTasksByDueDateAndProjectSortedByDueDateDescending(LocalDate dueDate, Long projectId) {
        List<Task> tasks = taskRepository.findByDueDateGreaterThanEqualAndProjectIdOrderByDueDateDesc(dueDate, projectId); // Supposons une méthode findByDueDateGreaterThanEqualAndProjectIdOrderByDueDateDesc dans le repository

        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }
	/**
     * Retrieves tasks with a start date greater than or equal to the specified start date and associated with a specific project,
     * sorted by start date in ascending order.
     *
     * @param startDate The start date for filtering tasks.
     * @param projectId The ID of the project for which tasks are retrieved.
     * @return List of TaskDTOs representing tasks with a start date greater than or equal to the specified start date and the project.
     */
	@Override
    public List<TaskDTO> getTasksByStartDateAndProjectSortedByStartDateAscending(LocalDate startDate, Long projectId) {
        List<Task> tasks = taskRepository.findByStartDateGreaterThanEqualAndProjectIdOrderByStartDateAsc(startDate, projectId); // Supposons une méthode findByStartDateGreaterThanEqualAndProjectIdOrderByStartDateAsc dans le repository

        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }
	 /**
     * Retrieves tasks with a specified completion status and associated with a specific project,
     * sorted by due date in ascending order.
     *
     * @param completed The completion status for filtering tasks.
     * @param projectId The ID of the project for which tasks are retrieved.
     * @return List of TaskDTOs representing tasks with the specified completion status and the project.
     */
	@Override
    public List<TaskDTO> getTasksByCompletedAndProjectSortedByDueDateAscending(Boolean completed, Long projectId) {
        List<Task> tasks = taskRepository.findByCompletedAndProjectIdOrderByDueDateAsc(completed, projectId); // Supposons une méthode findByCompletedAndProjectIdOrderByDueDateAsc dans le repository

        return tasks.stream()
                .map(taskMapper::toDto) 
                .collect(Collectors.toList());
    }
	 /**
     * Searches tasks by name, labels, description, title, and project name using a keyword.
     *
     * @param keyword The keyword to search for in task names, labels, descriptions, titles, and project names.
     * @return List of TaskDTOs representing tasks matching the search criteria.
     */
	@Override
	public List<TaskDTO> searchTasksByName(String keyword) {
		List<Task> tasks =taskRepository.findByLabels_NameContainingOrDescriptionContainingOrTitleContainingOrProject_NameContaining(
	            keyword, keyword, keyword, keyword);
		 return taskMapper.toDto(tasks);
	}
	 /**
     * Filters tasks based on label IDs, project ID, keyword, completion status, start dates, and due dates.
     *
     * @param labelIds     List of label IDs for filtering tasks.
     * @param projectId    The ID of the project for filtering tasks.
     * @param keyword      The keyword for filtering tasks by title or description.
     * @param completed    The completion status for filtering tasks.
     * @param minStartDate The minimum start date for filtering tasks.
     * @param maxStartDate The maximum start date for filtering tasks.
     * @param minDueDate   The minimum due date for filtering tasks.
     * @param maxDueDate   The maximum due date for filtering tasks.
     * @return List of TaskDTOs representing tasks matching the filter criteria.
     */
	@Override
	public List<TaskDTO> filter(List<Long> labelIds, Long projectId, String keyword, Boolean completed,
			 LocalDate minStartDate, LocalDate maxStartDate, LocalDate minDueDate, LocalDate maxDueDate) {
	    List<Task> tasks = taskRepository.findByLabels_IdInAndProject_IdAndTitleContainingOrDescriptionContaining(
	            labelIds,
	            projectId,
	            keyword,
	            keyword,
	            completed,
	            minStartDate,
	            maxStartDate,
	            minDueDate,
	            maxDueDate
	    );
	    return taskMapper.toDto(tasks);
	}
	/**
     * Counts the number of tasks for each label in a specific project.
     *
     * @param projectId The ID of the project for which label counts are calculated.
     * @return Map where keys are label names and values are the corresponding task counts.
     */
    @Override
	public Map<String, Long> countLabelsForProject(Long projectId) {
	    List<Object[]> labelCounts = taskRepository.countTasksByProjectId(projectId);
	    Map<String, Long> countsMap = new HashMap<>();
	    for (Object[] obj : labelCounts) {
	        String labelName = (String) obj[0];
	        Long count = (Long) obj[1];
	        countsMap.put(labelName, count);
	    }

	    return countsMap;
	}
    /**
     * Retrieves tasks associated with a specific member.
     *
     * @param memberId The ID of the member for which tasks are retrieved.
     * @return List of TaskDTOs representing tasks associated with the member.
     */
    @Override
	public List<TaskDTO> getTasksByMembersId(Long memberId) {
		 return taskMapper.toDto(taskRepository.findByMembers_Id(memberId));
	}
    /**
     * Updates the labels of a task.
     *
     * @param id     The ID of the task to update.
     * @param labels The new list of labels for the task.
     * @return TaskDTO representing the updated task.
     */
    @Override
    public TaskDTO updateTaskLabels(Long id , List<Label> labels) {
    	   Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    	   task.setLabels(labels);
    		  task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }
    /**
     * Retrieves tasks associated with a specific user.
     *
     * @param username The username of the user for which tasks are retrieved.
     * @return List of TaskDTOs representing tasks associated with the user.
     */
	@Override
	public List<TaskDTO> getUserTasks(String username) {
		
		 return taskMapper.toDto(taskRepository.findByMembers_Username(username));
	}
	 /**
     * Updates the status of a task.
     *
     * @param taskId The ID of the task to update.
     * @param status The new status for the task.
     * @return TaskDTO representing the updated task.
     */

	@Override
	public TaskDTO updateTaskStatus(Long taskId, String status) {
		   Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
    	   task.setStatus(status);
  
    	   if ("inProgress".equals(status)) {
    	      
    	        task.setStartDate(LocalDate.now());
    	    }

    	    if ("completed".equals(status)) {
    	      
    	        task.setDueDate(LocalDate.now());
    	    }
    		  task = taskRepository.save(task);
        return taskMapper.toDto(task);
	}

	 /**
     * Retrieves the count of tasks in progress and overdue.
     *
     * @return The count of tasks in progress and overdue.
     */
 
    @Override
    public Long getCountOfTasksInProgressAndOverdue() {
        LocalDate currentDate = LocalDate.now();
        return taskRepository.countByStatusAndDueDateBefore("in progress", currentDate);
    }
    /**
     * Retrieves tasks that are in the trash.
     *
     * @return List of TaskDTOs representing tasks in the trash.
     */
    @Override
   public List<TaskDTO> getTasksInTrash() {
        List<Task> tasksInTrash = taskRepository.findByArchivedTrue();

        // Convert Task entities to DTOs if needed
        return tasksInTrash.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

	@Override
	public List<TaskDTO> getTasksByStatusAndMembers_Id(String status, Long memberId) {
		// TODO Auto-generated method stub
		return null;
	}
 

}
