package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("tarefaHome")
public class TarefaHome extends EntityHome<Tarefa> {

	@In(create = true)
	ProjetoHome projetoHome;
	@In(create = true)
	TarefaHome tarefaHome;
	
	public void setTarefaId(Long id) {
		setId(id);
	}

	public Long getTarefaId() {
		return (Long) getId();
	}

	@Override
	protected Tarefa createInstance() {
		Tarefa tarefa = new Tarefa();
		return tarefa;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Projeto projeto = projetoHome.getDefinedInstance();
		if (projeto != null) {
			getInstance().setProjeto(projeto);
		}
		Tarefa tarefaPai = tarefaHome.getDefinedInstance();
		if(tarefaPai != null){
			getInstance().setTarefaPai(tarefaPai);
		}
	}

	public boolean isWired() {
		if (getInstance().getProjeto() == null)
			return false;
		return true;
	}

	public Tarefa getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Utilizacaorecurso> getUtilizacaorecursos() {
		return getInstance() == null ? null : new ArrayList<Utilizacaorecurso>(
				getInstance().getUtilizacaorecursos());
	}

}
