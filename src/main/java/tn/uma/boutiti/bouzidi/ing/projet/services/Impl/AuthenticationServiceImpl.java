package tn.uma.boutiti.bouzidi.ing.projet.services.Impl;

 

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tn.uma.boutiti.bouzidi.ing.projet.config.JwtService;
import tn.uma.boutiti.bouzidi.ing.projet.dto.AuthReqDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.AuthResDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.RegisterRequestDTO;
import tn.uma.boutiti.bouzidi.ing.projet.exceptions.UsernameAlreadyExistsException;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;
import tn.uma.boutiti.bouzidi.ing.projet.repository.MemberRepository;
import tn.uma.boutiti.bouzidi.ing.projet.services.AuthenticationService;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	 @Autowired 
	  private  PasswordEncoder passwordEncoder;
	 @Autowired
	  private  MemberRepository repository;
	 @Autowired
	  private   JwtService jwtService;
	 @Autowired
	  private final AuthenticationManager authenticationManager;
	 @Override
	 public AuthResDTO register(RegisterRequestDTO request) {
	     try {
	         if (repository.findByUsername(request.getUserName()).isPresent()) {
	        	  return AuthResDTO.builder()
	 	                 .msg("Username already exists")
	 	                 .build();
	         }

	         var member = Member.builder()
	                 .username(request.getUserName())
	                 .password(passwordEncoder.encode(request.getPassword()))
	                 .role(request.getRole())
	                 .build();

	         var savedUser = repository.save(member);
	         var jwtToken = jwtService.generateToken(member);

	         return AuthResDTO.builder()
	                 .msg("User created successfully")
	                 .accessToken(jwtToken)
	                 .build();
	     }   catch (Exception e) {
	         
	         return AuthResDTO.builder()
	                 .msg("User creation failed")
	                 .build();
	     }
	 }


@Override
public AuthResDTO authenticate(AuthReqDTO request) {
    try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
    } catch (BadCredentialsException e) {
        // Handle bad credentials (invalid username or password)
        return AuthResDTO.builder()
                .msg("Invalid username or password")
                .build();
    } catch (Exception e) {
        // Handle other authentication exceptions
        return AuthResDTO.builder()
                .msg("Authentication failed")
                .build();
    }

    var user = repository.findByUsername(request.getUserName())
            .orElseThrow(); // Assuming user is present after successful authentication

    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    return AuthResDTO.builder()
            .msg("Login successfully")
            .accessToken(jwtToken)
            .build();
}

}
