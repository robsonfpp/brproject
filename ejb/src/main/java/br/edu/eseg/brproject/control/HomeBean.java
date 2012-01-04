package br.edu.eseg.brproject.control;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import br.edu.eseg.brproject.model.Projeto;
import br.edu.eseg.brproject.model.Solicitacaomudanca;
import br.edu.eseg.brproject.model.Usuario;
import br.edu.eseg.brproject.model.action.NotastakeholderList;
import br.edu.eseg.brproject.model.action.ProjetoList;
import br.edu.eseg.brproject.model.action.SolicitacaomudancaList;

@Name("home")
public class HomeBean {
	@Logger
	Log log;
	@In(create = true)
	ProjetoList projetoList;
	@In(create = true)
	SolicitacaomudancaList solicitacaomudancaList;
	@In
	Usuario loggedUser;
	@In(create = true)
	NotastakeholderList notastakeholderList;

	public List<Projeto> getProjetos() {
		log.info("Fazendo query de projetos em aberto");

		if (loggedUser.getGp()) {
			projetoList
					.setEjbql("select p from Projeto p join p.usuario u join p.statusprojeto s");
			projetoList.setRestrictionExpressionStrings(Arrays.asList(
					"s.id <> #{5}", "u.id = #{loggedUser.id}"));
		} else {
			projetoList
					.setEjbql("select p from Projeto p join p.stakeholders s join s.usuario u");
			projetoList.setRestrictionExpressionStrings(Arrays.asList(
					"s.id <> #{5}", "u.id = #{loggedUser.id}"));
		}
		return projetoList.getResultList();
	}

	public List<Solicitacaomudanca> getSolicitacoes() {
		log.info("Fazendo query de solicitacoes de mudancas em aberto para '#{loggedUser.nome}'");

		if (loggedUser.getGp()) {
			solicitacaomudancaList
					.setEjbql("select m from Solicitacaomudanca m join m.statusmudanca s join m.projeto p join p.usuario pu");
			solicitacaomudancaList.setRestrictionExpressionStrings(Arrays
					.asList("s.id = #{1}", "pu.id = #{loggedUser.id}"));
		} else {
			solicitacaomudancaList
					.setEjbql("select m from Solicitacaomudanca m join m.statusmudanca s join m.usuario u join m.projeto p");
			solicitacaomudancaList.setRestrictionExpressionStrings(Arrays
					.asList("s.id = #{1}", "u.id = #{loggedUser.id}"));
		}

		return solicitacaomudancaList.getResultList();
	}
	
	public List getAvaliacoes(){
		Query q = notastakeholderList.getEntityManager().createNativeQuery("select p.id, p.nome from stakeholder s join (select * from stakeholder where usuarioid = ?1) as s2 on s.projetoid = s2.projetoid join projeto p on p.id = s.projetoid left join notastakeholder ns on ns.stakeholderavaliadoid = s.id where ns.id is null and s.usuarioid <> ?1 group by p.id");
		q.setParameter(1, loggedUser.getId());
		return q.getResultList();
	}
}
