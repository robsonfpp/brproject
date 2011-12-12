package br.edu.eseg.brproject.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.NotNull;

@Entity
@Table(name = "arquivo", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"nome","etapa", "projetoid", "statusprojetoid" }) })
public class Arquivo implements java.io.Serializable {

	private Long id;
	private String nome;
	private String tipo;
	private Long tamanho;
	private byte[] dados;
	private String etapa;
	private Projeto projeto;
	private Statusprojeto statusprojeto;

	public Arquivo() {
	}

	public Arquivo(Long id, String nome, String tipo, Long tamanho,
			byte[] dados, Projeto projeto, Statusprojeto statusprojeto) {
		super();
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
		this.tamanho = tamanho;
		this.dados = dados;
		this.projeto = projeto;
		this.statusprojeto = statusprojeto;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nome", nullable = false, length = 255)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "tipo", nullable = false)
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "tamanho")
	public Long getTamanho() {
		return tamanho != null ? tamanho : 0;
	}

	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "dados", nullable = false)
	public byte[] getDados() {
		return dados;
	}

	public void setDados(byte[] dados) {
		this.dados = dados;
	}

	@ManyToOne(fetch = FetchType.LAZY,optional=true)
	@JoinColumn(name = "projetoid",nullable=true)
	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	@ManyToOne(fetch = FetchType.LAZY,optional=true)
	@JoinColumn(name = "statusprojetoid",nullable=true)
	public Statusprojeto getStatusprojeto() {
		return statusprojeto;
	}

	public void setStatusprojeto(Statusprojeto statusprojeto) {
		this.statusprojeto = statusprojeto;
	}

	@Column(name = "etapa", nullable = false)
	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
}
