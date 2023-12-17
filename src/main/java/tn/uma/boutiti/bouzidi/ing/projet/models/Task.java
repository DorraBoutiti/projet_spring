package tn.uma.boutiti.bouzidi.ing.projet.models;


import java.time.LocalDate;
import java.time.LocalDate; 
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean completed;
    
    
    @ManyToMany
    @JoinTable(
    		name = "task_labels",
    		joinColumns = @JoinColumn(name = "task_id"),
    		inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    
    public void SetCProject(Project p) {
    	this.project = p;
    }
    
}
