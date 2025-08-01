package br.com.euliro.livraria.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.euliro.livraria.repositories.UserRepository;
import br.com.euliro.livraria.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//
		var token = this.recoverToken(request);

		if (token != null) {
			// 2. Se um token foi enviado, valida-o
			var email = tokenService.validateToken(token);

			if (!email.isEmpty()) {
				// 3. Se o token for válido, busca o usuário no banco
				UserDetails user = userRepository.findByEmail(email)
						.orElseThrow(() -> new RuntimeException("Usuário não encontrado no filtro de segurança"));

				// 4. Cria um objeto de autenticação e o coloca no contexto de segurança do
				// Spring
				var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		// 5. Continua a cadeia de filtros
		filterChain.doFilter(request, response);
	}

	// Método auxiliar para extrair o token do cabeçalho "Authorization"
	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return null;
		}
		return authHeader.replace("Bearer ", "");

	}

}
