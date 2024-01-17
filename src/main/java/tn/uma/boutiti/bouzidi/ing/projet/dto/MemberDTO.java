package tn.uma.boutiti.bouzidi.ing.projet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
