package br.com.eliel.ecommerce.modules.pagamento.controller;

import br.com.eliel.ecommerce.modules.pagamento.dto.PagamentoRequestDTO;
import br.com.eliel.ecommerce.modules.pagamento.dto.PagamentoResponseDTO;
import br.com.eliel.ecommerce.modules.pagamento.service.PagamentoService;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> criar(@RequestBody PagamentoRequestDTO dto) {
        return new ResponseEntity<>(pagamentoService.criar(dto), HttpStatus.CREATED);
    }
}