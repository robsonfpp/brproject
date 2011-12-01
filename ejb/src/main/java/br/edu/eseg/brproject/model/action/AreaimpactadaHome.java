package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("areaimpactadaHome")
public class AreaimpactadaHome extends EntityHome<Areaimpactada> {

	public void setAreaimpactadaId(Long id) {
		setId(id);
	}

	public Long getAreaimpactadaId() {
		return (Long) getId();
	}

	@Override
	protected Areaimpactada createInstance() {
		Areaimpactada areaimpactada = new Areaimpactada();
		return areaimpactada;
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

	public Areaimpactada getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Licao> getLicoes() {
		return getInstance() == null ? null : new ArrayList<Licao>(
				getInstance().getLicoes());
	}

}
