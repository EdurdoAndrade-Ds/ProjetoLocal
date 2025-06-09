package br.com.eliel.ecommerce.modules.cliente.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eliel.ecommerce.modules.cliente.dto.ProfileClienteResponseDTO;
import br.com.eliel.ecommerce.modules.cliente.useCases.ProfileClienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Cliente", description = "Informações do cliente")
public class ProfileClienteController {

    @Autowired
    private ProfileClienteUseCase profileClienteUseCase;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
        summary = "Perfil do cliente", 
        description = "Rota responsável por buscar as informações do perfil do cliente logado",
        security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Perfil do cliente retornado com sucesso",
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "Resposta de Sucesso",
                        value = """
                        {
                            "nome": "João Silva",
                            "username": "joaosilva",
                            "email": "joao@email.com",
                            "telefone": "(11) 99999-9999",
                            "endereco": "Rua das Flores, 123",
                            "cidade": "São Paulo",
                            "estado": "SP",
                            "cep": "01234-567"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Object> profile(HttpServletRequest request) {
        try {
            var clienteId = UUID.fromString(request.getAttribute("cliente_id").toString());
            var cliente = this.profileClienteUseCase.execute(clienteId);
            return ResponseEntity.ok().body(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID do cliente inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

