package tn.uma.boutiti.bouzidi.ing.projet.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.uma.boutiti.bouzidi.ing.projet.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTitleContaining(String keyword);
    
    //@Query("SELECT t FROM Task t LEFT JOIN FETCH t.labels")
  //  List<Task> findAllTasks();
    
}
