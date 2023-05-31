package com.example.ingressos.eventos;

import java.util.HashSet;
import java.util.Set;

import com.example.ingressos.empresa.Empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class NovoLayoutRequest {

	@Positive
	private int quantidadeAssentos;
	private Set<@NotBlank @Pattern(regexp = "[a-zA-Z-]+") String> metaInformacoesAssento = new HashSet<>();
	@NotNull
	private Boolean aceitaOverbooking;
	private String nome;

	public NovoLayoutRequest(@Positive int quantidadeAssentos, Boolean aceitaOverbooking) {
		super();
		this.quantidadeAssentos = quantidadeAssentos;
		this.aceitaOverbooking = aceitaOverbooking;
	}

	public void setMetaInformacoesAssento(Set<String> metaInformacoesAssento) {
		this.metaInformacoesAssento = metaInformacoesAssento;
	}

	public LayoutEvento toLayout(Empresa empresa) {
		if(metaInformacoesAssento.isEmpty()) {
			//aqui podia ser alguma meta-informacao configurada
			metaInformacoesAssento.add("geral");
		}
		return new LayoutEvento(empresa, quantidadeAssentos,aceitaOverbooking,metaInformacoesAssento, nome);
	}

}
