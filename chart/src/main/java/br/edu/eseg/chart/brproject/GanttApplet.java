package br.edu.eseg.chart.brproject;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import javax.swing.JApplet;

import netscape.javascript.JSObject;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class GanttApplet extends JApplet implements MouseListener,
		MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private Image dbImage;
	private IntervalCategoryDataset dataset;
	private Graphics dbg;
	private JFreeChart chart;
	private JSObject jso;
	private int mX, mY; // Coordenadas do mouse
	private List tarefas;

	public void init() {
		try {
			int w = Integer.parseInt(getParameter("width"));
			int h = Integer.parseInt(getParameter("heigth"));
			setSize(new Dimension(w, h));
			addMouseListener(this);
			addMouseMotionListener(this);
			jso = JSObject.getWindow(this);
		} catch (Exception e) {
			e.printStackTrace();
			String stack = "";
			for (StackTraceElement se : e.getStackTrace()) {
				stack += se.toString() + "\n";
			}
			jso.call("alert", new Object[] { stack });
		}
	}

	@Override
	public void start() {
		
		jso.call("init"+getParameter("action"), new Object[] {});
	}

	public void loadChart(String jsonString) {
		try {
			JsonArray jsonList = new com.google.gson.JsonParser().parse(
					jsonString).getAsJsonArray();
			final TaskSeries series = new TaskSeries("Planejamento");
			for (JsonElement je : jsonList) {
				JsonObject object = je.getAsJsonObject();
				Task task = parseTask(object);
				for (JsonElement sub : object.get("subtasks").getAsJsonArray()) {
					task.addSubtask(parseTask(sub.getAsJsonObject()));
				}
				for (JsonElement pred : object.get("predecessores")
						.getAsJsonArray()) {
					task.addPredecessor(parseTask(pred.getAsJsonObject()));
				}
				for (JsonElement rec : object.get("recursos").getAsJsonArray()) {
					task.addRecurso(rec.getAsString());
				}
				series.add(task);
			}
			dataset = new TaskSeriesCollection();
			((TaskSeriesCollection) dataset).add(series);
			chart = ChartFactory.createGanttChart(null, // chart
					"Tarefas", // domain axis label
					"Data", // range axis label
					dataset, // data
					false, true, true); // include legend
			repaint();
		} catch (Exception e) {
			e.printStackTrace();
			String stack = "";
			for (StackTraceElement se : e.getStackTrace()) {
				stack += se.toString() + "\n";
			}
			jso.call("alert", new Object[] { stack });
		}
	}

	private Task parseTask(JsonObject object) {
		Task task = new Task();
		task.setId(object.get("id").getAsLong());
		task.setDescription(object.get("description").getAsString());
		JsonObject durationObj = object.get("duration").getAsJsonObject();
		task.setDuration(new SimpleTimePeriod(new Date(durationObj.get("start")
				.getAsLong()), new Date(durationObj.get("end").getAsLong())));
		JsonElement percentComplete = object.get("percentComplete");
		task.setPercentComplete(percentComplete != null ? percentComplete
				.getAsDouble() : null);
		task.setMilestone(object.get("milestone").getAsBoolean());
		return task;
	}

	public void paint(Graphics g) {
		if (dbImage == null) {
			dbImage = createImage(getBounds().getSize().width, getBounds()
					.getSize().height);
			dbg = dbImage.getGraphics();
		}
		dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
		dbg.setColor(getForeground());
		chart.draw((Graphics2D) dbg, getBounds());
		setSize(new Dimension(getWidth(), getHeight()));
		g.drawImage(dbImage, 0, 0, this);
		tarefas = chart.getCategoryPlot().getCategoriesForAxis(
				chart.getCategoryPlot().getDomainAxis());
	}

	@Override
	public void repaint() {
		paint(getGraphics());
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public void mouseEntered(MouseEvent e) {
		// Chamado quando o ponteiro entra na area do applet.
	}

	public void mouseExited(MouseEvent e) {
		// Chamado quando o ponteiro sai da area do applet.
	}

	public void mouseClicked(MouseEvent e) {
		// Chamado quando o usuario clica e solta o botao do mouse sem move-lo.
		mX = e.getX();
		mY = e.getY();

		Rectangle2D dataarea = chart.getCategoryPlot().getDataArea();
		double x1 = dataarea.getMinX();
		double x0 = x1 - x1 * .8;

		if (x0 <= mX && mX <= x1) {

			Task task = buscaTarefa(tarefas, mY);
			if (task != null) {
				jso.call("getTask", new Object[] { task.getId() });
			}
		}
		repaint();
		e.consume();
	}

	public Task buscaTarefa(List tarefas, int y) {
		int esq = 0;
		int dir = tarefas.size() - 1;
		int meio;

		while (esq <= dir) {
			meio = esq + ((dir - esq) / 2);

			double y0 = chart
					.getCategoryPlot()
					.getDomainAxis()
					.getCategoryStart(meio, tarefas.size(),
							chart.getCategoryPlot().getDataArea(),
							chart.getCategoryPlot().getDomainAxisEdge());
			double y1 = chart
					.getCategoryPlot()
					.getDomainAxis()
					.getCategoryEnd(meio, tarefas.size(),
							chart.getCategoryPlot().getDataArea(),
							chart.getCategoryPlot().getDomainAxisEdge());

			if (y < y0) {
				dir = meio - 1;
			} else if (y > y1) {
				esq = meio + 1;
			} else {
				Comparable t = (Comparable) tarefas.get(meio);
				Task task = dataset.getTask(0, t);
				return task;
			}
		}
		return null;
	}

	public void mousePressed(MouseEvent e) {
		// Chamado quando um botao eh pressionado

	}

	public void mouseReleased(MouseEvent e) {
		// Chamado depois que um botao eh solto
	}

	public void mouseMoved(MouseEvent e) {
		// Chamado durante o movimento do mouse sem botoes pressionados
		mX = e.getX();
		mY = e.getY();
		double x1 = chart.getCategoryPlot().getDataArea().getMinX();
		double x0 = x1 - x1 * .8;
		if (x0 <= mX && mX <= x1) {
			if (buscaTarefa(tarefas, mY) != null)
				setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		showStatus("X=" + mX + " Y=" + mY);
		repaint();
		e.consume();
	}

	public void mouseDragged(MouseEvent e) {
		// Chamado quando o mouse eh movido com botoes pressionados
	}

}
