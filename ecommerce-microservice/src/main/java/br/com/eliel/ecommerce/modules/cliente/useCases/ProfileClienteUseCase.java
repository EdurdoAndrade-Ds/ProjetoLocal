package br.com.eliel.ecommerce.modules.cliente.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eliel.ecommerce.modules.cliente.dto.ProfileClienteResponseDTO;
import br.com.eliel.ecommerce.modules.cliente.repositories.ClienteRepository;

@Service
public class ProfileClienteUseCase {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public ProfileClienteResponseDTO execute(UUID clienteId) {
        var cliente = this.clienteRepository.findById(clienteId)
            .orElseThrow(() -> {
                throw new RuntimeException("Cliente não encontrado");
            });

        var clienteDTO = ProfileClienteResponseDTO.builder()
            .nome(cliente.getNome())
            .username(cliente.getUsername())
            .email(cliente.getEmail())
            .telefone(cliente.getTelefone())
            .endereco(cliente.getEndereco())
            .cidade(cliente.getCidade())
            .estado(cliente.getEstado())
            .cep(cliente.getCep())
            .build();

        return clienteDTO;
    }
}

