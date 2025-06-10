package br.com.eliel.ecommerce.modules.pagamento.service;

import br.com.eliel.ecommerce.modules.pagamento.dto.*;
import br.com.eliel.ecommerce.modules.pagamento.entity.Pagamento;
import br.com.eliel.ecommerce.modules.pagamento.repository.PagamentoRepository;
import br.com.eliel.ecommerce.modules.pedido.entity.Pedido;
import br.com.eliel.ecommerce.modules.pedido.repository.PedidoRepository;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Data
@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public PagamentoResponseDTO criar(PagamentoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setValor(dto.getValor());
        pagamento.setStatus("PENDENTE");
        pagamento.setData(LocalDateTime.now());

        pagamento = pagamentoRepository.save(pagamento);

        PagamentoResponseDTO response = new PagamentoResponseDTO();
        response.setId(pagamento.getId());
        response.setPedidoId(pedido.getId());
        response.setValor(pagamento.getValor());
        response.setStatus(pagamento.getStatus());
        response.setData(pagamento.getData());

        return response;
    }
}