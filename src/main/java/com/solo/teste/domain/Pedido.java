package com.solo.teste.domain;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "Pedido")
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotNull
	@Column(name = "numero_controle")
	private Integer numeroControle;

	@Column(name = "data_cadastro")
	private LocalDate dataCadastro;

	@NotNull
	private String nome;

	@NotNull
	private Double valor;

	@Column(name = "quantidade")
	private Integer quantidade;

	@NotNull
	@Column(name = "codigo_cliente")
	private Long codigoCliente;
}
