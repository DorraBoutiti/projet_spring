package tn.uma.boutiti.bouzidi.ing.projet.services;

import tn.uma.boutiti.bouzidi.ing.projet.dto.AuthReqDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.AuthResDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.RegisterRequestDTO;

public interface AuthenticationService {
	 public AuthResDTO register(RegisterRequestDTO request) throws Exception;
	 public AuthResDTO authenticate(AuthReqDTO request) ;

}
