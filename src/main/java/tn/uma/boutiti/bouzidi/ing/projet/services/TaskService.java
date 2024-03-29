package tn.uma.boutiti.bouzidi.ing.projet.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface TaskService {

    TaskDTO save (TaskDTO taskDTO);
    List<TaskDTO> findAll();
    TaskDTO findOne(Long id);
    void delete(Long id);
	List<TaskDTO> getTasksByProject(Long projectId);
	List<TaskDTO> getTasksByLabel(Long labelId);
    TaskDTO toTrash(Long id);
    TaskDTO toListTask(Long id);
	List<TaskDTO> getTasksByLabelFiltred(Long labelId);
	List<TaskDTO> getTasksByLabelAndProject(Long labelId, Long projectId);
	List<TaskDTO> getTasksByDueDate(LocalDate dueDate);
	List<TaskDTO> getTasksByDueDateAndProjectSortedByDueDateDescending(LocalDate dueDate, Long projectId);
	List<TaskDTO> getTasksByStartDateAndProjectSortedByStartDateAscending(LocalDate startDate, Long projectId);
	List<TaskDTO> getTasksByCompletedAndProjectSortedByDueDateAscending(Boolean completed, Long projectId);	
	List<TaskDTO> searchTasksByName(String keyword);
	List<TaskDTO> filter(List<Long> labelIds, Long projectId, String keyword, Boolean completed,
		        LocalDate minStartDate, LocalDate maxStartDate, LocalDate minDueDate, LocalDate maxDueDate);
	Map<String, Long> countLabelsForProject(Long projectId);

	List<TaskDTO> getTasksByMembersId(Long memberId);
	TaskDTO updateTaskLabels (Long id , List<Label> labels) ;
	List<TaskDTO> getUserTasks(String username);
	TaskDTO updateTaskStatus(Long taskId, String status);

	List<TaskDTO> getTasksByStatusAndMembers_Id(String status,Long memberId);
	Long getCountOfTasksInProgressAndOverdue();	
	List<TaskDTO> getTasksInTrash();
	Page<TaskDTO> getTasksByProject(Long projectId, Pageable pageable);
	Page<TaskDTO> getTasksInTrash(Pageable pageable);

}
