package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("recursoHome")
public class RecursoHome extends EntityHome<Recurso> {

	@In(create = true)
	TiporecursoHome tiporecursoHome;

	public void setRecursoId(Long id) {
		setId(id);
	}

	public Long getRecursoId() {
		return (Long) getId();
	}

	@Override
	protected Recurso createInstance() {
		Recurso recurso = new Recurso();
		return recurso;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Tiporecurso tiporecurso = tiporecursoHome.getDefinedInstance();
		if (tiporecurso != null) {
			getInstance().setTiporecurso(tiporecurso);
		}
	}

	public boolean isWired() {
		return true;
	}

	public Recurso getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Utilizacaorecurso> getUtilizacaorecursos() {
		return getInstance() == null ? null : new ArrayList<Utilizacaorecurso>(
				getInstance().getUtilizacaorecursos());
	}

}
