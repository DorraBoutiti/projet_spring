package tn.uma.boutiti.bouzidi.ing.projet.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String username;
    // Avoid exposing password in DTO for security reasons
    private Set<Long> projectIds;
}
