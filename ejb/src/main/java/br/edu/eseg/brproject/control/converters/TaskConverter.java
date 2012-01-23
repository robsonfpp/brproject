package br.edu.eseg.brproject.control.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;

import br.edu.eseg.brproject.model.Tarefa;
import br.edu.eseg.brproject.model.action.TarefaHome;

@Name("taskConverter")
@Converter(forClass = Tarefa.class, id = "br.edu.eseg.brproject.control.TaskConverter")
@BypassInterceptors
public class TaskConverter implements javax.faces.convert.Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(arg2==null || arg2.equals(""))return null;
		String[] props = arg2.split(";");
		Tarefa t = new Tarefa();
		t.setId(new Long(props[0]));
		t.setNome(props[1]);
		t.setEap(props[2]);
		return t;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2==null)return "";
		Tarefa t = (Tarefa) arg2;
		return t.getId() + ";" + t.getNome() + ";" + t.getEap();
	}

}
