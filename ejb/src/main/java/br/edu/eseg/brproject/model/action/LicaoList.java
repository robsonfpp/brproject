package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("licaoList")
public class LicaoList extends EntityQuery<Licao> {

	private static final String EJBQL = "select licao from Licao licao";

	private static final String[] RESTRICTIONS = { "lower(licao.titulo) like lower(concat(#{licaoList.licao.titulo},'%'))",
		"lower(licao.descricao) like lower(concat(#{licaoList.licao.descricao},'%'))",
		"lower(licao.descricao) like lower(concat(#{licaoList.licao.projeto.nome},'%'))",
		"licao.areaimpactada.id = #{licaoList.licao.areaimpactada.id}",
		"licao.impactolicao.id = #{licaoList.licao.impactolicao.id}",
		"licao.prioridadelicao.id = #{licaoList.licao.prioridadelicao.id}",
		"licao.projeto.id = #{licaoList.licao.projeto.id}"
		};

	private Licao licao = new Licao();

	public LicaoList() {
		licao.setProjeto(new Projeto());
		licao.setAreaimpactada(new Areaimpactada());
		licao.setImpactolicao(new Impactolicao());
		licao.setPrioridadelicao(new Prioridadelicao());
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Licao getLicao() {
		return licao;
	}
}
