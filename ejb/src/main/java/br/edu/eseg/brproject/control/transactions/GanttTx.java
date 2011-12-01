package br.edu.eseg.brproject.control.transactions;

import java.util.List;

import javax.ejb.Local;

import br.edu.eseg.brproject.model.Tarefa;


@Local
public interface GanttTx {

	public List<Tarefa> getMacroTarefas(Long projetoId);

	public List<Tarefa> getTarefas(Long projetoId);

	void destroy();
	
}