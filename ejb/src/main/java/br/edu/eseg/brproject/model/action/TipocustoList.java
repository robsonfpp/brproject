package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("tipocustoList")
public class TipocustoList extends EntityQuery<Tipocusto> {

	private static final String EJBQL = "select tipocusto from Tipocusto tipocusto";

	private static final String[] RESTRICTIONS = { "lower(tipocusto.nome) like lower(concat(#{tipocustoList.tipocusto.nome},'%'))", };

	private Tipocusto tipocusto = new Tipocusto();

	public TipocustoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Tipocusto getTipocusto() {
		return tipocusto;
	}
}
