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
import javax.persistence.UniqueConstraint;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(name = "usuario" )
public class Usuario implements java.io.Serializable {

	private Long id;
	private String ddd;
	private String email;
	private String endereco;
	private Boolean gp;
	private String login;
	private String nome;
	private String senha;
	private String telefone;
	private Set<Projeto> projetos = new HashSet<Projeto>(0);
	private Set<Stakeholder> stakeholders = new HashSet<Stakeholder>(0);
	private Set<Solicitacaomudanca> solicitacaomudancas = new HashSet<Solicitacaomudanca>(
			0);

	public Usuario() {
	}

	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public Usuario(String ddd, String email, String endereco, Boolean gp,
			String login, String nome, String senha, String telefone,
			Set<Projeto> projetos, Set<Stakeholder> stakeholders,
			Set<Solicitacaomudanca> solicitacaomudancas) {
		this.ddd = ddd;
		this.email = email;
		this.endereco = endereco;
		this.gp = gp;
		this.login = login;
		this.nome = nome;
		this.senha = senha;
		this.telefone = telefone;
		this.projetos = projetos;
		this.stakeholders = stakeholders;
		this.solicitacaomudancas = solicitacaomudancas;
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

	@Column(name = "ddd", length = 2)
	@Length(max = 2)
	public String getDdd() {
		return this.ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	@Column(name = "email")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "endereco")
	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Column(name = "gp")
	public Boolean getGp() {
		return this.gp;
	}

	public void setGp(Boolean gp) {
		this.gp = gp;
	}

	@Column(name = "login", unique = true, nullable = false, length = 45)
	@NotNull
	@Length(max = 45)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "nome")
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "senha", nullable = false, length = 12)
	@NotNull
	@Length(max = 12)
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Column(name = "telefone", length = 8)
	@Length(max = 8)
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Projeto> getProjetos() {
		return this.projetos;
	}

	public void setProjetos(Set<Projeto> projetos) {
		this.projetos = projetos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Stakeholder> getStakeholders() {
		return this.stakeholders;
	}

	public void setStakeholders(Set<Stakeholder> stakeholders) {
		this.stakeholders = stakeholders;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<Solicitacaomudanca> getSolicitacaomudancas() {
		return this.solicitacaomudancas;
	}

	public void setSolicitacaomudancas(
			Set<Solicitacaomudanca> solicitacaomudancas) {
		this.solicitacaomudancas = solicitacaomudancas;
	}
	
}
