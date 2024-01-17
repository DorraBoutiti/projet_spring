package tn.uma.boutiti.bouzidi.ing.projet.models;


import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
    private LocalDate startDate;
    private LocalDate dueDate;
    private boolean completed;
    private boolean archived;
    private String priority;
    private String status;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    		name = "task_users",
    		joinColumns = @JoinColumn(name = "task_id"),
    		inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<Member> members;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    		name = "task_labels",
    		joinColumns = @JoinColumn(name = "task_id"),
    		inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private List<Label> labels;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_id")
    private Project project;
}
