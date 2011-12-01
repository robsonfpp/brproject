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
		sql.append("select s.id,u.nome from stakeholder s left join (	select n.* from notastakeholder n join stakeholder st on st.id = n.stakeholderavaliadorid where st.usuarioid = ?1) as sh on sh.stakeholderavaliadoid = s.id left join usuario u on u.id = s.usuarioid where s.usuarioid <> ?2 and sh.stakeholderavaliadoid is null and s.projetoid = ?3");
		Query q = getEntityManager().createNativeQuery(sql.toString());
		q.setParameter(1, usuarioid);
		q.setParameter(2, usuarioid);
		q.setParameter(3, projetoid);
		List<Object[]> l = q.getResultList();
		for (Object[] s : l) {
			result.add(new SelectItem(s[0], s[1].toString()));
		}
		return result;
	}
}
