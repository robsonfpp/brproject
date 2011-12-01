package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("statusmudancaHome")
public class StatusmudancaHome extends EntityHome<Statusmudanca> {

	public void setStatusmudancaId(Long id) {
		setId(id);
	}

	public Long getStatusmudancaId() {
		return (Long) getId();
	}

	@Override
	protected Statusmudanca createInstance() {
		Statusmudanca statusmudanca = new Statusmudanca();
		return statusmudanca;
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

	public Statusmudanca getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Solicitacaomudanca> getSolicitacaomudancas() {
		return getInstance() == null ? null
				: new ArrayList<Solicitacaomudanca>(getInstance()
						.getSolicitacaomudancas());
	}

}
