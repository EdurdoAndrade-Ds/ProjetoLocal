package br.com.eliel.ecommerce.modules.pedido.controller;

import br.com.eliel.ecommerce.modules.pedido.dto.PedidoRequestDTO;
import br.com.eliel.ecommerce.modules.pedido.dto.PedidoResponseDTO;
import br.com.eliel.ecommerce.modules.pedido.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Cria um novo pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<PedidoResponseDTO> criar(@RequestBody PedidoRequestDTO dto) {
        return new ResponseEntity<>(pedidoService.criar(dto), HttpStatus.CREATED);
    }
}