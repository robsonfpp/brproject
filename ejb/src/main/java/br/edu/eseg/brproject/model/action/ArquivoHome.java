package br.edu.eseg.brproject.model.action;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

import br.edu.eseg.brproject.model.Arquivo;
import br.edu.eseg.brproject.model.Projeto;
import br.edu.eseg.brproject.model.Statusprojeto;

@Name("arquivoHome")
public class ArquivoHome extends EntityHome<Arquivo> {

	@In(create = true)
	StatusprojetoHome statusprojetoHome;
	@In(create = true)
	ProjetoHome projetoHome;

	public void setArquivoId(Long id) {
		setId(id);
	}

	public Long getArquivoId() {
		return (Long) getId();
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Statusprojeto statusprojeto = statusprojetoHome.getDefinedInstance();
		if (statusprojeto != null) {
			getInstance().setStatusprojeto(statusprojeto);
		}
		Projeto projeto = projetoHome.getDefinedInstance();
		if (projeto != null) {
			getInstance().setProjeto(projeto);
		}
	}

	public boolean isWired() {
		if (getInstance().getStatusprojeto() == null)
			return false;
		if (getInstance().getProjeto() == null)
			return false;
		return true;
	}

	public Arquivo getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	@Override
	protected Arquivo createInstance() {
		Arquivo arquivo = new Arquivo();
		return arquivo;
	}

}
