package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("prioridadelicaoHome")
public class PrioridadelicaoHome extends EntityHome<Prioridadelicao> {

	public void setPrioridadelicaoId(Long id) {
		setId(id);
	}

	public Long getPrioridadelicaoId() {
		return (Long) getId();
	}

	@Override
	protected Prioridadelicao createInstance() {
		Prioridadelicao prioridadelicao = new Prioridadelicao();
		return prioridadelicao;
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

	public Prioridadelicao getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Licao> getLicoes() {
		return getInstance() == null ? null : new ArrayList<Licao>(
				getInstance().getLicoes());
	}

}
