package br.com.eliel.ecommerce.modules.pagamento.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PagamentoRequestDTO {
    private Long pedidoId;
    private BigDecimal valor;
}