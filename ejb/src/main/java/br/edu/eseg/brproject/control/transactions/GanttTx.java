package br.edu.eseg.brproject.control.transactions;

import java.util.List;

import javax.ejb.Local;

import br.edu.eseg.brproject.model.Projeto;
import br.edu.eseg.brproject.model.Recurso;
import br.edu.eseg.brproject.model.Tarefa;
import br.edu.eseg.brproject.model.Utilizacaorecurso;

@Local
public interface GanttTx {

	public Long createRecurso(Recurso r);

	public void saveTarefa(Tarefa tarefa);

	public void excluirTarefa(Tarefa tarefa);

	public void destroy();

//	public Projeto getProjetoById(Long projetoId);
//
//	public Tarefa getTarefaById(Long tarefaId);
//
//	public List<Tarefa> getPredecessores(Long tarefaId);
//
//	public List<Recurso> getRecursos(Long projetoId);
//
//	public List<Tarefa> getSubtarefas(Long tarefaId);
//
//	public List<Utilizacaorecurso> getUtilizacaorecursos(Long tarefaId);
//
//	public List<Tarefa> getTarefas(Long projetoId);
//
//	public List<Tarefa> getMacroTarefas(Long projetoId);

}