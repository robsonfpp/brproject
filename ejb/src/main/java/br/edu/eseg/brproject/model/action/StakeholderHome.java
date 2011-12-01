package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("stakeholderHome")
public class StakeholderHome extends EntityHome<Stakeholder> {

	@In(create = true)
	ProjetoHome projetoHome;
	@In(create = true)
	UsuarioHome usuarioHome;

	public void setStakeholderId(Long id) {
		setId(id);
	}

	public Long getStakeholderId() {
		return (Long) getId();
	}

	@Override
	protected Stakeholder createInstance() {
		Stakeholder stakeholder = new Stakeholder();
		return stakeholder;
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
		Usuario usuario = usuarioHome.getDefinedInstance();
		if (usuario != null) {
			getInstance().setUsuario(usuario);
		}
	}

	public boolean isWired() {
		if (getInstance().getProjeto() == null)
			return false;
		if (getInstance().getUsuario() == null)
			return false;
		return true;
	}

	public Stakeholder getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Notastakeholder> getNotaavaliadores() {
		return getInstance() == null ? null : new ArrayList<Notastakeholder>(
				getInstance().getNotaavaliadores());
	}

	public List<Notastakeholder> getNotaavaliados() {
		return getInstance() == null ? null : new ArrayList<Notastakeholder>(
				getInstance().getNotaavaliados());
	}

}
