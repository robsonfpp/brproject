package br.edu.eseg.brproject.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jfree.util.ObjectUtilities;

import br.edu.eseg.brproject.model.Projeto;
import br.edu.eseg.brproject.model.Tarefa;
import br.edu.eseg.brproject.model.Utilizacaorecurso;
import br.edu.eseg.brproject.model.action.TarefaList;

import com.google.gson.Gson;

@Name("gantt")
public class GanttBean {

	// @In(create = true)
	// GanttTx GantTxImpl;
	@In(create = true)
	TarefaList tarefaList;
	public String tarefaId;

	public String getTarefas(String projeto_id, String tipo) {

		String ret = "Error";
		try {
			List<Tarefa> tarefas = null;
			if (tipo.equals("visao_geral")) {
				tarefas = getMacroTarefas(Long.valueOf(projeto_id));
			} else if (tipo.equals("visao_detalhada")) {
				tarefas = getTarefas(Long.valueOf(projeto_id));
			}
			List<Task> tasks = new ArrayList<Task>();
			
			for (Tarefa t : tarefas) {
				if(tarefas.size()<=1){
					break;
				}
				Task task = new Task(t.getNome(), new TimePeriod(t.getInicio(),
						t.getFim()));
				task.setId(t.getId());
				task.setMilestone(t.getMilestone());
				task.setPercentComplete(t.getPorcentcomp());
				if (tipo.equals("visao_detalhada")) {
					for (Tarefa st : t.getSubtarefas()) {
						task.addSubtask(new Task(st.getNome(), st.getInicio(),
								st.getFim()));
					}
				} else if (tarefas.indexOf(t) == 0) {
					for (Tarefa st : t.getSubtarefas()) {
						task.addSubtask(new Task(st.getNome(), st.getInicio(),
								st.getFim()));
					}
				}

				for (Tarefa tp : t.getTarefaspredecessoras()) {
					task.addPredecessor(new Task(tp.getNome(), tp.getInicio(),
							tp.getFim()));
				}
				for (Utilizacaorecurso u : t.getUtilizacaorecursos()) {
					task.addRecurso(u.getRecurso().getNome());
				}
				tasks.add(task);
			}
			ret = new Gson().toJson(tasks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	private List<Tarefa> getMacroTarefas(Long projetoId) {
		Query q = tarefaList
				.getEntityManager()
				.createNativeQuery(
						"select t.* from tarefa t join projeto p on p.id = t.projetoid join subtarefa st on st.tarefaid = t.id or t.milestone where t.projetoid = ? group by t.id order by t.id",
						Tarefa.class);
		q.setParameter(1, projetoId);
		return q.getResultList();
	}

	private List<Tarefa> getTarefas(Long projetoId) {
		Query q = tarefaList
				.getEntityManager()
				.createNativeQuery(
						"select t.* from tarefa t where t.projetoid = ?1 order by t.id",
						Tarefa.class);
		q.setParameter(1, projetoId);
		return q.getResultList();
	}

	private Projeto getProjeto(Long projetoId) {
		Query q = tarefaList.getEntityManager().createNativeQuery(
				"select * from projeto where id = ?", Projeto.class);
		q.setParameter(1, projetoId);
		return (Projeto) q.getSingleResult();
	}

	public String getTarefaId() {
		return tarefaId;
	}

	public void setTarefaId(String tarefaId) {
		this.tarefaId = tarefaId;
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