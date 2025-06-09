package br.com.eliel.ecommerce.modules.cliente.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eliel.ecommerce.modules.cliente.entities.ClienteEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByUsernameOrEmail(String username, String email);
    Optional<ClienteEntity> findByUsername(String username);
    Optional<ClienteEntity> findByEmail(String email);
}

