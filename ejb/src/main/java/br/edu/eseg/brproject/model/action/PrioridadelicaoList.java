package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

@Name("prioridadelicaoList")
public class PrioridadelicaoList extends EntityQuery<Prioridadelicao> {

	private static final String EJBQL = "select prioridadelicao from Prioridadelicao prioridadelicao";

	private static final String[] RESTRICTIONS = { "lower(prioridadelicao.nome) like lower(concat(#{prioridadelicaoList.prioridadelicao.nome},'%'))", };

	private Prioridadelicao prioridadelicao = new Prioridadelicao();

	public PrioridadelicaoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public List<SelectItem> getPrioridadelicaoSelectItem(){
		List<SelectItem> result = new ArrayList<SelectItem>();
		for(Prioridadelicao a: getResultList()){
			result.add(new SelectItem(a.getId(),a.getNome()));
		}
		return result;
	}


	public Prioridadelicao getPrioridadelicao() {
		return prioridadelicao;
	}
}
