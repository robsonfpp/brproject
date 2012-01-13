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

	public String salvarProjeto() {
		projeto.setUsuario(loggedUser);
		Statusprojeto s = new Statusprojeto();
		s.setId(new Long(1));
		projeto.setStatusprojeto(s);
		if (projetoHome.getProjetoId() != null) {
			try {
				projetoTx.updateProjeto(projeto);
				statusMessages.add(Severity.INFO,
						"Projeto atualizado com sucesso!");
			} catch (Exception e) {
				statusMessages.add(Severity.ERROR,
						"Erro ao atualizar o projeto!");
				log.error("Erro ao cirar o projeto", e);
				return "erro";
			}
		} else {
			try {
				Long projetoId = projetoTx.createProjeto(projeto);
				projetoHome.setProjetoId(projetoId);
				statusMessages
						.add(Severity.INFO, "Projeto criado com sucesso!");
			} catch (Exception e) {
				statusMessages.add(Severity.ERROR, "Erro ao criar o projeto!");
				log.error("Erro ao atualizar o projeto", e);
				return "erro";
			}
		}
		return "salvou";
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

}