package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("tarefaList")
public class TarefaList extends EntityQuery<Tarefa> {

	private static final String EJBQL = "select tarefa from Tarefa tarefa";

	private static final String[] RESTRICTIONS = { "lower(tarefa.nome) like lower(concat(#{tarefaList.tarefa.nome},'%'))",
			"tarefa.projeto.id = #{tarefaList.tarefa.projeto.id}" };

	private Tarefa tarefa = new Tarefa();

	public TarefaList() {
		tarefa.setProjeto(new Projeto());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(1000);
	}

	public Tarefa getTarefa() {
		return tarefa;
	}
}
