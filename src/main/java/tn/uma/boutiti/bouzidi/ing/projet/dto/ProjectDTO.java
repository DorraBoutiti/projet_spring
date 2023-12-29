package tn.uma.boutiti.bouzidi.ing.projet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private List<TaskDTO> tasks;

    //private List<MemberDTO> members;

}
