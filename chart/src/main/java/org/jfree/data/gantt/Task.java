/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2008, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ---------
 * Task.java
 * ---------
 * (C) Copyright 2003-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 10-Jan-2003 : Version 1 (DG);
 * 16-Sep-2003 : Added percentage complete (DG);
 * 30-Jul-2004 : Added clone() and equals() methods and implemented
 *               Serializable (DG);
 *
 */

package org.jfree.data.gantt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriod;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;

/**
 * A simple representation of a task. The task has a description and a duration.
 * You can add sub-tasks to the task.
 */
public class Task implements Cloneable, PublicCloneable, Serializable {

	private Long id;
	
	/** For serialization. */
	private static final long serialVersionUID = 1094303785346988894L;

	/** The task description. */
	private String description;

	/** The time period for the task (estimated or actual). */
	private TimePeriod duration;

	/** The percent complete (<code>null</code> is permitted). */
	private Double percentComplete;

	/** Storage for the sub-tasks (if any). */
	private List<Task> subtasks;

	/** Storage for the predecessors tasks (if any). */
	private List<Task> predecessores;

	/** Storage for the task's resources (if any). */
	private List<String> recursos;
	
	private boolean milestone;

	public Task(){
		subtasks = new ArrayList<Task>();
		predecessores = new ArrayList<Task>();
		recursos = new ArrayList<String>();
	}
	
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
			throw new IllegalArgumentException("Null 'description' argument.");
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
		this(description, new SimpleTimePeriod(start, end));
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
			throw new IllegalArgumentException("Null 'description' argument.");
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
			throw new IllegalArgumentException("Null 'predecessor' argument.");
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

	/**
	 * Tests this object for equality with an arbitrary object.
	 * 
	 * @param object
	 *            the other object (<code>null</code> permitted).
	 * 
	 * @return A boolean.
	 */
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
		if (!ObjectUtilities.equal(this.percentComplete, that.percentComplete)) {
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

	/**
	 * Returns a clone of the task.
	 * 
	 * @return A clone.
	 * 
	 * @throws CloneNotSupportedException
	 *             never thrown by this class, but subclasses may not support
	 *             cloning.
	 */
	public Object clone() throws CloneNotSupportedException {
		Task clone = (Task) super.clone();
		return clone;
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
		this.setDuration(new SimpleTimePeriod(minDate, maxDate));
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

	public List getSubtasks() {
		return subtasks;
	}

	public void setSubtasks(List subtasks) {
		this.subtasks = subtasks;
	}

	public List getPredecessores() {
		return predecessores;
	}

	public void setPredecessores(List predecessores) {
		this.predecessores = predecessores;
	}

	public List getRecursos() {
		return recursos;
	}

	public void setRecursos(List recursos) {
		this.recursos = recursos;
	}	
	
	
	
}