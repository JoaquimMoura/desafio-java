package com.solo.teste.domain.dto;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@XmlRootElement(name = "pedido")
@XmlAccessorType(XmlAccessType.FIELD)
public class PedidoDTO {

	@NotNull(message = "O número de controle não pode ser nulo")
	@XmlElement(name = "numeroControle")
	@JsonProperty(value = "numeroControle")
	private Integer numeroControle;

	@JsonProperty(value = "dataCadastro")
	@XmlElement(name = "dataCadastro")
	private LocalDate dataCadastro;

	@NotNull(message = "O nome do produto não pode ser nulo")
	@Size(min = 1, message = "O nome do produto deve ter pelo menos 1 caractere")
	@XmlElement(name = "nome")
	@JsonProperty(value = "nome")
	private String nome;

	@NotNull(message = "O valor do produto não pode ser nulo")
	@Positive(message = "O valor do produto deve ser positivo")
	@XmlElement(name = "valor")
	@JsonProperty(value = "valor")
	private Double valor;

	@XmlElement(name = "quantidade")
	@JsonProperty(value = "quantidade")
	private Integer quantidade = 0;

	@NotNull(message = "O código do cliente não pode ser nulo")
	@XmlElement(name = "codigoCliente")
	@JsonProperty(value = "codigoCliente")
	private Long codigoCliente;
}
