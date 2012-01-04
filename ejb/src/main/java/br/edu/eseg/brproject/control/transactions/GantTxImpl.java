package br.edu.eseg.brproject.control.transactions;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import br.edu.eseg.brproject.model.Tarefa;
import br.edu.eseg.brproject.model.action.TarefaHome;
import br.edu.eseg.brproject.model.action.TarefaList;

@Stateful
@Name("GantTxImpl")
public class GantTxImpl implements GanttTx {

	@PersistenceContext
	EntityManager em;
	@In(create = true)
	TarefaHome tarefaHome;
	@In(create = true)
	TarefaList tarefaList;

	@Override
	public List<Tarefa> getMacroTarefas(Long projetoId) {
		Query q = tarefaList.getEntityManager().createNativeQuery("select * from tarefa t join projeto p on p.id = t.projetoid join subtarefa st on st.tarefaid = t.id or t.milestone group by t.id where t.projetoid = ?",Tarefa.class);
		q.setParameter(1, projetoId);
		return q.getResultList();
	}

	@Override
	public List<Tarefa> getTarefas(Long projetoId) {
		Query q = tarefaList.getEntityManager().createNativeQuery("select * from tarefa t join projeto p on p.id = t.projetoid where t.projetoid = ?",Tarefa.class);
		q.setParameter(1, projetoId);
		return q.getResultList();
	}
	
	@Override
	@Remove
	public void destroy(){}

}
