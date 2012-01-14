package br.edu.eseg.brproject.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;

import br.edu.eseg.brproject.control.transactions.GanttTx;
import br.edu.eseg.brproject.model.Projeto;
import br.edu.eseg.brproject.model.Recurso;
import br.edu.eseg.brproject.model.Tarefa;
import br.edu.eseg.brproject.model.Tiporecurso;
import br.edu.eseg.brproject.model.Utilizacaorecurso;
import br.edu.eseg.brproject.model.action.TarefaList;

@Name("tarefa")
@Scope(ScopeType.PAGE)
public class TarefaBean {

	@Logger
	Log log;
	@In
	StatusMessages statusMessages;
	@In(create = true)
	GanttTx ganttTx;
	@In(create = true)
	TarefaList tarefaList;

	private Long projetoId;
	private Long tarefaId;
	private Tarefa tarefa;
	private String nomeRecurso;
	private Long tipoId;
	private Long tarefapaiId;
	private Integer percentComp;
	// para selecionar o pai
	private List<SelectItem> tarefaspai = new ArrayList<SelectItem>();
	// para selecionar o predecessor
	private List<Tarefa> tarefas = new ArrayList<Tarefa>();
	// para selecionar os recursos
	private List<Recurso> recursos = new ArrayList<Recurso>();
	// predecessores selecionados
	private List<Tarefa> tarefaspredecessoras = new ArrayList<Tarefa>();
	// recursos selecionados
	private List<Recurso> recursosAlocados = new ArrayList<Recurso>();

	public void prepareEditor() {
		log.info("iniciando o bean " + this.getClass().getName());
		log.info("projetoid: " + projetoId);
		log.info("tarefaid: " + tarefaId);

		tarefas = getTarefas(projetoId);
		recursos = getRecursos(projetoId);

		tarefa = getTarefaById(tarefaId);

		if (tarefa != null) {
			tarefas.remove(tarefa);

			for (Tarefa t : tarefa.getTarefaspredecessoras()) {
				Tarefa pred = new Tarefa(t.getId());
				pred.setEap(t.getEap());
				pred.setNome(t.getNome());
				tarefaspredecessoras.add(pred);
			}

			for (Utilizacaorecurso ut : tarefa.getUtilizacaorecursos()) {
				recursosAlocados.add(ut.getRecurso());
			}

			if (tarefa.getTarefaPai() != null) {
				tarefapaiId = tarefa.getTarefaPai().getId();
			}

			percentComp = (int) Math.floor((tarefa.getPorcentcomp() * 100));
		} else {
			tarefa = new Tarefa();
			tarefa.setProjeto(getProjetoById(projetoId));
			tarefa.setInicio(tarefa.getProjeto().getInicio());
			Calendar c = new GregorianCalendar();
			c.setTime(tarefa.getProjeto().getInicio());
			c.add(Calendar.DATE, 1);
			tarefa.setFim(c.getTime());
			percentComp = 0;
		}

		if (tarefas.size() > 0) {
			tarefas.remove(0);
		}
		for (Tarefa t : tarefas) {
			if (t.getMilestone() != null && t.getMilestone())
				continue;
			tarefaspai.add(new SelectItem(t.getId(), "[" + t.getEap() + "] "
					+ t.getNome()));
		}
		recursos.removeAll(recursosAlocados);
		tarefas.removeAll(tarefaspredecessoras);
	}

	public void salvarTarefa() {
		log.info("Dados da tarefa: " + tarefa);
		log.info("predecessores: " + tarefaspredecessoras.size());
		log.info("recursos: " + recursosAlocados.size());
		log.info("Tarefa pai: " + tarefapaiId);
		log.info("Porcentagem completa: " + percentComp + "%");

		if (tarefapaiId != null) {
			tarefa.setTarefaPai(getTarefaById(tarefapaiId));
		}
		if (tarefa.getMilestone() != null && tarefa.getMilestone()) {
			tarefa.setFim(tarefa.getInicio());
		} else {
			if (!tarefa.getInicio().before(tarefa.getFim())) {
				statusMessages.add(Severity.ERROR,
						"A data de inicio deve ser menor que a data final!");
				return;
			}
		}
		tarefa.setTarefaspredecessoras(new HashSet<Tarefa>(tarefaspredecessoras));
		tarefa.setUtilizacaorecursos(new HashSet<Utilizacaorecurso>());
		for (Recurso r : recursosAlocados) {
			Utilizacaorecurso ur = new Utilizacaorecurso();
			ur.setRecurso(r);
			ur.setTarefa(tarefa);
			tarefa.getUtilizacaorecursos().add(ur);
		}
		tarefa.setPorcentcomp(new Double(percentComp) / 100);

		ganttTx.saveTarefa(tarefa);
		statusMessages.add(Severity.INFO, "Tarefa salva com sucesso!");
	}

	public void excluirTarefa() {
		log.info("Excluindo a tarefa: " + tarefa);
		ganttTx.excluirTarefa(tarefa);
		statusMessages.add(Severity.INFO, "Tarefa excluída com sucesso!");
	}

	public void addRecurso() {
		log.info("adicionando recurso:" + nomeRecurso);
		log.info("projetoid: " + projetoId);
		Recurso r = new Recurso();
		r.setNome(nomeRecurso);
		r.setTiporecurso(new Tiporecurso(tipoId));
		r.setProjeto(getProjetoById(projetoId));
		Long id = ganttTx.createRecurso(r);
		r.setId(id);
		recursos.add(r);
		nomeRecurso = null;
		tipoId = null;
	}

	public List<Tarefa> getTarefas(Long projetoId) {
		System.out.println("projetoid: " + projetoId);
		if (projetoId == null)
			return null;
		Query q = tarefaList.getEntityManager().createNativeQuery(
				"select * from tarefa where projetoid = ?1 order by eap",
				Tarefa.class);
		q.setParameter(1, projetoId);
		return q.getResultList();
	}

	public List<Recurso> getRecursos(Long projetoId) {
		Query q = tarefaList.getEntityManager().createNativeQuery(
				"select * from recurso where projetoid = ?1", Recurso.class);
		q.setParameter(1, projetoId);
		return q.getResultList();
	}

	public Tarefa getTarefaById(Long tarefaId) {
		Query q = tarefaList.getEntityManager().createNativeQuery("select * from tarefa where id = ?",
				Tarefa.class);
		q.setParameter(1, tarefaId);
		List<Tarefa> result = q.getResultList();
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	public Projeto getProjetoById(Long projetoId) {
		Query q = tarefaList.getEntityManager().createNativeQuery("select * from projeto where id = ?",
				Projeto.class);
		q.setParameter(1, projetoId);
		List<Projeto> result = q.getResultList();
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	public Long getProjetoId() {
		return projetoId;
	}

	public void setProjetoId(Long projetoId) {
		this.projetoId = projetoId;
	}

	public Long getTarefaId() {
		return tarefaId;
	}

	public void setTarefaId(Long tarefaId) {
		this.tarefaId = tarefaId;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public String getNomeRecurso() {
		return nomeRecurso;
	}

	public void setNomeRecurso(String nomeRecurso) {
		this.nomeRecurso = nomeRecurso;
	}

	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}

	public Long getTarefapaiId() {
		return tarefapaiId;
	}

	public void setTarefapaiId(Long tarefapaiId) {
		this.tarefapaiId = tarefapaiId;
	}

	public Integer getPercentComp() {
		return percentComp;
	}

	public void setPercentComp(Integer percentComp) {
		this.percentComp = percentComp;
	}

	public List<SelectItem> getTarefaspai() {
		return tarefaspai;
	}

	public void setTarefaspai(List<SelectItem> tarefaspai) {
		this.tarefaspai = tarefaspai;
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public List<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	public List<Tarefa> getTarefaspredecessoras() {
		return tarefaspredecessoras;
	}

	public void setTarefaspredecessoras(List<Tarefa> tarefaspredecessoras) {
		this.tarefaspredecessoras = tarefaspredecessoras;
	}

	public List<Recurso> getRecursosAlocados() {
		return recursosAlocados;
	}

	public void setRecursosAlocados(List<Recurso> recursosAlocados) {
		this.recursosAlocados = recursosAlocados;
	}

}
