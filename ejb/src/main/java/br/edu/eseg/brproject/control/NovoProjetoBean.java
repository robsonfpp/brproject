package br.edu.eseg.brproject.control;

import java.io.Serializable;
import java.util.HashSet;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

import br.edu.eseg.brproject.control.transactions.ProjetoTx;
import br.edu.eseg.brproject.model.Projeto;
import br.edu.eseg.brproject.model.Stakeholder;
import br.edu.eseg.brproject.model.Statusprojeto;
import br.edu.eseg.brproject.model.Usuario;
import br.edu.eseg.brproject.model.action.ProjetoHome;

@Name("novoProjeto")
@Scope(ScopeType.PAGE)
public class NovoProjetoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Logger
	Log log;
	@In
	StatusMessages statusMessages;
	@In
	Usuario loggedUser;
	@In(create = true)
	ProjetoTx projetoTx;
	@In(create = true)
	ProjetoHome projetoHome;

	private Projeto projeto = new Projeto();
	private Stakeholder stakeholder = new Stakeholder();

	@Create()
	public void init() {
		if (projetoHome.getProjetoId() != null) {
			projetoHome.load();
			projeto = projetoHome.getDefinedInstance();
		}
	}

	public void addStakeholder() {
		try {
			Stakeholder stk = new Stakeholder(projeto,
					stakeholder.getUsuario(), stakeholder.getCcb(),
					stakeholder.getPapel(), new HashSet(), new HashSet());
			if (!projeto.getStakeholders().contains(stk)) {
				projeto.getStakeholders().add(stk);
				statusMessages.add(Severity.INFO,
						"Stakeholder '{0}' adicionado com sucesso", stakeholder
								.getUsuario().getNome());
			} else {
				statusMessages.add(Severity.WARN,
						"Stakeholder '{0}' já foi adicionado!", stakeholder
								.getUsuario().getNome());
			}
			cleanStakeholder();
		} catch (Exception e) {
			statusMessages.add(Severity.ERROR,
					"Erro ao adicionar o stakeholder '{0}'", e);
			log.error("Erro ao adicionar stakeholder!", e);
		}
	}

	public void removeStakeholder(Stakeholder stakeholder) {
		System.out.println(stakeholder.getUsuario().getNome());
		boolean r = projeto.getStakeholders().remove(stakeholder);
		log.info("Stakeholder {0} foi removido", r ? "" : "Não");
	}

	public String salvarProjeto() {
		projeto.setUsuario(loggedUser);
		Statusprojeto s = new Statusprojeto();
		s.setId(new Long(1));
		projeto.setStatusprojeto(s);
		if (projetoHome.getProjetoId() != null) {
			try {
				projetoTx.updateProjeto(projeto);
				statusMessages
						.add(Severity.INFO, "Projeto atualizado com sucesso!");
			} catch (Exception e) {
				statusMessages.add(Severity.ERROR, "Erro ao atualizar o projeto!");
				log.error("Erro ao cirar o projeto", e);
				return "erro";
			}
		} else {
			try {
				Long projetoId = projetoTx.createProjeto(projeto);
				projetoHome.setProjetoId(projetoId);
				statusMessages.add(Severity.INFO,
						"Projeto criado com sucesso!");
			} catch (Exception e) {
				statusMessages.add(Severity.ERROR,
						"Erro ao criar o projeto!");
				log.error("Erro ao atualizar o projeto", e);
				return "erro";
			}
		}
		return "salvou";
	}

	public void cleanStakeholder() {
		stakeholder = new Stakeholder();
	}

	public void addUsuario(Usuario u) {
		stakeholder.setUsuario(u);
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Stakeholder getStakeholder() {
		return stakeholder;
	}

	public void setStakeholder(Stakeholder stakeholder) {
		this.stakeholder = stakeholder;
	}

}