package br.com.eliel.ecommerce.modules.cliente.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.eliel.ecommerce.modules.cliente.entities.ClienteEntity;
import br.com.eliel.ecommerce.modules.cliente.repositories.ClienteRepository;

@Service
public class CreateClienteUseCase {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public ClienteEntity execute(ClienteEntity clienteEntity) {
        var cliente = this.clienteRepository.findByUsernameOrEmail(clienteEntity.getUsername(), clienteEntity.getEmail());
        
        if (cliente.isPresent()) {
            throw new RuntimeException("Cliente já existe");
        }
        
        var senha = passwordEncoder.encode(clienteEntity.getSenha());
        clienteEntity.setSenha(senha);
        
        var clienteCreated = this.clienteRepository.save(clienteEntity);
        return clienteCreated;
    }
}

