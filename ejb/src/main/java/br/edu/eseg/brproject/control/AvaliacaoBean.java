package br.edu.eseg.brproject.control;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.international.StatusMessages;

import br.edu.eseg.brproject.control.transactions.AvaliacaoTx;
import br.edu.eseg.brproject.model.Projeto;
import br.edu.eseg.brproject.model.Usuario;
import br.edu.eseg.brproject.model.action.ProjetoHome;

@Name("avaliacao")
@Scope(ScopeType.PAGE)
public class AvaliacaoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@In
	Usuario loggedUser;
	@In
	StatusMessages statusMessages;
	@In(create = true)
	ProjetoHome projetoHome;
	@In(create = true)
	AvaliacaoTx avaliacaoTx;

	private Projeto projeto = new Projeto();
	private Long avaliadoId;
	private Long notaid;

	@Create
	public void init() {
		projeto = projetoHome.find();
	}

	public void salvarNotaStakeholder() {
		avaliacaoTx.salvarAvaliacao(loggedUser.getId(), avaliadoId,
				projeto.getId(), notaid);
		statusMessages.add(Severity.INFO,"Prioridade salva com sucesso!");
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Long getAvaliadoId() {
		return avaliadoId;
	}

	public void setAvaliadoId(Long avaliadoId) {
		this.avaliadoId = avaliadoId;
	}

	public Long getNotaid() {
		return notaid;
	}

	public void setNotaid(Long notaid) {
		this.notaid = notaid;
	}

}
