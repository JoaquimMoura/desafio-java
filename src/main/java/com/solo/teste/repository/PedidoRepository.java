package com.solo.teste.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solo.teste.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	Optional<Pedido> findByNumeroControle(Integer numeroControle);

	List<Pedido> findByDataCadastro(LocalDate dataCadastro);

	Optional<Pedido> findByNumeroControleAndDataCadastro(Integer numeroPedido, LocalDate dataCadastro);
}
