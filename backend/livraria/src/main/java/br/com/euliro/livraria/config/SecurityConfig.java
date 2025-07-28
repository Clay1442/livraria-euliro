package br.com.euliro.livraria.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.euliro.livraria.security.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private SecurityFilter securityFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http
	    		.csrf(csrf -> csrf.disable())
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	            .cors(Customizer.withDefaults()) 
	            .authorizeHttpRequests(auth -> auth        
	            	    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
	            	    .requestMatchers(HttpMethod.POST, "/users").permitAll()
	            	    .requestMatchers(HttpMethod.GET, "/books").permitAll()  
	            	    .requestMatchers(HttpMethod.GET, "/books/**").permitAll() 

	            
	            	    // Apenas ADMIN pode ver, atualizar ou deletar usuários
	            	    .requestMatchers("/users/**").hasRole("ADMIN") 
	            	    // Apenas ADMIN pode criar, atualizar ou deletar livros
	            	    .requestMatchers("/books/**").hasRole("ADMIN")
	           
	            	    .anyRequest().authenticated() 
	            	
	            )
	            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
	            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
	            .build();
	    }
            
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource(){
		
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
		
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	
}
