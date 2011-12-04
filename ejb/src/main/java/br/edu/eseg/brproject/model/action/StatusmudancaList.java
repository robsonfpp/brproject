package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

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

	public List<SelectItem> getStatusmudancaSelectItem(){
		List<SelectItem> result = new ArrayList<SelectItem>();
		for(Statusmudanca s: getResultList()){
			if(s.getId()!=1){
				result.add(new SelectItem(s.getId(),s.getNome()));
			}
		}
		return result;
	}
	
	public Statusmudanca getStatusmudanca() {
		return statusmudanca;
	}
}
