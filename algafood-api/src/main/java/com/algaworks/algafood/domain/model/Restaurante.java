package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@ValorZeroIncluiDescricao(ValorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private BigDecimal taxaFrete;
	
	
	@ManyToOne
	@JoinColumn(name = "cozinha_codigo", nullable = false)
	private Cozinha cozinha;
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	@Embedded
	private Endereco endereco;
	
	private boolean ativo = Boolean.TRUE;
	
	private boolean abertura = Boolean.TRUE;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id"), 
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet();
	
	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel", joinColumns = @JoinColumn(name = "restaurante_id"), 
	inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> responsaveis = new HashSet<>();
	
	
	
	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}
	
	public void abertura() {
		setAbertura(true);
	}
	
	public void fechamento() {
		setAbertura(false);
	}
	
	public boolean adicionarPagamento(FormaPagamento pagamento) {
		return getFormasPagamento().add(pagamento);
	}
	
	public boolean removerFormaPagamento(FormaPagamento pagamento) {
		return getFormasPagamento().remove(pagamento);
	}
	
	public boolean adicionarResponsavel(Usuario usuario) {
		return getResponsaveis().add(usuario);
	}
	
	public boolean removerResponsavel(Usuario usuario) {
		return getResponsaveis().remove(usuario);
	}
	
	public boolean aceitaFormaPagamento(FormaPagamento pagamento) {
		return getFormasPagamento().contains(pagamento);
	}
	
	public boolean naoAceitaFormaPagamento(FormaPagamento pagamento) {
		return !aceitaFormaPagamento(pagamento);
	}
}
