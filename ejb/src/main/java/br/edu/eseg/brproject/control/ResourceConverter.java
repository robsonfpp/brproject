package br.edu.eseg.brproject.control;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;

import br.edu.eseg.brproject.model.Recurso;
import br.edu.eseg.brproject.model.action.RecursoHome;

@Name("resourceConverter")
@Converter(forClass = Recurso.class, id = "br.edu.eseg.brproject.control.ResourceConverter")
@BypassInterceptors
public class ResourceConverter implements javax.faces.convert.Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(arg2==null || arg2.equals(""))return null;
		String[] props = arg2.split(";");
		Recurso r = new Recurso();
		r.setId(new Long(props[0]));
		r.setNome(props[1]);
		return r;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2==null)return "";
		Recurso r = (Recurso) arg2;
		return r.getId() + ";" + r.getNome();
	}

}
