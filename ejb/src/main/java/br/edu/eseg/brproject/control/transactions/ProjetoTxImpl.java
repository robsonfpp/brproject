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
				.append("datacriacao,inicio,fimprevisto)")
				.append("values(?,?,?,?,?,?,?)");
		Query q = em.createNativeQuery(sql.toString());
		q.setParameter(1, p.getStatusprojeto().getId());
		q.setParameter(2, p.getUsuario().getId());
		q.setParameter(3, p.getNome());
		q.setParameter(4, p.getCliente());
		q.setParameter(5, p.getDatacriacao());
		q.setParameter(6, p.getInicio());
		q.setParameter(7, p.getFimprevisto());
		q.executeUpdate();

		Long projetoId = getLastInsertedId();

		sql = new StringBuilder();
		sql.append("insert into tarefa(eap,projetoid,nome,inicio,fim,porcentcomp,milestone) values('0',?,?,current_timestamp,current_timestamp,0.0,0)");
		q = em.createNativeQuery(sql.toString());
		q.setParameter(1, projetoId);
		q.setParameter(2, "Projeto: " + p.getNome());
		q.executeUpdate();

		return projetoId;
	}

	@Override
	public void updateProjeto(Projeto p) {
		StringBuilder sql = new StringBuilder();
		sql.append("update projeto set ").append("statusprojetoid = ?, ")
				.append("gerenteprojetoid = ?, ").append("nome = ?, ")
				.append("cliente= ?, ").append("inicio= ?, ")
				.append("fimprevisto= ?, ").append("motivoencerrado = ? ")
				.append("where id = ?");
		Query q = em.createNativeQuery(sql.toString());
		q.setParameter(1, p.getStatusprojeto().getId());
		q.setParameter(2, p.getUsuario().getId());
		q.setParameter(3, p.getNome());
		q.setParameter(4, p.getCliente());
		q.setParameter(5, p.getInicio());
		q.setParameter(6, p.getFimprevisto());
		q.setParameter(7, p.getMotivoencerrado());
		q.setParameter(8, p.getId());
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

	private Long getLastInsertedId() {
		StringBuilder sql = new StringBuilder();
		sql.append("select last_insert_id() from dual");
		Query q = em.createNativeQuery(sql.toString());
		Object r = q.getSingleResult();
		return Long.valueOf(r.toString());
	}
}
