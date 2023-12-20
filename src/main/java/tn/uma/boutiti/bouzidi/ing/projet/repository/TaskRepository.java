package tn.uma.boutiti.bouzidi.ing.projet.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
	Map<Label, Long> countTasksByProjectId(Long projectId);
}
