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
		return http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(Customizer.withDefaults()).authorizeHttpRequests(auth -> auth
						// --- Endpoints Públicos (não exigem login) ---
						.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/users").permitAll()
						.requestMatchers(HttpMethod.GET, "/books").permitAll()
						.requestMatchers(HttpMethod.GET, "/books/**").permitAll().requestMatchers("/h2-console/**")
						.permitAll()

						// Apenas usuários com o papel de CLIENTE podem manipular o carrinho
						.requestMatchers("/carts/**").hasRole("CLIENTE")
						// Apenas CLIENTES podem criar pedidos a partir do carrinho
						.requestMatchers(HttpMethod.POST, "/orders/from-cart/**").hasRole("CLIENTE")
						// Qualquer usuário LOGADO pode ver seus próprios pedidos
						.requestMatchers("/orders/**").authenticated()
						// Apenas ADMIN pode criar/alterar/deletar livros (POST, PUT, PATCH, DELETE)
						.requestMatchers("/books/**").hasRole("ADMIN")
						// Apenas ADMIN pode listar, buscar por id, atualizar ou deletar outros usuários
						.requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
						.requestMatchers("/authors/**").hasRole("ADMIN")

						// --- Endpoints Autenticados (qualquer usuário logado) ---
						.anyRequest().authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())).build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

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
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
