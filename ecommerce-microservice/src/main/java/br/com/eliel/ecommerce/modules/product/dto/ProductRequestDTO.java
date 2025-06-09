package br.com.eliel.ecommerce.modules.product.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDTO {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer estoque;

    // Getters e Setters
}
