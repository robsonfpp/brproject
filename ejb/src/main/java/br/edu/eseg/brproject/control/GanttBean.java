package br.edu.eseg.brproject.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;
import org.jfree.util.ObjectUtilities;

import br.edu.eseg.brproject.control.transactions.GanttTx;
import br.edu.eseg.brproject.model.Projeto;
import br.edu.eseg.brproject.model.Recurso;
import br.edu.eseg.brproject.model.Tarefa;
import br.edu.eseg.brproject.model.Tiporecurso;
import br.edu.eseg.brproject.model.Utilizacaorecurso;
import br.edu.eseg.brproject.model.action.TarefaList;

import com.google.gson.Gson;

@Name("gantt")
@Scope(ScopeType.CONVERSATION)
public class GanttBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Logger
	Log log;
	@In
	StatusMessages statusMessages;
	@In(create = true)
	GanttTx ganttTx;
	@In(create = true)
	TarefaList tarefaList;
	EntityManager em;

	private Long projetoId;
	private Long tarefaId;
	private Tarefa tarefa;
	private String nomeRecurso;
	private Long tipoId;
	private Long tarefapaiId;
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

	@Create
	public void init() {
		em = tarefaList.getEntityManager();
	}

	public void prepareEditor() {
		log.info("iniciando o bean " + this.getClass().getName());
		log.info("projetoid: " + projetoId);
		log.info("tarefaid: " + tarefaId);

		tarefas = getTarefas(projetoId);
		recursos = getRecursos(projetoId);

		tarefa = getTarefaById(tarefaId);

		if (tarefa != null) {
			tarefas.remove(tarefa);

			tarefaspredecessoras.addAll(tarefa.getTarefaspredecessoras());

			for (Utilizacaorecurso ut : tarefa.getUtilizacaorecursos()) {
				recursosAlocados.add(ut.getRecurso());
			}

			if (tarefa.getTarefaPai() != null) {
				tarefapaiId = tarefa.getTarefaPai().getId();
			}
		} else {
			tarefa = new Tarefa();
			tarefa.setProjeto(getProjetoById(projetoId));
			tarefa.setInicio(tarefa.getProjeto().getInicio());
			Calendar c = new GregorianCalendar();
			c.setTime(tarefa.getProjeto().getInicio());
			c.add(Calendar.DATE, 1);
			tarefa.setFim(c.getTime());
		}

		if (tarefas.size() > 0) {
			tarefas.remove(0);
		}
		for (Tarefa t : tarefas) {
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

		if (tarefapaiId != null) {
			tarefa.setTarefaPai(getTarefaById(tarefapaiId));
		} else {
			tarefa.setTarefaPai(new Tarefa());
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

	public String getTarefas(String projeto_id, String tipo) {
		em.clear();
		em.flush();
		String ret = "Error";
		try {
			List<Tarefa> tarefas = null;
			if (tipo.equals("visao_geral")) {
				tarefas = getMacroTarefas(Long.valueOf(projeto_id));
			} else if (tipo.equals("visao_detalhada")) {
				tarefas = getTarefas(Long.valueOf(projeto_id));
			}
			List<Task> tasks = new ArrayList<Task>();
			log.info("carregando gantt: \n" + tarefas);
			for (Tarefa t : tarefas) {
				if (tarefas.size() <= 1) {
					break;
				}

				List<Tarefa> subtarefas = getSubtarefas(t.getId());
				List<Tarefa> predecessoras = getPredecessores(t.getId());
				List<Utilizacaorecurso> recursos = getUtilizacaorecursos(t
						.getId());

				Task task = new Task("[" + t.getEap() + "]-" + t.getNome(),
						new TimePeriod(t.getInicio(), t.getFim()));
				task.setId(t.getId());
				task.setMilestone(t.getMilestone());
				task.setPercentComplete(t.getPorcentcomp());
				if (tipo.equals("visao_detalhada")) {
					for (Tarefa st : subtarefas) {
						task.addSubtask(new Task("[" + st.getEap() + "]-"
								+ st.getNome(), st.getInicio(), st.getFim()));
					}
				}
				if (tarefas.indexOf(t) == 0) {
					task.setDescription(t.getNome());
					task.setId(new Long(-1));
					for (Tarefa st : tarefas) {
						if (st.equals(t)) {
							continue;
						}
						task.addSubtask(new Task("[" + t.getEap() + "]-"
								+ st.getNome(), st.getInicio(), st.getFim()));
					}
				}

				for (Tarefa tp : predecessoras) {
					task.addPredecessor(new Task("[" + tp.getEap() + "]-"
							+ tp.getNome(), tp.getInicio(), tp.getFim()));
				}
				for (Utilizacaorecurso u : recursos) {
					task.addRecurso(u.getRecurso().getNome());
				}
				tasks.add(task);
			}

			// ganbiarra para fazer o gráfico ficar bonito quando tem pouca
			// tarefa =)
			System.out.println("tarefas do grafico: " + tasks.size());
			if (tasks.size() > 0) {
				String ghostTask = " ";
				int index = 0;
				if (tasks.size() > 1) {
					index = 1;
				}
				System.out.println("peguei o indice: " + index);
				Date init = tasks.get(index).getDuration().getStart();
				System.out.println("A data inicial eh: " + init);
				if (tipo.equals("visao_detalhada")) {
					for (int i = tasks.size(); i <= 12; i++) {
						Task ta = new Task(ghostTask, init, init);
						ta.addSubtask(new Task("_", init, init));
						ta.setId(new Long(-1));
						tasks.add(ta);
						ghostTask += " ";
					}
				} else {
					for (int i = tasks.size(); i <= 7; i++) {
						Task ta = new Task(ghostTask, init, init);
						ta.addSubtask(new Task("_", init, init));
						ta.setId(new Long(-1));
						tasks.add(ta);
						ghostTask += " ";
					}
				}
			}
			ret = new Gson().toJson(tasks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
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

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public List<SelectItem> getTarefaspai() {
		return tarefaspai;
	}

	public void setTarefaspai(List<SelectItem> tarefaspai) {
		this.tarefaspai = tarefaspai;
	}

	public List<Recurso> getRecursosAlocados() {
		return recursosAlocados;
	}

	public void setRecursosAlocados(List<Recurso> recursosAlocados) {
		this.recursosAlocados = recursosAlocados;
	}

	public Long getTarefapaiId() {
		return tarefapaiId;
	}

	public void setTarefapaiId(Long tarefapaiId) {
		this.tarefapaiId = tarefapaiId;
	}

	public List<Tarefa> getTarefaspredecessoras() {
		return tarefaspredecessoras;
	}

	public void setTarefaspredecessoras(List<Tarefa> tarefaspredecessoras) {
		this.tarefaspredecessoras = tarefaspredecessoras;
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

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public List<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	public List<Tarefa> getMacroTarefas(Long projetoId) {
		Query q = em
				.createNativeQuery(
						"select * from tarefa where tarefapaiid is null and projetoid = ?1 order by eap",
						Tarefa.class);
		q.setParameter(1, projetoId);
		return q.getResultList();
	}

	public List<Tarefa> getTarefas(Long projetoId) {
		Query q = em.createNativeQuery(
				"select * from tarefa where projetoid = ?1 order by eap",
				Tarefa.class);
		q.setParameter(1, projetoId);
		return q.getResultList();
	}

	public List<Utilizacaorecurso> getUtilizacaorecursos(Long tarefaId) {
		Query q = em.createNativeQuery(
				"select * from utilizacaorecurso where tarefaid = ?1",
				Utilizacaorecurso.class);
		q.setParameter(1, tarefaId);
		return q.getResultList();
	}

	public List<Tarefa> getSubtarefas(Long tarefaId) {
		Query q = em.createNativeQuery(
				"select * from tarefa where tarefapaiid = ?", Tarefa.class);
		q.setParameter(1, tarefaId);
		return q.getResultList();
	}

	public List<Recurso> getRecursos(Long projetoId) {
		Query q = em.createNativeQuery(
				"select * from recurso where projetoid = ?1", Recurso.class);
		q.setParameter(1, projetoId);
		return q.getResultList();
	}

	public List<Tarefa> getPredecessores(Long tarefaId) {
		Query q = em
				.createNativeQuery(
						"select t.* from  tarefapredecessora p join tarefa t on p.tarefapredecessoraid = t.id where p.tarefaid = ?",
						Tarefa.class);
		q.setParameter(1, tarefaId);
		return q.getResultList();
	}

	public Tarefa getTarefaById(Long tarefaId) {
		Query q = em.createNativeQuery("select * from tarefa where id = ?",
				Tarefa.class);
		q.setParameter(1, tarefaId);
		List<Tarefa> result = q.getResultList();
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	public Projeto getProjetoById(Long projetoId) {
		Query q = em.createNativeQuery("select * from projeto where id = ?",
				Projeto.class);
		q.setParameter(1, projetoId);
		List<Projeto> result = q.getResultList();
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	private class Task {

		private Long id;

		/** The task description. */
		private String description;

		/** The time period for the task (estimated or actual). */
		private TimePeriod duration;

		/** The percent complete (<code>null</code> is permitted). */
		private Double percentComplete;

		/** Storage for the sub-tasks (if any). */
		private List subtasks;

		/** Storage for the predecessors tasks (if any). */
		private List predecessores;

		/** Storage for the task's resources (if any). */
		private List recursos;

		private boolean milestone;

		/**
		 * Creates a new task.
		 * 
		 * @param description
		 *            the task description (<code>null</code> not permitted).
		 * @param duration
		 *            the task duration (<code>null</code> permitted).
		 */
		public Task(String description, TimePeriod duration) {
			if (description == null) {
				throw new IllegalArgumentException(
						"Null 'description' argument.");
			}
			this.description = description;
			this.duration = duration;
			this.percentComplete = null;
			this.subtasks = new java.util.ArrayList();
			this.predecessores = new java.util.ArrayList();
			this.recursos = new java.util.ArrayList();
			this.id = new Long(0);
		}

		/**
		 * Creates a new task.
		 * 
		 * @param description
		 *            the task description (<code>null</code> not permitted).
		 * @param start
		 *            the start date (<code>null</code> not permitted).
		 * @param end
		 *            the end date (<code>null</code> not permitted).
		 */
		public Task(String description, Date start, Date end) {
			this(description, new TimePeriod(start, end));
		}

		/**
		 * Returns the task description.
		 * 
		 * @return The task description (never <code>null</code>).
		 */
		public String getDescription() {
			return this.description;
		}

		/**
		 * Sets the task description.
		 * 
		 * @param description
		 *            the description (<code>null</code> not permitted).
		 */
		public void setDescription(String description) {
			if (description == null) {
				throw new IllegalArgumentException(
						"Null 'description' argument.");
			}
			this.description = description;
		}

		/**
		 * Returns the duration (actual or estimated) of the task.
		 * 
		 * @return The task duration (possibly <code>null</code>).
		 */
		public TimePeriod getDuration() {
			return this.duration;
		}

		/**
		 * Sets the task duration (actual or estimated).
		 * 
		 * @param duration
		 *            the duration (<code>null</code> permitted).
		 */
		public void setDuration(TimePeriod duration) {
			this.duration = duration;
		}

		/**
		 * Returns the percentage complete for this task.
		 * 
		 * @return The percentage complete (possibly <code>null</code>).
		 */
		public Double getPercentComplete() {
			return this.percentComplete;
		}

		/**
		 * Sets the percentage complete for the task.
		 * 
		 * @param percent
		 *            the percentage (<code>null</code> permitted).
		 */
		public void setPercentComplete(Double percent) {
			this.percentComplete = percent;
		}

		/**
		 * Sets the percentage complete for the task.
		 * 
		 * @param percent
		 *            the percentage.
		 */
		public void setPercentComplete(double percent) {
			setPercentComplete(new Double(percent));
		}

		/**
		 * Adds a sub-task to the task.
		 * 
		 * @param subtask
		 *            the subtask (<code>null</code> not permitted).
		 */
		public void addSubtask(Task subtask) {
			if (subtask == null) {
				throw new IllegalArgumentException("Null 'subtask' argument.");
			}
			if (this.equals(subtask)) {
				throw new IllegalArgumentException(
						"'subtask' argument cannot be itself.");
			}
			if (!this.subtasks.contains(subtask)) {
				this.subtasks.add(subtask);
				resetDuration();
				recalculatePercent();
			}
		}

		/**
		 * Removes a sub-task from the task.
		 * 
		 * @param subtask
		 *            the subtask.
		 */
		public void removeSubtask(Task subtask) {
			this.subtasks.remove(subtask);
		}

		/**
		 * Returns the sub-task count.
		 * 
		 * @return The sub-task count.
		 */
		public int getSubtaskCount() {
			return this.subtasks.size();
		}

		/**
		 * Returns a sub-task.
		 * 
		 * @param index
		 *            the index.
		 * 
		 * @return The sub-task.
		 */
		public Task getSubtask(int index) {
			return (Task) this.subtasks.get(index);
		}

		public void addPredecessor(Task predecessor) {
			if (predecessor == null) {
				throw new IllegalArgumentException(
						"Null 'predecessor' argument.");
			}
			if (this.equals(predecessor)) {
				throw new IllegalArgumentException(
						"'predecessor' argument cannot be itself.");
			}
			if (!predecessores.contains(predecessor)) {
				this.predecessores.add(predecessor);
			}
		}

		public void removePredecessor(Task predecessor) {
			this.predecessores.remove(predecessor);
		}

		public int getPredecessorCount() {
			return this.predecessores.size();
		}

		public Task getPredecessor(int index) {
			return (Task) this.predecessores.get(index);
		}

		public boolean equals(Object object) {
			if (object == this) {
				return true;
			}
			if (!(object instanceof Task)) {
				return false;
			}
			Task that = (Task) object;
			if (!ObjectUtilities.equal(this.description, that.description)) {
				return false;
			}
			if (!ObjectUtilities.equal(this.duration, that.duration)) {
				return false;
			}
			if (!ObjectUtilities.equal(this.percentComplete,
					that.percentComplete)) {
				return false;
			}
			if (!ObjectUtilities.equal(this.subtasks, that.subtasks)) {
				return false;
			}
			if (!ObjectUtilities.equal(this.predecessores, that.predecessores)) {
				return false;
			}
			if (!ObjectUtilities.equal(this.recursos, that.recursos)) {
				return false;
			}
			if (!ObjectUtilities.equal(this.milestone, that.milestone)) {
				return false;
			}
			return true;
		}

		public String toString() {
			return description + ": " + duration.start + " - " + duration.end;
		}

		private void recalculatePercent() {
			Double percent = 0.0;
			for (Object o : subtasks) {
				Task t = (Task) o;
				if (t.getPercentComplete() != null) {
					percent += t.getPercentComplete();
				}
			}
			this.setPercentComplete(percent / subtasks.size());
		}

		private void resetDuration() {
			Date minDate = new Date(Long.MAX_VALUE);
			Date maxDate = new Date(0);
			for (Object o : subtasks) {
				Task t = (Task) o;
				if (t.getDuration().getStart().before(minDate)) {
					minDate = t.getDuration().getStart();
				}
				if (t.getDuration().getEnd().after(maxDate)) {
					maxDate = t.getDuration().getEnd();
				}
			}
			this.setDuration(new TimePeriod(minDate, maxDate));
		}

		public void addRecurso(String recurso) {
			if (recurso == null) {
				throw new IllegalArgumentException("Null 'recurso' argument.");
			}
			if (this.equals(recurso)) {
				throw new IllegalArgumentException(
						"'recurso' argument cannot be itself.");
			}
			if (!recursos.contains(recurso)) {
				this.recursos.add(recurso);
			}
		}

		public void removeRecurso(String recurso) {
			this.recursos.remove(recurso);
		}

		public int getRecursoCount() {
			return this.recursos.size();
		}

		public String getRecurso(int index) {
			return (String) this.recursos.get(index);
		}

		public boolean isMilestone() {
			return milestone;
		}

		public void setMilestone(boolean milestone) {
			this.milestone = milestone;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

	}

	private class TimePeriod implements Comparable {

		/** The start date/time. */
		private long start;

		/** The end date/time. */
		private long end;

		/**
		 * Creates a new time allocation.
		 * 
		 * @param start
		 *            the start date/time in milliseconds.
		 * @param end
		 *            the end date/time in milliseconds.
		 */
		public TimePeriod(long start, long end) {
			if (start > end) {
				throw new IllegalArgumentException("Requires start <= end.");
			}
			this.start = start;
			this.end = end;
		}

		/**
		 * Creates a new time allocation.
		 * 
		 * @param start
		 *            the start date/time (<code>null</code> not permitted).
		 * @param end
		 *            the end date/time (<code>null</code> not permitted).
		 */
		public TimePeriod(Date start, Date end) {
			this(start.getTime(), end.getTime());
		}

		/**
		 * Returns the start date/time.
		 * 
		 * @return The start date/time (never <code>null</code>).
		 */
		public Date getStart() {
			return new Date(this.start);
		}

		/**
		 * Returns the start date/time in milliseconds.
		 * 
		 * @return The start.
		 * 
		 * @since 1.0.10.
		 */
		public long getStartMillis() {
			return this.start;
		}

		/**
		 * Returns the end date/time.
		 * 
		 * @return The end date/time (never <code>null</code>).
		 */
		public Date getEnd() {
			return new Date(this.end);
		}

		/**
		 * Returns the end date/time in milliseconds.
		 * 
		 * @return The end.
		 * 
		 * @since 1.0.10.
		 */
		public long getEndMillis() {
			return this.end;
		}

		/**
		 * Tests this time period instance for equality with an arbitrary
		 * object. The object is considered equal if it is an instance of
		 * {@link TimePeriod} and it has the same start and end dates.
		 * 
		 * @param obj
		 *            the other object (<code>null</code> permitted).
		 * 
		 * @return A boolean.
		 */
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof TimePeriod)) {
				return false;
			}
			TimePeriod that = (TimePeriod) obj;
			if (!this.getStart().equals(that.getStart())) {
				return false;
			}
			if (!this.getEnd().equals(that.getEnd())) {
				return false;
			}
			return true;
		}

		/**
		 * Returns an integer that indicates the relative ordering of two time
		 * periods.
		 * 
		 * @param obj
		 *            the object (<code>null</code> not permitted).
		 * 
		 * @return An integer.
		 * 
		 * @throws ClassCastException
		 *             if <code>obj</code> is not an instance of
		 *             {@link TimePeriod}.
		 */
		public int compareTo(Object obj) {
			TimePeriod that = (TimePeriod) obj;
			long t0 = getStart().getTime();
			long t1 = getEnd().getTime();
			long m0 = t0 + (t1 - t0) / 2L;
			long t2 = that.getStart().getTime();
			long t3 = that.getEnd().getTime();
			long m1 = t2 + (t3 - t2) / 2L;
			if (m0 < m1) {
				return -1;
			} else if (m0 > m1) {
				return 1;
			} else {
				if (t0 < t2) {
					return -1;
				} else if (t0 > t2) {
					return 1;
				} else {
					if (t1 < t3) {
						return -1;
					} else if (t1 > t3) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		}

		/**
		 * Returns a hash code for this object instance. The approach described
		 * by Joshua Bloch in "Effective Java" has been used here - see:
		 * <p>
		 * <code>http://developer.java.sun.com/
		 * developer/Books/effectivejava/Chapter3.pdf</code>
		 * 
		 * @return A hash code.
		 */
		public int hashCode() {
			int result = 17;
			result = 37 * result + (int) this.start;
			result = 37 * result + (int) this.end;
			return result;
		}
	}
}