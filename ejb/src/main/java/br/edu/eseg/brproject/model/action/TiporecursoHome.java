package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("tiporecursoHome")
public class TiporecursoHome extends EntityHome<Tiporecurso> {

	public void setTiporecursoId(Long id) {
		setId(id);
	}

	public Long getTiporecursoId() {
		return (Long) getId();
	}

	@Override
	protected Tiporecurso createInstance() {
		Tiporecurso tiporecurso = new Tiporecurso();
		return tiporecurso;
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

	public Tiporecurso getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Recurso> getRecursos() {
		return getInstance() == null ? null : new ArrayList<Recurso>(
				getInstance().getRecursos());
	}

}
