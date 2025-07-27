package br.com.euliro.livraria.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.euliro.livraria.dto.LoginRequestDTO;
import br.com.euliro.livraria.dto.LoginResponseDTO;
import br.com.euliro.livraria.entities.User;
import br.com.euliro.livraria.services.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
    	var  usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        
    	var auth = this.authenticationManager.authenticate(usernamePassword);
        
        var user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);
        
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}