package tn.uma.boutiti.bouzidi.ing.projet.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthReqDTO {
	  private String userName;
	  String password;
}
