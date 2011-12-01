package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("licaoList")
public class LicaoList extends EntityQuery<Licao> {

	private static final String EJBQL = "select licao from Licao licao";

	private static final String[] RESTRICTIONS = { "lower(licao.titulo) like lower(concat(#{licaoList.licao.titulo},'%'))", };

	private Licao licao = new Licao();

	public LicaoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Licao getLicao() {
		return licao;
	}
}
