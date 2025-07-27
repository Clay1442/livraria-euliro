package br.com.euliro.livraria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
	                .requestMatchers(HttpMethod.POST, "/users").permitAll()
	                .requestMatchers("/h2-console/**").permitAll()
	                
	                // --- REGRAS DE AUTORIZAÇÃO (A MUDANÇA ESTÁ AQUI) ---
	                // Apenas quem tem o papel "ADMIN" pode acessar QUALQUER endpoint de /users
	                .requestMatchers("/users/**").hasRole("ADMIN") 
	                
	                // Para os livros, qualquer um pode ver (GET), mas só ADMIN pode criar/alterar/deletar
	                .requestMatchers(HttpMethod.GET, "/books").permitAll()
	                .requestMatchers(HttpMethod.GET, "/books/**").permitAll()
	                .requestMatchers("/books/**").hasRole("ADMIN")
	                
		                	                
	                // Qualquer outra requisição precisa estar autenticada (mas não exige um papel específico)
	                .anyRequest().authenticated()
	            )
	            // 2. Registra nosso filtro para rodar antes do filtro padrão de autenticação
	            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
	            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
	            .build();
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
