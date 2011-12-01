package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("tipocustoHome")
public class TipocustoHome extends EntityHome<Tipocusto> {

	public void setTipocustoId(Long id) {
		setId(id);
	}

	public Long getTipocustoId() {
		return (Long) getId();
	}

	@Override
	protected Tipocusto createInstance() {
		Tipocusto tipocusto = new Tipocusto();
		return tipocusto;
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

	public Tipocusto getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Utilizacaorecurso> getUtilizacaorecursos() {
		return getInstance() == null ? null : new ArrayList<Utilizacaorecurso>(
				getInstance().getUtilizacaorecursos());
	}

}
