package com.solo.teste.service;

import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.solo.teste.domain.Pedido;
import com.solo.teste.domain.dto.PedidoDTO;
import com.solo.teste.repository.PedidoRepository;

class PedidoServiceTest {

	@Mock
	private PedidoRepository pedidoRepository;

	@InjectMocks
	private PedidoService pedidoService;

	public PedidoServiceTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testReceberPedidoComSucesso() {

		var pedidoDTO = new PedidoDTO();
		pedidoDTO.setNumeroControle(123);
		pedidoDTO.setNome("Produto Teste");
		pedidoDTO.setValor(100.0);
		pedidoDTO.setCodigoCliente(01L);
		pedidoDTO.setQuantidade(0);

		when(pedidoRepository.findByNumeroControle(anyInt())).thenReturn(empty());

		var mensagemRetorno = pedidoService.receberPedido(pedidoDTO);

		verify(pedidoRepository, times(1)).save(any(Pedido.class));
		assertEquals("Pedido recebido com sucesso!", mensagemRetorno);
	}

	@Test
	void testReceberPedidoComNumeroControleDuplicado() {

		var pedidoDTO = new PedidoDTO();
		pedidoDTO.setNumeroControle(123);
		pedidoDTO.setNome("Produto Teste");
		pedidoDTO.setValor(100.0);
		pedidoDTO.setCodigoCliente(01L);

		when(pedidoRepository.findByNumeroControle(anyInt())).thenReturn(of(new Pedido()));

		var mensagemRetorno = pedidoService.receberPedido(pedidoDTO);

		assertEquals("Número de controle já cadastrado!", mensagemRetorno);
	}

	@Test
	void testListarPedidos() {

		var numeroPedido = 123;
		var dataCadastro = LocalDate.now();

		when(pedidoRepository.findByNumeroControleAndDataCadastro(numeroPedido, dataCadastro))
				.thenReturn(of(new Pedido()));

		when(pedidoRepository.findByNumeroControle(numeroPedido)).thenReturn(of(new Pedido()));

		when(pedidoRepository.findByDataCadastro(dataCadastro)).thenReturn(singletonList(new Pedido()));

		when(pedidoRepository.findAll()).thenReturn(singletonList(new Pedido()));

		List<PedidoDTO> pedidos1 = pedidoService.listarPedidos(numeroPedido, dataCadastro);
		assertEquals(1, pedidos1.size());

		List<PedidoDTO> pedidos2 = pedidoService.listarPedidos(numeroPedido, null);
		assertEquals(1, pedidos2.size());

		List<PedidoDTO> pedidos3 = pedidoService.listarPedidos(null, dataCadastro);
		assertEquals(1, pedidos3.size());

		List<PedidoDTO> pedidos4 = pedidoService.listarPedidos(null, null);
		assertEquals(1, pedidos4.size());
	}

}
