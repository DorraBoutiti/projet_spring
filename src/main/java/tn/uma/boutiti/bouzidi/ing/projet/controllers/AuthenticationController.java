package tn.uma.boutiti.bouzidi.ing.projet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tn.uma.boutiti.bouzidi.ing.projet.dto.AuthReqDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.AuthResDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.RegisterRequestDTO;
import tn.uma.boutiti.bouzidi.ing.projet.services.AuthenticationService;

/**
 * Controller class handling authentication-related endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {

    
	 /** Injecting the AuthenticationService */
    @Autowired
    private AuthenticationService service;

    
    /**  End point for user registration. */
    @PostMapping("/register")
    public ResponseEntity<AuthResDTO> register(final  @RequestBody RegisterRequestDTO request ) throws Exception {
    	System.out.println("register");
      
        return ResponseEntity.ok().body(service.register(request));
    }

    /**
    End point for user authentication.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResDTO> authenticate(
          final  @RequestBody AuthReqDTO request
    ) {
      
        return ResponseEntity.ok(service.authenticate(request));
    }
}
