package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("licaoHome")
public class LicaoHome extends EntityHome<Licao> {

	@In(create = true)
	AreaimpactadaHome areaimpactadaHome;
	@In(create = true)
	ImpactolicaoHome impactolicaoHome;
	@In(create = true)
	PrioridadelicaoHome prioridadelicaoHome;
	@In(create = true)
	ProjetoHome projetoHome;

	public void setLicaoId(Long id) {
		setId(id);
	}

	public Long getLicaoId() {
		return (Long) getId();
	}

	@Override
	protected Licao createInstance() {
		Licao licao = new Licao();
		return licao;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Areaimpactada areaimpactada = areaimpactadaHome.getDefinedInstance();
		if (areaimpactada != null) {
			getInstance().setAreaimpactada(areaimpactada);
		}
		Impactolicao impactolicao = impactolicaoHome.getDefinedInstance();
		if (impactolicao != null) {
			getInstance().setImpactolicao(impactolicao);
		}
		Prioridadelicao prioridadelicao = prioridadelicaoHome
				.getDefinedInstance();
		if (prioridadelicao != null) {
			getInstance().setPrioridadelicao(prioridadelicao);
		}
		Projeto projeto = projetoHome.getDefinedInstance();
		if (projeto != null) {
			getInstance().setProjeto(projeto);
		}
	}

	public boolean isWired() {
		if (getInstance().getAreaimpactada() == null)
			return false;
		if (getInstance().getImpactolicao() == null)
			return false;
		if (getInstance().getPrioridadelicao() == null)
			return false;
		if (getInstance().getProjeto() == null)
			return false;
		return true;
	}

	public Licao getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
