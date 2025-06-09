package br.com.eliel.ecommerce.modules.pedido.repository;

import br.com.eliel.ecommerce.modules.pedido.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}