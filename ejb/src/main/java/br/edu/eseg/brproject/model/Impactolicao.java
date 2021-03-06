package br.edu.eseg.brproject.model;

// Generated May 29, 2011 12:04:47 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Impactolicao generated by hbm2java
 */
@Entity
@Table(name = "impactolicao" )
public class Impactolicao implements java.io.Serializable {

	private Long id;
	private String nome;
	private Set<Licao> licoes = new HashSet<Licao>(0);

	public Impactolicao() {
	}

	public Impactolicao(String nome, Set<Licao> licoes) {
		this.nome = nome;
		this.licoes = licoes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nome")
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "impactolicao")
	public Set<Licao> getLicoes() {
		return this.licoes;
	}

	public void setLicoes(Set<Licao> licoes) {
		this.licoes = licoes;
	}

}
