package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("utilizacaorecursoList")
public class UtilizacaorecursoList extends EntityQuery<Utilizacaorecurso> {

	private static final String EJBQL = "select utilizacaorecurso from Utilizacaorecurso utilizacaorecurso";

	private static final String[] RESTRICTIONS = {};

	private Utilizacaorecurso utilizacaorecurso;

	public UtilizacaorecursoList() {
		utilizacaorecurso = new Utilizacaorecurso();
		utilizacaorecurso.setId(new UtilizacaorecursoId());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Utilizacaorecurso getUtilizacaorecurso() {
		return utilizacaorecurso;
	}
}
