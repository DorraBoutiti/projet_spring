package tn.uma.boutiti.bouzidi.ing.projet.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.uma.boutiti.bouzidi.ing.projet.dto.LabelDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;
import tn.uma.boutiti.bouzidi.ing.projet.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTitleContaining(String keyword);
    List<Task> findByProjectId(Long projectId);
    List<Task> findByLabelsId(Long labelId);
	List<Task> findByLabelsIdAndDueDateGreaterThan(Long labelId, LocalDate currentDate);
	List<Task> findByLabelsIdAndDueDateGreaterThanAndProjectId(Long labelId, LocalDate currentDate, Long projectId);
	List<Task> findByDueDateGreaterThanEqual(LocalDate dueDate);
	List<Task> findByDueDateGreaterThanEqualAndProjectIdOrderByDueDateDesc(LocalDate dueDate, Long projectId);
	List<Task> findByStartDateGreaterThanEqualAndProjectIdOrderByStartDateAsc(LocalDate startDate, Long projectId);
	List<Task> findByCompletedAndProjectIdOrderByDueDateAsc(Boolean completed, Long projectId);
	List<Task> findByLabels_NameContainingOrDescriptionContainingOrTitleContainingOrProject_NameContaining(
	    	    String labelKeyword, String descriptionKeyword, String titleKeyword, String projectKeyword);

	 @Modifying
	    @Query(value =
	            "SELECT * FROM Task t " +
	                    "WHERE (:labelIds IS NULL OR EXISTS (SELECT 1 FROM task_labels l WHERE l.label_id IN :labelIds AND l.task_id = t.id)) " +
	                    "AND (:projectId IS NULL OR t.project_id = :projectId) " +
	                    "AND (LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :description, '%'))) " +
	                    "AND (:completed IS NULL OR t.completed = :completed) " +
	                    "AND (:minStartDate IS NULL OR t.start_Date >= :minStartDate) " +
	                    "AND (:maxStartDate IS NULL OR t.start_Date <= :maxStartDate) " +
	                    "AND (:minDueDate IS NULL OR t.due_Date >= :minDueDate) " +
	                    "AND (:maxDueDate IS NULL OR t.due_Date <= :maxDueDate)",
	            nativeQuery = true)
	    List<Task> findByLabels_IdInAndProject_IdAndTitleContainingOrDescriptionContaining(
	            @Param("labelIds") List<Long> labelIds,
	            @Param("projectId") Long projectId,
	            @Param("title") String title,
	            @Param("description") String description,
	            @Param("completed") Boolean completed,
	            @Param("minStartDate") LocalDate minStartDate,
	            @Param("maxStartDate") LocalDate maxStartDate,
	            @Param("minDueDate") LocalDate minDueDate,
	            @Param("maxDueDate") LocalDate maxDueDate
	    );
	@Query("SELECT l.name, COUNT(t) " +
	            "FROM Task t " +
	            "JOIN t.labels l " +
	            "WHERE t.project.id = :projectId " +
	            "GROUP BY l.name")
	 List<Object[]> countTasksByProjectId(@Param("projectId") Long projectId);
	 List<Task> findByStatusAndMembers_Id(String status,Long memberId);
		
}
