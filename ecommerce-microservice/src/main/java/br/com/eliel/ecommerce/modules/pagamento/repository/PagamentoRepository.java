package br.com.eliel.ecommerce.modules.pagamento.repository;

import br.com.eliel.ecommerce.modules.pagamento.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    Optional<Pagamento> findByPedidoId(Long pedidoId);
}