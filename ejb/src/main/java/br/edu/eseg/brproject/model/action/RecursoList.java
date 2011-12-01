package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("recursoList")
public class RecursoList extends EntityQuery<Recurso> {

	private static final String EJBQL = "select recurso from Recurso recurso";

	private static final String[] RESTRICTIONS = { "lower(recurso.nome) like lower(concat(#{recursoList.recurso.nome},'%'))", };

	private Recurso recurso = new Recurso();

	public RecursoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Recurso getRecurso() {
		return recurso;
	}
}
