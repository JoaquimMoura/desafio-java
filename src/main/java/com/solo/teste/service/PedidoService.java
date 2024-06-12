package com.solo.teste.service;

import static java.time.LocalDate.now;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.solo.teste.domain.Pedido;
import com.solo.teste.domain.dto.PedidoDTO;
import com.solo.teste.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

	private static final int QUANTIDADE_MAXIMA_DESCONTO_5 = 5;
	private static final int QUANTIDADE_MAXIMA_DESCONTO_10 = 10;
	private static final double DESCONTO_5_PERCENT = 0.05;
	private static final double DESCONTO_10_PERCENT = 0.1;

	private final PedidoRepository repository;

	public String receberPedido(PedidoDTO dto) {

		// regra 2 = Não poderá aceitar um número de controle já cadastrado.
		Optional<PedidoDTO> pedidoExistente = buscarPedidoPorNumeroControle(dto.getNumeroControle());

		if (pedidoExistente.isPresent()) {
			return "Número de controle já cadastrado!";
		}

		// Define a data de cadastro como a data atual, se não foi fornecida
		if (dto.getDataCadastro() == null) {
			dto.setDataCadastro(now());
		}

		// Define a quantidade como 1, se não foi fornecida
		if (dto.getQuantidade() == 0) {
			dto.setQuantidade(1);
		}

		// Calcula o valor total do pedido com desconto, se aplicável
		double valorTotal = dto.getValor() * dto.getQuantidade();

		if (dto.getQuantidade() > QUANTIDADE_MAXIMA_DESCONTO_5) {
			valorTotal *= (1 - DESCONTO_5_PERCENT); // Aplica desconto de 5%
		} else if (dto.getQuantidade() > QUANTIDADE_MAXIMA_DESCONTO_10) {
			valorTotal *= (1 - DESCONTO_10_PERCENT); // Aplica desconto de 10%
		}

		// Salva o valor total do pedido, se necessário
		dto.setValor(valorTotal);

		repository.save(fromDTO(dto));

		return "Pedido recebido com sucesso!";
	}

	public List<PedidoDTO> listarPedidos(Integer numeroPedido, LocalDate dataCadastro) {
		if (numeroPedido != null && dataCadastro != null) {
			return listarPedidosPorNumeroEData(numeroPedido, dataCadastro);
		} else if (numeroPedido != null) {
			return listarPedidosPorNumero(numeroPedido);
		} else if (dataCadastro != null) {
			return listarPedidosPorData(dataCadastro);
		} else {
			return listarTodosPedidos();
		}
	}

	private List<PedidoDTO> listarTodosPedidos() {
		var pedidos = repository.findAll();
		return mapToDTOList(pedidos);
	}

	private List<PedidoDTO> listarPedidosPorNumero(Integer numeroPedido) {
		var pedido = repository.findByNumeroControle(numeroPedido);
		return pedido.map(this::mapToDTO).map(Collections::singletonList).orElse(emptyList());
	}

	private List<PedidoDTO> listarPedidosPorData(LocalDate dataCadastro) {
		List<Pedido> pedidos = repository.findByDataCadastro(dataCadastro);
		return mapToDTOList(pedidos);
	}

	private List<PedidoDTO> listarPedidosPorNumeroEData(Integer numeroPedido, LocalDate dataCadastro) {
		Optional<Pedido> pedido = repository.findByNumeroControleAndDataCadastro(numeroPedido, dataCadastro);
		return pedido.map(this::mapToDTO).map(Collections::singletonList).orElse(emptyList());
	}

	private List<PedidoDTO> mapToDTOList(List<Pedido> pedidos) {
		return pedidos.stream().map(this::mapToDTO).collect(toList());
	}

	public Optional<PedidoDTO> buscarPedidoPorNumeroControle(Integer numeroControle) {
		Optional<Pedido> pedidoOptional = repository.findByNumeroControle(numeroControle);
		return pedidoOptional.map(this::mapToDTO);
	}

	private PedidoDTO mapToDTO(Pedido pedido) {
		var dto = new PedidoDTO();
		dto.setNumeroControle(pedido.getNumeroControle());
		dto.setDataCadastro(pedido.getDataCadastro());
		dto.setNome(pedido.getNome());
		dto.setValor(pedido.getValor());
		dto.setQuantidade(pedido.getQuantidade());
		dto.setCodigoCliente(pedido.getCodigoCliente());
		return dto;
	}

	public static Pedido fromDTO(PedidoDTO dto) {
		var pedido = new Pedido();
		pedido.setNumeroControle(dto.getNumeroControle());
		pedido.setDataCadastro(dto.getDataCadastro() != null ? dto.getDataCadastro() : now());
		pedido.setNome(dto.getNome());
		pedido.setValor(dto.getValor());
		pedido.setQuantidade(dto.getQuantidade() != null ? dto.getQuantidade() : 1);
		pedido.setCodigoCliente(dto.getCodigoCliente());
		return pedido;
	}

}
