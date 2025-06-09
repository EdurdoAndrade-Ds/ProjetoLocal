package br.com.eliel.ecommerce.modules.pedido.dto;

import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {
    private Long clienteId;
    private List<ItemDTO> itens;

    @Data
    public static class ItemDTO {
        private Long produtoId;
        private Integer quantidade;
    }
}