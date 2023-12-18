package tn.uma.boutiti.bouzidi.ing.projet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String password;
    @JsonIgnore // Use @JsonIgnore to break the serialization loop
    @ManyToMany(mappedBy = "members")
    private List<Project> projects;
}
