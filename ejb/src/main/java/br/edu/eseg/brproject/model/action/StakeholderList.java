package br.edu.eseg.brproject.model.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;

import br.edu.eseg.brproject.model.Stakeholder;

@Name("stakeholderList")
public class StakeholderList extends EntityQuery<Stakeholder> {

	private static final String EJBQL = "select stakeholder from Stakeholder stakeholder";

	private static final String[] RESTRICTIONS = { "lower(stakeholder.papel) like lower(concat(#{stakeholderList.stakeholder.papel},'%'))", };

	private Stakeholder stakeholder = new Stakeholder();

	public StakeholderList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Stakeholder getStakeholder() {
		return stakeholder;
	}

	public List<SelectItem> getStakeholdersNaoAvaliados(Long projetoid,
			Long usuarioid) {
		List<SelectItem> result = new ArrayList<SelectItem>();
		StringBuilder sql = new StringBuilder();
		sql.append("select s1.id, u1.nome from stakeholder s1 join stakeholder s2 on s1.projetoid = s2.projetoid join usuario u1 on u1.id = s1.usuarioid join usuario u2 on u2.id = s2.usuarioid left join notastakeholder ns on ns.stakeholderavaliadoid = s1.id and ns.stakeholderavaliadorid = s2.id where u1.nome <> u2.nome and ns.id is null and u2.id = ?1 and s2.projetoid = ?2");
		Query q = getEntityManager().createNativeQuery(sql.toString());
		q.setParameter(1, usuarioid);
		q.setParameter(2, projetoid);
		List<Object[]> l = q.getResultList();
		for (Object[] s : l) {
			result.add(new SelectItem(s[0], s[1].toString()));
		}
		return result;
	}
	
	public List<Stakeholder> getStakeholdersPendentes(Long projetoid){
		StringBuilder sql = new StringBuilder();
		sql.append("select s.* from stakeholder s join usuario u on s.usuarioid = u.id left join notastakeholder ns on ns.stakeholderavaliadorid = s.id where s.projetoid = ?1 group by s.id having count(ns.stakeholderavaliadoid) < (select count(*)-1 from stakeholder where projetoid = ?1)");
		Query q = getEntityManager().createNativeQuery(sql.toString(),Stakeholder.class);
		q.setParameter(1, projetoid);
		return (List<Stakeholder>)q.getResultList();
	}
}
