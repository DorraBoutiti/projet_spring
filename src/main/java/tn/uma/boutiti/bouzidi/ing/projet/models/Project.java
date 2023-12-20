package tn.uma.boutiti.bouzidi.ing.projet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @OneToMany(mappedBy = "project",fetch = FetchType.EAGER)
    private List<Task> tasks ;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "project_member",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<Member> members;
    
}
