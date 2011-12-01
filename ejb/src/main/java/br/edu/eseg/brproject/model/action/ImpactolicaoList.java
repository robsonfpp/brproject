package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

@Name("impactolicaoList")
public class ImpactolicaoList extends EntityQuery<Impactolicao> {

	private static final String EJBQL = "select impactolicao from Impactolicao impactolicao";

	private static final String[] RESTRICTIONS = { "lower(impactolicao.nome) like lower(concat(#{impactolicaoList.impactolicao.nome},'%'))", };

	private Impactolicao impactolicao = new Impactolicao();

	public ImpactolicaoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Impactolicao getImpactolicao() {
		return impactolicao;
	}
	public List<SelectItem> getImpactolicaoSelectItem(){
		List<SelectItem> result = new ArrayList<SelectItem>();
		for(Impactolicao a: getResultList()){
			result.add(new SelectItem(a.getId(),a.getNome()));
		}
		return result;
	}
}
