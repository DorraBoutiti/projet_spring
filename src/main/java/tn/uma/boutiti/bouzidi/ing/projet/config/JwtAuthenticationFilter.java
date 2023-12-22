package tn.uma.boutiti.bouzidi.ing.projet.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.beans.Transient;
import java.io.IOException;
import java.security.Security;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
 
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
 

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
	  System.out.println("hellooooo fromm doFilterInternal");
    if (request.getServletPath().contains("/api/auth")) {
    	
    	System.out.println("hellooooo fromm auth");
      filterChain.doFilter(request, response);
      return;
    }
      String authHeader = request.getHeader("Authorization");
      String jwt;
      String userName;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
    	System.out.println("hellooooo fromm Bearer");
      filterChain.doFilter(request, response);
      return;
    }
    jwt = authHeader.substring(7);
    userName = jwtService.extractUsername(jwt);
    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
    	System.out.println("hellooooo fromm userName");
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
    /*  var isTokenValid = tokenRepository.findByToken(jwt)
          .map(t -> !t.isExpired() && !t.isRevoked())
          .orElse(false);*/
      if (jwtService.isTokenValid(jwt, userDetails) /*&& isTokenValid*/) {
    	  System.out.println("hellooooo fromm jwtService");
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    System.out.println("hellooooo fromm last");
    filterChain.doFilter(request, response);
  }
}