package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("impactolicaoHome")
public class ImpactolicaoHome extends EntityHome<Impactolicao> {

	public void setImpactolicaoId(Long id) {
		setId(id);
	}

	public Long getImpactolicaoId() {
		return (Long) getId();
	}

	@Override
	protected Impactolicao createInstance() {
		Impactolicao impactolicao = new Impactolicao();
		return impactolicao;
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

	public Impactolicao getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Licao> getLicoes() {
		return getInstance() == null ? null : new ArrayList<Licao>(
				getInstance().getLicoes());
	}

}
