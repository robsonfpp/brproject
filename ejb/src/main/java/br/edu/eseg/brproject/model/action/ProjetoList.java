package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("projetoList")
public class ProjetoList extends EntityQuery<Projeto> {

	private static final String EJBQL = "select projeto from Projeto projeto";

	private static final String[] RESTRICTIONS = {
			"lower(projeto.cliente) like lower(concat(#{projetoList.projeto.cliente},'%'))",
			"lower(projeto.nome) like lower(concat(#{projetoList.projeto.nome},'%'))", };

	private Projeto projeto = new Projeto();

	public ProjetoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Projeto getProjeto() {
		return projeto;
	}
}
