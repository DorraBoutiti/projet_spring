package tn.uma.boutiti.bouzidi.ing.projet.models;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
