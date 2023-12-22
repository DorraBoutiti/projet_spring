package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tn.uma.boutiti.bouzidi.ing.projet.dto.AuthReqDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.AuthResDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.RegisterRequestDTO;
import tn.uma.boutiti.bouzidi.ing.projet.exceptions.UsernameAlreadyExistsException;
import tn.uma.boutiti.bouzidi.ing.projet.services.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
@Autowired
  private  AuthenticationService service;

@PostMapping("/register")
public ResponseEntity<AuthResDTO> register(
		  
    @RequestBody RegisterRequestDTO request
) throws Exception {
	  System.out.println("hello fromm newyork");
  return ResponseEntity.ok().body(service.register(request));
}
  @PostMapping("/authenticate")
  public ResponseEntity<AuthResDTO> authenticate(
      @RequestBody AuthReqDTO request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }
}
