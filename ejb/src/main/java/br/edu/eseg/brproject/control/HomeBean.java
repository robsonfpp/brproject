package br.edu.eseg.brproject.control;

import java.io.Serializable;
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
public class HomeBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
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

		Query q = projetoList.getEntityManager().createNativeQuery("select p.* from projeto p join statusprojeto s on s.id = p.statusprojetoid where (p.gerenteprojetoid= ?1 or exists (select * from stakeholder s where s.projetoid = p.id and s.usuarioid = ?1)) and p.statusprojetoid < 5", Projeto.class);
		q.setParameter(1, loggedUser.getId());
		return (List<Projeto>) q.getResultList();
	}

	public List<Solicitacaomudanca> getSolicitacoes() {
		log.info("Fazendo query de solicitacoes de mudancas em aberto para '#{loggedUser.nome}'");

		if (loggedUser.getGp()) {
			solicitacaomudancaList
					.setEjbql("select m from Solicitacaomudanca m join m.statusmudanca s join m.projeto p join p.usuario pu join p.statusprojeto sp");
			solicitacaomudancaList.setRestrictionExpressionStrings(Arrays
					.asList("s.id = #{1}", "pu.id = #{loggedUser.id}","sp.id < #{5}"));
		} else {
			solicitacaomudancaList
					.setEjbql("select m from Solicitacaomudanca m join m.statusmudanca s join m.usuario u join m.projeto p join p.statusprojeto sp");
			solicitacaomudancaList.setRestrictionExpressionStrings(Arrays
					.asList("s.id = #{1}", "u.id = #{loggedUser.id}","sp.id < #{5}"));
		}

		return solicitacaomudancaList.getResultList();
	}
	
	public List getAvaliacoes(){
		Query q = notastakeholderList.getEntityManager().createNativeQuery("select p.id,p.nome from stakeholder s1 join stakeholder s2 on s1.projetoid = s2.projetoid join usuario u1 on u1.id = s1.usuarioid join usuario u2 on u2.id = s2.usuarioid join projeto p on p.id = s2.projetoid left join notastakeholder ns on ns.stakeholderavaliadoid = s1.id and ns.stakeholderavaliadorid = s2.id where u1.nome <> u2.nome and ns.id is null and u2.id = ?1 and p.statusprojetoid < 5 group by p.id order by p.id");
		q.setParameter(1, loggedUser.getId());
		return q.getResultList();
	}
}
