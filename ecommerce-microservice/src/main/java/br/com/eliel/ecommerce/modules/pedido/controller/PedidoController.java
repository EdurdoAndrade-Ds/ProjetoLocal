package br.com.eliel.ecommerce.modules.pedido.controller;

import br.com.eliel.ecommerce.modules.pedido.dto.PedidoRequestDTO;
import br.com.eliel.ecommerce.modules.pedido.dto.PedidoResponseDTO;
import br.com.eliel.ecommerce.modules.pedido.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    @Operation(
        summary = "Cria um novo pedido",
        security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<PedidoResponseDTO> criar(
        @RequestBody PedidoRequestDTO dto,
        Authentication authentication
    ) {
        Long clienteId = Long.parseLong(authentication.getName());
        return new ResponseEntity<>(pedidoService.criar(dto, clienteId), HttpStatus.CREATED);
    }
}