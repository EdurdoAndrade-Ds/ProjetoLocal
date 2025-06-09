package br.com.eliel.ecommerce.modules.pedido.service;

import br.com.eliel.ecommerce.modules.pedido.dto.PedidoRequestDTO;
import br.com.eliel.ecommerce.modules.pedido.dto.PedidoResponseDTO;
import br.com.eliel.ecommerce.modules.pedido.entity.ItemPedido;
import br.com.eliel.ecommerce.modules.pedido.entity.Pedido;
import br.com.eliel.ecommerce.modules.pedido.repository.PedidoRepository;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public PedidoResponseDTO criar(PedidoRequestDTO dto, Long clienteId) {
        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);

        List<ItemPedido> itens = dto.getItens().stream().map(itemDTO -> {
            ItemPedido item = new ItemPedido();
            item.setProdutoId(itemDTO.getProdutoId());
            item.setNomeProduto(itemDTO.getNomeProduto());
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(BigDecimal.TEN); // Preço fixo
            item.setPedido(pedido);
            return item;
        }).collect(Collectors.toList());

        pedido.setItens(itens);
        pedido.setTotal(itens.stream()
            .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
            .reduce(BigDecimal.ZERO, BigDecimal::add));

        Pedido salvo = pedidoRepository.save(pedido);

        return mapToResponseDTO(salvo);
    }

    public List<PedidoResponseDTO> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO buscarPorId(Long id, Long clienteId) {
        Pedido pedido = pedidoRepository.findByIdAndClienteId(id, clienteId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado ou acesso negado"));
        return mapToResponseDTO(pedido);
    }

    public void cancelar(Long id, Long clienteId) {
        Pedido pedido = pedidoRepository.findByIdAndClienteId(id, clienteId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado ou acesso negado"));
        pedido.setCancelado(true);
        pedidoRepository.save(pedido);
    }

    public List<PedidoResponseDTO> historico(Long clienteId) {
        return pedidoRepository.findByClienteIdAndCanceladoTrue(clienteId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private PedidoResponseDTO mapToResponseDTO(Pedido pedido) {
        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setId(pedido.getId());
        response.setClienteId(pedido.getClienteId());
        response.setTotal(pedido.getTotal());
        response.setItens(pedido.getItens().stream().map(i -> {
            PedidoResponseDTO.ItemDTO ri = new PedidoResponseDTO.ItemDTO();
            ri.setProdutoId(i.getProdutoId());
            ri.setNomeProduto(i.getNomeProduto());
            ri.setQuantidade(i.getQuantidade());
            ri.setPrecoUnitario(i.getPrecoUnitario());
            return ri;
        }).toList());
        return response;
    }
}