package com.solo.teste.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solo.teste.domain.dto.PedidoDTO;
import com.solo.teste.service.PedidoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pedidos")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping(
            value = "receberPedidos",
            produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE},
            consumes = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> receberPedidos(@Valid @RequestBody List<PedidoDTO> pedidos) {
        log.info("*Regra 1* -> O arquivo pode conter 1 ou mais pedidos, limitado a 10 ");
        if (pedidos.isEmpty() || pedidos.size() > 10) {
            return ResponseEntity.status(BAD_REQUEST).body("O número de pedidos deve estar entre 1 e 10.");
        }

        var response = new StringBuilder();
        
        for (PedidoDTO pedido : pedidos) {
            String result = service.receberPedido(pedido);
            response.append(result).append("\n");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

    @GetMapping(produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
    public List<PedidoDTO> listarPedidos(
            @RequestParam(value = "numeroPedido", required = false) Integer numeroPedido,
            @RequestParam(value = "dataCadastro", required = false) LocalDate dataCadastro) {
        return service.listarPedidos(numeroPedido, dataCadastro);
    }
}
