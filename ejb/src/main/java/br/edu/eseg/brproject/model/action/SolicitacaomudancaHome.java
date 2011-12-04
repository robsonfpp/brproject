package br.edu.eseg.brproject.model.action;

import java.util.Date;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import br.edu.eseg.brproject.model.Projeto;
import br.edu.eseg.brproject.model.Solicitacaomudanca;
import br.edu.eseg.brproject.model.Statusmudanca;
import br.edu.eseg.brproject.model.Usuario;

@Name("solicitacaomudancaHome")
public class SolicitacaomudancaHome extends EntityHome<Solicitacaomudanca> {

	@In(create = true)
	ProjetoHome projetoHome;
	@In(create = true)
	StatusmudancaHome statusmudancaHome;
	@In(create = true)
	UsuarioHome usuarioHome;

	public void setSolicitacaomudancaId(Long id) {
		setId(id);
	}

	public Long getSolicitacaomudancaId() {
		return (Long) getId();
	}

	@Override
	protected Solicitacaomudanca createInstance() {
		Solicitacaomudanca solicitacaomudanca = new Solicitacaomudanca();
		return solicitacaomudanca;
	}

	@Override
	public String update() {
		if(isIdDefined() && getInstance().getStatusmudanca().getId() > 1){
			getInstance().setDatafechamento(new Date());
		}
		return super.update();
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Projeto projeto = projetoHome.getDefinedInstance();
		if (projeto != null) {
			getInstance().setProjeto(projeto);
		}
		Statusmudanca statusmudanca = statusmudancaHome.getDefinedInstance();
		if (statusmudanca != null) {
			getInstance().setStatusmudanca(statusmudanca);
		}
		Usuario usuario = usuarioHome.getDefinedInstance();
		if (usuario != null) {
			getInstance().setUsuario(usuario);
		}
	}

	public boolean isWired() {
		if (getInstance().getProjeto() == null)
			return false;
		if (getInstance().getStatusmudanca() == null)
			return false;
		if (getInstance().getUsuario() == null)
			return false;
		return true;
	}

	public Solicitacaomudanca getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
