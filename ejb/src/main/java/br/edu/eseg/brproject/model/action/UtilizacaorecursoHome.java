package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("utilizacaorecursoHome")
public class UtilizacaorecursoHome extends EntityHome<Utilizacaorecurso> {

	@In(create = true)
	RecursoHome recursoHome;
	@In(create = true)
	TarefaHome tarefaHome;
	@In(create = true)
	TipocustoHome tipocustoHome;

	public void setUtilizacaorecursoId(UtilizacaorecursoId id) {
		setId(id);
	}

	public UtilizacaorecursoId getUtilizacaorecursoId() {
		return (UtilizacaorecursoId) getId();
	}

	public UtilizacaorecursoHome() {
		setUtilizacaorecursoId(new UtilizacaorecursoId());
	}

	@Override
	public boolean isIdDefined() {
		if (getUtilizacaorecursoId().getRecursoid() == 0)
			return false;
		if (getUtilizacaorecursoId().getTarefaid() == 0)
			return false;
		return true;
	}

	@Override
	protected Utilizacaorecurso createInstance() {
		Utilizacaorecurso utilizacaorecurso = new Utilizacaorecurso();
		utilizacaorecurso.setId(new UtilizacaorecursoId());
		return utilizacaorecurso;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Recurso recurso = recursoHome.getDefinedInstance();
		if (recurso != null) {
			getInstance().setRecurso(recurso);
		}
		Tarefa tarefa = tarefaHome.getDefinedInstance();
		if (tarefa != null) {
			getInstance().setTarefa(tarefa);
		}
		Tipocusto tipocusto = tipocustoHome.getDefinedInstance();
		if (tipocusto != null) {
			getInstance().setTipocusto(tipocusto);
		}
	}

	public boolean isWired() {
		if (getInstance().getRecurso() == null)
			return false;
		if (getInstance().getTarefa() == null)
			return false;
		if (getInstance().getTipocusto() == null)
			return false;
		return true;
	}

	public Utilizacaorecurso getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
