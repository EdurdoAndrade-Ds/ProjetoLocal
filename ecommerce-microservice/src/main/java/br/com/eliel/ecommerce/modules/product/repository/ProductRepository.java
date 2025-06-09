package br.com.eliel.ecommerce.modules.product.repository;

import br.com.eliel.ecommerce.modules.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
