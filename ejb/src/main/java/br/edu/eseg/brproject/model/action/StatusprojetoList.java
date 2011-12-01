package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("statusprojetoList")
public class StatusprojetoList extends EntityQuery<Statusprojeto> {

	private static final String EJBQL = "select statusprojeto from Statusprojeto statusprojeto";

	private static final String[] RESTRICTIONS = { "lower(statusprojeto.nome) like lower(concat(#{statusprojetoList.statusprojeto.nome},'%'))", };

	private Statusprojeto statusprojeto = new Statusprojeto();

	public StatusprojetoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Statusprojeto getStatusprojeto() {
		return statusprojeto;
	}
}
