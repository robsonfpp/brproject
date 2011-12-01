package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("tiporecursoList")
public class TiporecursoList extends EntityQuery<Tiporecurso> {

	private static final String EJBQL = "select tiporecurso from Tiporecurso tiporecurso";

	private static final String[] RESTRICTIONS = { "lower(tiporecurso.nome) like lower(concat(#{tiporecursoList.tiporecurso.nome},'%'))", };

	private Tiporecurso tiporecurso = new Tiporecurso();

	public TiporecursoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Tiporecurso getTiporecurso() {
		return tiporecurso;
	}
}
