package tn.uma.boutiti.bouzidi.ing.projet.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String username;
    private String password;
    private String role;
    private List<ProjectDTO> projects;
}
