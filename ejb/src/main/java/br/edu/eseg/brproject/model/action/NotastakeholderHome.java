package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("notastakeholderHome")
public class NotastakeholderHome extends EntityHome<Notastakeholder> {

	@In(create = true)
	NotaHome notaHome;
	@In(create = true)
	ProjetoHome projetoHome;
	@In(create = true)
	StakeholderHome stakeholderHome;

	public void setNotastakeholderId(Long id) {
		setId(id);
	}

	public Long getNotastakeholderId() {
		return (Long) getId();
	}

	@Override
	protected Notastakeholder createInstance() {
		Notastakeholder notastakeholder = new Notastakeholder();
		return notastakeholder;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Nota nota = notaHome.getDefinedInstance();
		if (nota != null) {
			getInstance().setNota(nota);
		}
		Projeto projeto = projetoHome.getDefinedInstance();
		if (projeto != null) {
			getInstance().setProjeto(projeto);
		}
		Stakeholder stakeholderavaliado = stakeholderHome.getDefinedInstance();
		if (stakeholderavaliado != null) {
			getInstance().setStakeholderavaliado(stakeholderavaliado);
		}
		Stakeholder stakeholderavaliador = stakeholderHome.getDefinedInstance();
		if (stakeholderavaliador != null) {
			getInstance().setStakeholderavaliador(stakeholderavaliador);
		}
	}

	public boolean isWired() {
		if (getInstance().getNota() == null)
			return false;
		if (getInstance().getProjeto() == null)
			return false;
		if (getInstance().getStakeholderavaliado() == null)
			return false;
		if (getInstance().getStakeholderavaliador() == null)
			return false;
		return true;
	}

	public Notastakeholder getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
