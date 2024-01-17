package tn.uma.boutiti.bouzidi.ing.projet.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
