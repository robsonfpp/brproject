package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("projetoHome")
public class ProjetoHome extends EntityHome<Projeto> {

	@In(create = true)
	StatusprojetoHome statusprojetoHome;
	@In(create = true)
	UsuarioHome usuarioHome;

	public void setProjetoId(Long id) {
		setId(id);
	}

	public Long getProjetoId() {
		return (Long) getId();
	}

	@Override
	protected Projeto createInstance() {
		Projeto projeto = new Projeto();
		return projeto;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Statusprojeto statusprojeto = statusprojetoHome.getDefinedInstance();
		if (statusprojeto != null) {
			getInstance().setStatusprojeto(statusprojeto);
		}
		Usuario usuario = usuarioHome.getDefinedInstance();
		if (usuario != null) {
			getInstance().setUsuario(usuario);
		}
	}

	public boolean isWired() {
		if (getInstance().getStatusprojeto() == null)
			return false;
		if (getInstance().getUsuario() == null)
			return false;
		return true;
	}

	public Projeto getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Licao> getLicoes() {
		return getInstance() == null ? null : new ArrayList<Licao>(
				getInstance().getLicoes());
	}

	public List<Notastakeholder> getNotastakeholders() {
		return getInstance() == null ? null : new ArrayList<Notastakeholder>(
				getInstance().getNotastakeholders());
	}

	public List<Solicitacaomudanca> getSolicitacaomudancas() {
		return getInstance() == null ? null
				: new ArrayList<Solicitacaomudanca>(getInstance()
						.getSolicitacaomudancas());
	}

	public List<Stakeholder> getStakeholders() {
		return getInstance() == null ? null : new ArrayList<Stakeholder>(
				getInstance().getStakeholders());
	}

	public List<Tarefa> getTarefas() {
		return getInstance() == null ? null : new ArrayList<Tarefa>(
				getInstance().getTarefas());
	}
	
	public List<Arquivo> getArquivos() {
		return getInstance() == null ? null : new ArrayList<Arquivo>(
				getInstance().getArquivos());
	}

}
