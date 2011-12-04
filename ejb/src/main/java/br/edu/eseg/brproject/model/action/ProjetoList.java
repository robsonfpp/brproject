package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

@Name("projetoList")
public class ProjetoList extends EntityQuery<Projeto> {

	private static final String EJBQL = "select projeto from Projeto projeto";

	private static final String[] RESTRICTIONS = {
			"lower(projeto.cliente) like lower(concat(#{projetoList.projeto.cliente},'%'))",
			"lower(projeto.nome) like lower(concat(#{projetoList.projeto.nome},'%'))",
			"lower(projeto.usuario.nome) like lower(concat(#{projetoList.projeto.usuario.nome},'%'))"};

	private Projeto projeto = new Projeto();

	public ProjetoList() {
		projeto.setUsuario(new Usuario());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public List<SelectItem> getProjetoSelectItem(){
		List<SelectItem> result = new ArrayList<SelectItem>();
		projeto.setStatusprojeto(new Statusprojeto(new Long(5)));
		getRestrictionExpressionStrings().add("projeto.statusprojeto.id <> #{projetoList.projeto.statusprojeto.id}");
		//getRestrictionExpressionStrings().add("exists(select stakeholder from Stakeholder stakeholder where stakeholder.projeto = projeto and stakeholder.id = #{usuarioHome.usuarioId})");
		setMaxResults(1000);
		for(Projeto p: getResultList()){
			result.add(new SelectItem(p.getId(),p.getNome()));
		}
		return result;
	}
	
	public Projeto getProjeto() {
		return projeto;
	}
}
