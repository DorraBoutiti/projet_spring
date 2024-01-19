package tn.uma.boutiti.bouzidi.ing.projet.config;


import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import lombok.RequiredArgsConstructor;
import static  tn.uma.boutiti.bouzidi.ing.projet.models.Role.USER;

import java.util.Arrays;

import static  tn.uma.boutiti.bouzidi.ing.projet.models.Role.ADMIN;

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
            
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http
                .authorizeHttpRequests(req ->
                        {
							try {
								req
										.requestMatchers(mvcMatcherBuilder.pattern("/h2-console/**")).permitAll()
								        .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui/**")).permitAll()
								        .requestMatchers(mvcMatcherBuilder.pattern("/swagger-ui.html")).permitAll()
								        .requestMatchers(mvcMatcherBuilder.pattern("/v3/api-docs/**")).permitAll()
								        .requestMatchers(mvcMatcherBuilder.pattern("/api/auth/**")).permitAll()
								        .requestMatchers(mvcMatcherBuilder.pattern("/api/user/**"))
								        .hasAnyRole(USER.name(),ADMIN.name())
								      
								        .requestMatchers(mvcMatcherBuilder.pattern("/api/tasks/**"))
								        .hasAnyRole(ADMIN.name())
								        .requestMatchers(mvcMatcherBuilder.pattern("/api/members/**"))
								        .hasAnyRole(ADMIN.name())
								        .requestMatchers(mvcMatcherBuilder.pattern("/api/labels/**"))
								        .hasAnyRole(ADMIN.name())
								        .requestMatchers(mvcMatcherBuilder.pattern("/api/projects/**"))
								        .hasAnyRole(ADMIN.name())
								        .anyRequest().authenticated()
								        .and()
								        .cors(cors -> cors 
								                .configurationSource(request -> {
								                    CorsConfiguration corsConfiguration = new CorsConfiguration();
								                    corsConfiguration.setAllowCredentials(true);
								                    corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
								                    corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
								                            "Accept", "Authorization", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method",
								                            "Access-Control-Request-Headers"));
								                    corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
								                            "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
								                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
								                    return corsConfiguration;
								                }));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
                                
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().disable());

        return http.build();
    }


    //Sql version
    
/*    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http.csrf(csrf->csrf.ignoringRequestMatchers(WHITE_LIST_URL));
        http
               
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers(WHITE_LIST_URL).permitAll()
                              //  .requestMatchers("/api/tasks/**").hasAnyRole(ADMIN.name())
                              //  .requestMatchers("/labels").hasAnyRole(USER.name())
                              //  .requestMatchers("/api/tasks/**").hasAnyRole(USER.name())
                            
                               
                                .anyRequest().permitAll()
                )
           
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    } */
 
}
