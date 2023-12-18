package tn.uma.boutiti.bouzidi.ing.projet.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private Set<Long> taskIds;
    private Set<Long> memberIds;
}
