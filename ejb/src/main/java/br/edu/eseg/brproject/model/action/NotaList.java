package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

@Name("notaList")
public class NotaList extends EntityQuery<Nota> {

	private static final String EJBQL = "select nota from Nota nota";

	private static final String[] RESTRICTIONS = { "lower(nota.nome) like lower(concat(#{notaList.nota.nome},'%'))", };

	private Nota nota = new Nota();

	public NotaList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
		setOrderColumn("nota.valor");
		setOrderDirection("desc");
	}

	public Nota getNota() {
		return nota;
	}
	
	public List<SelectItem> getNotaSelectItem(){
		List<SelectItem> result = new ArrayList<SelectItem>();
		for(Nota a: getResultList()){
			result.add(new SelectItem(a.getId(),a.getNome()));
		}
		return result;
	}

}
