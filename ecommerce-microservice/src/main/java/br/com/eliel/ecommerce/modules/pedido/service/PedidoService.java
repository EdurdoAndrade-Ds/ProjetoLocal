package br.com.eliel.ecommerce.modules.pedido.service;

import br.com.eliel.ecommerce.modules.pedido.dto.PedidoRequestDTO;
import br.com.eliel.ecommerce.modules.pedido.dto.PedidoResponseDTO;
import br.com.eliel.ecommerce.modules.pedido.entity.ItemPedido;
import br.com.eliel.ecommerce.modules.pedido.entity.Pedido;
import br.com.eliel.ecommerce.modules.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setClienteId(dto.getClienteId());

        List<ItemPedido> itens = dto.getItens().stream().map(itemDTO -> {
            ItemPedido item = new ItemPedido();
            item.setProdutoId(itemDTO.getProdutoId());
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(BigDecimal.TEN); // Preço fixo temporário
            item.setPedido(pedido);
            return item;
        }).collect(Collectors.toList());

        pedido.setItens(itens);
        pedido.setTotal(itens.stream()
            .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
            .reduce(BigDecimal.ZERO, BigDecimal::add));

        Pedido salvo = pedidoRepository.save(pedido);

        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setId(salvo.getId());
        response.setClienteId(salvo.getClienteId());
        response.setTotal(salvo.getTotal());
        response.setItens(itens.stream().map(i -> {
            PedidoResponseDTO.ItemDTO ri = new PedidoResponseDTO.ItemDTO();
            ri.setProdutoId(i.getProdutoId());
            ri.setQuantidade(i.getQuantidade());
            ri.setPrecoUnitario(i.getPrecoUnitario());
            return ri;
        }).toList());

        return response;
    }
}