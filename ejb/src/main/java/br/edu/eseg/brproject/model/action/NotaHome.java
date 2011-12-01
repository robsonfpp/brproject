package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("notaHome")
public class NotaHome extends EntityHome<Nota> {

	public void setNotaId(Long id) {
		setId(id);
	}

	public Long getNotaId() {
		return (Long) getId();
	}

	@Override
	protected Nota createInstance() {
		Nota nota = new Nota();
		return nota;
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

	public Nota getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Notastakeholder> getNotastakeholders() {
		return getInstance() == null ? null : new ArrayList<Notastakeholder>(
				getInstance().getNotastakeholders());
	}

}
