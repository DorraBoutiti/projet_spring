package tn.uma.boutiti.bouzidi.ing.projet.services.Impl;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.uma.boutiti.bouzidi.ing.projet.config.JwtService;
import tn.uma.boutiti.bouzidi.ing.projet.dto.AuthReqDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.AuthResDTO;
import tn.uma.boutiti.bouzidi.ing.projet.dto.RegisterRequestDTO;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;
import tn.uma.boutiti.bouzidi.ing.projet.repository.MemberRepository;
import tn.uma.boutiti.bouzidi.ing.projet.services.AuthenticationService;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	/** Used for encoding and decoding passwords */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/** Repository for accessing member data */
	@Autowired
	private MemberRepository repository;

	/** Service for handling JSON Web Tokens (JWT) */
	@Autowired
	private JwtService jwtService;

	/** Authentication manager for managing authentication process */
	@Autowired
	private final AuthenticationManager authenticationManager;
	
	/**
	 * Registers a new user.
	 */
	@Override
	public AuthResDTO register(RegisterRequestDTO request) {
	    AuthResDTO response;
	    System.out.println(request);
	    try {
	        if (repository.findByUsername(request.getUserName()).isPresent()) {
	            response = AuthResDTO.builder()
	                    .msg("Username already exists")
	                    .build();
	        } else {
	          final  Member member = Member.builder()
	                    .username(request.getUserName())
	                    .password(passwordEncoder.encode(request.getPassword()))
	                    .role(request.getRole())
	                    .build();
	          repository.save(member);
	           final String jwtToken = jwtService.generateToken(member);

	            response = AuthResDTO.builder()
	                    .msg("User created successfully")
	                    .accessToken(jwtToken)
	                    .build();
	            
	        }
	    } catch (Exception e) {
	        response = AuthResDTO.builder()
	                .msg("User creation failed")
	                .build();
	    }
	    return response;
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
  

    return AuthResDTO.builder()
            .msg("Login successfully")
            .accessToken(jwtToken)
            .build();
}

}
