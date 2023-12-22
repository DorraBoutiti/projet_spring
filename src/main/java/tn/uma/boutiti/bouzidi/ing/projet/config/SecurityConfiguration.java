package tn.uma.boutiti.bouzidi.ing.projet.config;


import static tn.uma.boutiti.bouzidi.ing.projet.models.Role.ADMIN;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/api/auth/**",
            "/swagger-ui/**",
            "/swagger-ui/index.html#/",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/h2-console/**" 
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    //H2 version
   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    	  MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

    	http.csrf(csrf->csrf.ignoringRequestMatchers(mvcMatcherBuilder.pattern("/api/auth/**"),
                PathRequest.toH2Console()));
        http
               
                .authorizeHttpRequests(req ->
                        req
                              
                                .requestMatchers(mvcMatcherBuilder.pattern("/h2-console/**")).permitAll()  
                              //  .requestMatchers("/api/tasks/**").hasAnyRole(ADMIN.name())
                                .anyRequest().permitAll()
                )
           
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http
        .csrf().disable()  
        .headers().frameOptions().disable();   


        return http.build();
    }
    
    //Sql version
    
  /*  @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http.csrf(csrf->csrf.ignoringRequestMatchers(WHITE_LIST_URL));
        http
               
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers(WHITE_LIST_URL).permitAll()
                              //  .requestMatchers("/api/tasks/**").hasAnyRole(ADMIN.name())
                                .anyRequest().permitAll()
                )
           
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }*/
}
