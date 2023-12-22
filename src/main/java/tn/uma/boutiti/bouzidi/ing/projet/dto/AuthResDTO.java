package tn.uma.boutiti.bouzidi.ing.projet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResDTO {

	 @JsonProperty("msg")
	  private String msg;
	  @JsonProperty("access_token")
	  private String accessToken;

}
