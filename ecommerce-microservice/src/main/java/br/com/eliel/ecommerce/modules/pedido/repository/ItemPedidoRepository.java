package br.com.eliel.ecommerce.modules.pedido.repository;

import br.com.eliel.ecommerce.modules.pedido.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}