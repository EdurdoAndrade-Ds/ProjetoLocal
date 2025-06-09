package br.com.eliel.ecommerce.modules.cliente.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthClienteResponseDTO {
    private String token;
    private Long id;
    private String username;
}

