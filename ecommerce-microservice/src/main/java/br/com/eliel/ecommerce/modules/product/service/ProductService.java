package br.com.eliel.ecommerce.modules.product.service;

import br.com.eliel.ecommerce.modules.product.dto.ProductRequestDTO;
import br.com.eliel.ecommerce.modules.product.dto.ProductResponseDTO;
import br.com.eliel.ecommerce.modules.product.entities.Product;
import br.com.eliel.ecommerce.modules.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public ProductResponseDTO criar(ProductRequestDTO dto) {
        Product product = new Product();
        product.setNome(dto.getNome());
        product.setDescricao(dto.getDescricao());
        product.setPreco(dto.getPreco());
        product.setEstoque(dto.getEstoque());

        Product saved = repository.save(product);
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(saved.getId());
        response.setNome(saved.getNome());
        response.setDescricao(saved.getDescricao());
        response.setPreco(saved.getPreco());
        response.setEstoque(saved.getEstoque());

        return response;
    }

    public List<ProductResponseDTO> listar() {
        return repository.findAll().stream().map(produto -> {
            ProductResponseDTO dto = new ProductResponseDTO();
            dto.setId(produto.getId());
            dto.setNome(produto.getNome());
            dto.setDescricao(produto.getDescricao());
            dto.setPreco(produto.getPreco());
            dto.setEstoque(produto.getEstoque());
            return dto;
        }).collect(Collectors.toList());
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
