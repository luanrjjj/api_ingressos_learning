package com.example.ingressos.eventos;
import java.util.Set;
import org.springframework.util.Assert;

import com.example.ingressos.empresa.Empresa;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class LayoutEvento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @Positive int quantidadeAssentos;
	private boolean aceitaOverbooking;
	@ElementCollection
	private Set<@NotBlank @Pattern(regexp = "[a-zA-Z-]+") String> metaInformacoesAssento;
	@ManyToOne
	private Empresa empresa;
	@NotBlank
	private String nome;

	public LayoutEvento(
		Empresa empresa,
		@Positive int quantidadeAssentos,
		boolean aceitaOverbooking,
		@Size(min = 1) Set<@NotBlank @Pattern(regexp = "[a-zA-Z-]+") String> metaInformacoesAssento,
		@NotBlank String nome
		) {
				Assert.notNull(empresa, "Empresa não pode ser nula");
				Assert.isTrue(metaInformacoesAssento.iterator().hasNext(),
						"Precisa de pelo menos uma metainformacao sobre o assento do evento");
				Assert.isTrue(quantidadeAssentos > 0,
						"O numero de assentos precisa ser positivo");
				Assert.hasText(nome, "O nome do layout precisa estar preenchido");

		this.quantidadeAssentos = quantidadeAssentos;
		this.aceitaOverbooking = aceitaOverbooking;
		this.metaInformacoesAssento = metaInformacoesAssento;
		this.nome = nome;
	}

	public boolean pertenceAEmpresa(@Valid @NotNull Empresa empresa) {
		Assert.notNull(empresa, "Empresa não pode ser nula");
		return this.empresa.equals(empresa);
	}

	public boolean suportaMetaInformacao(String metaInformacao) {
		return this.metaInformacoesAssento.contains(metaInformacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LayoutEvento other = (LayoutEvento) obj;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
