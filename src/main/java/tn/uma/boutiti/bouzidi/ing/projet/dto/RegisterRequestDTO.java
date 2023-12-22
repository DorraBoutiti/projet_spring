package tn.uma.boutiti.bouzidi.ing.projet.dto;

 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.uma.boutiti.bouzidi.ing.projet.models.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

	  private String userName;
	  private String password;
	  private Role role;

}
