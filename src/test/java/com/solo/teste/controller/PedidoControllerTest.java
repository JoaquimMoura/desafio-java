package com.solo.teste.controller;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solo.teste.domain.dto.PedidoDTO;
import com.solo.teste.service.PedidoService;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PedidoService pedidoService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		openMocks(this);
	}

	@Test
	void testReceberPedidosComErroListaVazia() throws Exception {
		mockMvc.perform(post("/pedidos/receberPedidos").contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(emptyList()))).andExpect(status().isBadRequest())
				.andExpect(content().string("O número de pedidos deve estar entre 1 e 10."));
	}

	@Test
	void testReceberPedidosComSucesso() throws Exception {

		var pedidos = new ArrayList<>();

		var pedidoInvalido = new PedidoDTO();
		pedidoInvalido.setNumeroControle(1);
		pedidoInvalido.setNome("VAGA DEV");
		pedidoInvalido.setValor(10.0);
		pedidoInvalido.setCodigoCliente(01L);

		pedidos.add(pedidoInvalido);

		when(pedidoService.receberPedido(pedidoInvalido)).thenReturn("Pedido recebido com sucesso");

		mockMvc.perform(post("/pedidos/receberPedidos").contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pedidos))).andExpect(status().isOk());
	}

	@Test
	void testReceberPedidosComErroNumeroPedidos() throws Exception {

		var pedidos = new ArrayList<>();

		for (int i = 0; i < 11; i++) {
			var pedidoDTO = new PedidoDTO();
			pedidoDTO.setNumeroControle(i + 1);
			pedidoDTO.setNome("Produto " + (i + 1));
			pedidoDTO.setValor(10.0);
			pedidoDTO.setCodigoCliente((long) (i + 1));

			pedidos.add(pedidoDTO);
		}

		when(pedidoService.receberPedido(Mockito.any(PedidoDTO.class))).thenReturn("Pedido recebido com sucesso");

		mockMvc.perform(post("/pedidos/receberPedidos").contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pedidos))).andExpect(status().isBadRequest())
				.andExpect(content().string("O número de pedidos deve estar entre 1 e 10."));
	}

}
