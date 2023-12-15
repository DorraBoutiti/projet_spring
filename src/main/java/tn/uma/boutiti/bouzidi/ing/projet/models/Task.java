package tn.uma.boutiti.bouzidi.ing.projet.models;


import java.time.LocalDate;
import java.time.LocalDate; 
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
    
    public Task() {
		super();
		this.id = 0L;
		this.title = "";
		this.description = "";
		this.dueDate = LocalDate.now();
		this.completed = false;
	}
    
	public Task(String title, String description, LocalDate dueDate, boolean completed) {
		super();		
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.completed = completed;		
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public Set<Label> getLabels() {
		return labels;
	}
	public void setLabels(Set<Label> labels) {
		this.labels = labels;
	}
	
    @ManyToMany
    @JoinTable(
    		name = "task_labels",
    		joinColumns = @JoinColumn(name = "task_id"),
    		inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private Set<Label> labels = new HashSet<>();
    

    
}
