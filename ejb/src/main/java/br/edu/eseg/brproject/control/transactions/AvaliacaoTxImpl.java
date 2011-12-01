package br.edu.eseg.brproject.control.transactions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;

@Stateless
@Name("avaliacaoTx")
public class AvaliacaoTxImpl implements AvaliacaoTx {

	@PersistenceContext
	EntityManager em;

	@Override
	public void salvarAvaliacao(Long usuarioId, Long stakeholderId,
			Long projetoId, Long notaId) {
		StringBuilder sql = new StringBuilder();

		sql.append("select id from stakeholder where projetoid = ? and usuarioid = ?");
		Query q = em.createNativeQuery(sql.toString());
		q.setParameter(1, projetoId);
		q.setParameter(2, usuarioId);
		Object avaliadorId = q.getSingleResult();

		sql = new StringBuilder();

		sql.append(
				"insert into notastakeholder (projetoid, notaid, stakeholderavaliadoid, stakeholderavaliadorid)")
				.append("values (?,?,?,?)");
		q = em.createNativeQuery(sql.toString());
		q.setParameter(1, projetoId);
		q.setParameter(2, notaId);
		q.setParameter(3, stakeholderId);
		q.setParameter(4, avaliadorId);
		q.executeUpdate();
	}
}