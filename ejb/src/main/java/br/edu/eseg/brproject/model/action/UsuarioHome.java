package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("usuarioHome")
public class UsuarioHome extends EntityHome<Usuario> {

	public void setUsuarioId(Long id) {
		setId(id);
	}

	public Long getUsuarioId() {
		return (Long) getId();
	}

	@Override
	protected Usuario createInstance() {
		Usuario usuario = new Usuario();
		return usuario;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Usuario getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Projeto> getProjetos() {
		return getInstance() == null ? null : new ArrayList<Projeto>(
				getInstance().getProjetos());
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

}
