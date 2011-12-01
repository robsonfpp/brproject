package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("statusmudancaList")
public class StatusmudancaList extends EntityQuery<Statusmudanca> {

	private static final String EJBQL = "select statusmudanca from Statusmudanca statusmudanca";

	private static final String[] RESTRICTIONS = { "lower(statusmudanca.nome) like lower(concat(#{statusmudancaList.statusmudanca.nome},'%'))", };

	private Statusmudanca statusmudanca = new Statusmudanca();

	public StatusmudancaList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Statusmudanca getStatusmudanca() {
		return statusmudanca;
	}
}
