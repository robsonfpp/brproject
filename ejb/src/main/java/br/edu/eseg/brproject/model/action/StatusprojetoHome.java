package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("statusprojetoHome")
public class StatusprojetoHome extends EntityHome<Statusprojeto> {

	public void setStatusprojetoId(Long id) {
		setId(id);
	}

	public Long getStatusprojetoId() {
		return (Long) getId();
	}

	@Override
	protected Statusprojeto createInstance() {
		Statusprojeto statusprojeto = new Statusprojeto();
		return statusprojeto;
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

	public Statusprojeto getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Projeto> getProjetos() {
		return getInstance() == null ? null : new ArrayList<Projeto>(
				getInstance().getProjetos());
	}

}
