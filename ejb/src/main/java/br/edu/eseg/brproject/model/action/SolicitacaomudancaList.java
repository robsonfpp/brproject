package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

@Name("solicitacaomudancaList")
public class SolicitacaomudancaList extends EntityQuery<Solicitacaomudanca> {

	private static final String EJBQL = "select solicitacaomudanca from Solicitacaomudanca solicitacaomudanca";

	private static final String[] RESTRICTIONS = {
			"lower(solicitacaomudanca.titulo) like lower(concat(#{solicitacaomudancaList.solicitacaomudanca.titulo},'%'))",
			"lower(solicitacaomudanca.usuario.nome) like lower(concat(#{solicitacaomudancaList.solicitacaomudanca.usuario.nome},'%'))",
			"lower(solicitacaomudanca.projeto.nome) like lower(concat(#{solicitacaomudancaList.solicitacaomudanca.projeto.nome},'%'))",
			"lower(solicitacaomudanca.statusmudanca.nome) like lower(concat(#{solicitacaomudancaList.solicitacaomudanca.statusmudanca.nome},'%'))" };

	private Solicitacaomudanca solicitacaomudanca = new Solicitacaomudanca();

	public SolicitacaomudancaList() {
		solicitacaomudanca.setProjeto(new Projeto());
		solicitacaomudanca.setUsuario(new Usuario());
		solicitacaomudanca.setStatusmudanca(new Statusmudanca());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Solicitacaomudanca getSolicitacaomudanca() {
		return solicitacaomudanca;
	}
}
