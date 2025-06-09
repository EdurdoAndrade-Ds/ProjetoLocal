package br.com.eliel.ecommerce.modules.pagamento.entity;

import br.com.eliel.ecommerce.modules.pedido.entity.Pedido;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;
    private String status;

    @OneToOne
    private Pedido pedido;

    private LocalDateTime data;

    // Getters e setters
}