package br.edu.eseg.brproject.control.transactions;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;

import br.edu.eseg.brproject.model.Projeto;
import br.edu.eseg.brproject.model.Stakeholder;

@Stateless
@Name("projetoTx")
public class ProjetoTxImpl implements ProjetoTx {

	@PersistenceContext
	EntityManager em;

	@Override
	public Long createProjeto(Projeto p) {
		StringBuilder sql = new StringBuilder();

		sql.append("insert into projeto(").append("statusprojetoid,")
				.append("gerenteprojetoid,nome,cliente,")
				.append("resumo,justificativa,necessidades,")
				.append("produto,premissas,restricoes,")
				.append("responsabilidadesgp,datacriacao,inicio,")
				.append("fimprevisto,orcamento)")
				.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		Query q = em.createNativeQuery(sql.toString());
		q.setParameter(1, p.getStatusprojeto().getId());
		q.setParameter(2, p.getUsuario().getId());
		q.setParameter(3, p.getNome());
		q.setParameter(4, p.getCliente());
		q.setParameter(5, p.getResumo());
		q.setParameter(6, p.getJustificativa());
		q.setParameter(7, p.getNecessidades());
		q.setParameter(8, p.getProduto());
		q.setParameter(9, p.getPremissas());
		q.setParameter(10, p.getRestricoes());
		q.setParameter(11, p.getResponsabilidadesgp());
		q.setParameter(12, p.getDatacriacao());
		q.setParameter(13, p.getInicio());
		q.setParameter(14, p.getFimprevisto());
		q.setParameter(15, p.getOrcamento());
		q.executeUpdate();

		sql = new StringBuilder();
		sql.append("select last_insert_id() from dual");
		q = em.createNativeQuery(sql.toString());
		Object r = q.getSingleResult();
		Integer projetoId = Integer.parseInt(r.toString());

		for (Stakeholder s : p.getStakeholders()) {
			sql = new StringBuilder();
			sql.append("insert into stakeholder")
					.append("(projetoid,usuarioid,papel)")
					.append("values(?,?,?)");
			q = em.createNativeQuery(sql.toString());
			q.setParameter(1, projetoId);
			q.setParameter(2, s.getUsuario().getId());
			q.setParameter(3, s.getPapel());
			q.executeUpdate();
		}
		return new Long(projetoId);
	}

	@Override
	public void updateProjeto(Projeto p) {
		StringBuilder sql = new StringBuilder();
		sql.append("update projeto set ").append("statusprojetoid = ?, ")
				.append("gerenteprojetoid = ?, ").append("nome = ?, ")
				.append("cliente= ?, ").append("resumo= ?, ")
				.append("justificativa= ?, ").append("necessidades= ?, ")
				.append("produto= ?, ").append("premissas= ?, ")
				.append("restricoes= ?, ").append("responsabilidadesgp = ?, ")
				.append("datacriacao= ?, ").append("inicio= ?, ")
				.append("fimprevisto= ?, ").append("orcamento= ? ")
				.append("where id = ?");
		Query q = em.createNativeQuery(sql.toString());
		q.setParameter(1, p.getStatusprojeto().getId());
		q.setParameter(2, p.getUsuario().getId());
		q.setParameter(3, p.getNome());
		q.setParameter(4, p.getCliente());
		q.setParameter(5, p.getResumo());
		q.setParameter(6, p.getJustificativa());
		q.setParameter(7, p.getNecessidades());
		q.setParameter(8, p.getProduto());
		q.setParameter(9, p.getPremissas());
		q.setParameter(10, p.getRestricoes());
		q.setParameter(11, p.getResponsabilidadesgp());
		q.setParameter(12, p.getDatacriacao());
		q.setParameter(13, p.getInicio());
		q.setParameter(14, p.getFimprevisto());
		q.setParameter(15, p.getOrcamento());
		q.setParameter(16, p.getId());
		q.executeUpdate();
	}

	@Override
	public void removeStakeholder(Long stakeholderId) {
		Query q = em.createNativeQuery("delete from stakeholder where id = ?");
		q.setParameter(1, stakeholderId);
		q.executeUpdate();
	}

	@Override
	public void addStakeholder(Long projetoId, Long usuarioId) {
		Query q = em
				.createNativeQuery("insert into stakeholder(projetoid,usuarioid) values(?,?)");
		q.setParameter(1, projetoId);
		q.setParameter(2, usuarioId);
		q.executeUpdate();
	}

	@Override
	public void changeStatus(Long projetoId, Long statusId) {
		StringBuilder sql = new StringBuilder();
		sql.append("update projeto set statusprojetoid = ?1")
				.append(statusId == 5 ? ", fim = ?3 " : " ")
				.append("where id = ?2 ");
		Query q = em.createNativeQuery(sql.toString());
		q.setParameter(1, statusId);
		q.setParameter(2, projetoId);
		if (statusId == 5) {
			q.setParameter(3, new Date());
		}
		q.executeUpdate();
	}
}
