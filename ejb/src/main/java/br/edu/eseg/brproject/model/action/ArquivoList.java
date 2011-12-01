package br.edu.eseg.brproject.model.action;

import java.util.Arrays;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import br.edu.eseg.brproject.model.Arquivo;

@Name("arquivoList")
public class ArquivoList extends EntityQuery<Arquivo> {

	private static final String EJBQL = "select arquivo from Arquivo arquivo";

	private static final String[] RESTRICTIONS = {
			"lower(arquivo.tipo) like lower(concat(#{arquivoList.arquivo.tipo},'%'))",
			"lower(arquivo.nome) like lower(concat(#{arquivoList.arquivo.nome},'%'))",
			"lower(arquivo.etapa) like lower(concat(#{arquivoList.arquivo.etapa},'%'))"};

	private Arquivo arquivo = new Arquivo();

	public ArquivoList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Arquivo getArquivo() {
		return arquivo;
	}
	
}
