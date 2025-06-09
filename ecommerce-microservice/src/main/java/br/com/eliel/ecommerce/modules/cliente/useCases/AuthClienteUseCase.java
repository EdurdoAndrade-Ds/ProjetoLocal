package br.com.eliel.ecommerce.modules.cliente.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.eliel.ecommerce.exceptions.AuthenticationException;
import br.com.eliel.ecommerce.modules.cliente.dto.AuthClienteDTO;
import br.com.eliel.ecommerce.modules.cliente.dto.AuthClienteResponseDTO;
import br.com.eliel.ecommerce.modules.cliente.repositories.ClienteRepository;
import br.com.eliel.ecommerce.providers.JWTProvider;

import java.util.Arrays;

@Service
public class AuthClienteUseCase {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JWTProvider jwtProvider;
    
    public AuthClienteResponseDTO execute(AuthClienteDTO authClienteDTO) {
        var cliente = this.clienteRepository.findByUsernameOrEmail(authClienteDTO.getUsername(), authClienteDTO.getUsername())
            .orElseThrow(() -> {
                throw new AuthenticationException();
            });
            
        var passwordMatches = this.passwordEncoder.matches(authClienteDTO.getSenha(), cliente.getSenha());
        
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        if (!cliente.isAtivo()) {
            throw new AuthenticationException("Cliente desativado");
        }
        
        var token = jwtProvider.generateToken(cliente.getId().toString(), Arrays.asList("CLIENTE"));
        
        return AuthClienteResponseDTO.builder()
            .token(token)
            .id(cliente.getId())
            .username(cliente.getUsername())
            .build();
    }
}

