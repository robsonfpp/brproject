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
 * Tiporecurso generated by hbm2java
 */
@Entity
@Table(name = "tiporecurso" )
public class Tiporecurso implements java.io.Serializable {

	private Long id;
	private String nome;
	private Set<Recurso> recursos = new HashSet<Recurso>(0);

	public Tiporecurso() {
	}
	
	public Tiporecurso(Long id) {
		this.id = id;
	}

	public Tiporecurso(String nome, Set<Recurso> recursos) {
		this.nome = nome;
		this.recursos = recursos;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tiporecurso")
	public Set<Recurso> getRecursos() {
		return this.recursos;
	}

	public void setRecursos(Set<Recurso> recursos) {
		this.recursos = recursos;
	}

}
