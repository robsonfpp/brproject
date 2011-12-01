package br.edu.eseg.brproject.model.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import br.edu.eseg.brproject.model.Areaimpactada;

@Name("areaimpactadaList")
public class AreaimpactadaList extends EntityQuery<Areaimpactada> {

	private static final String EJBQL = "select areaimpactada from Areaimpactada areaimpactada";

	private static final String[] RESTRICTIONS = { "lower(areaimpactada.nome) like lower(concat(#{areaimpactadaList.areaimpactada.nome},'%'))", };

	private Areaimpactada areaimpactada = new Areaimpactada();

	public AreaimpactadaList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Areaimpactada getAreaimpactada() {
		return areaimpactada;
	}
	
	public List<SelectItem> getAreaimpactadaSelectItem(){
		List<SelectItem> result = new ArrayList<SelectItem>();
		for(Areaimpactada a: getResultList()){
			result.add(new SelectItem(a.getId(),a.getNome()));
		}
		return result;
	}
}
