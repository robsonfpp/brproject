package br.edu.eseg.brproject.control.transactions;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.jboss.seam.annotations.Name;

import br.edu.eseg.brproject.model.Projeto;
import br.edu.eseg.brproject.model.Recurso;
import br.edu.eseg.brproject.model.Tarefa;
import br.edu.eseg.brproject.model.Utilizacaorecurso;

@Stateful
@Name("ganttTx")
public class GanttTxImpl implements GanttTx {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@Override
	public void saveTarefa(Tarefa tarefa) {
		Query qTask = null;
		Query q = null;
		// tarefa
		if (tarefa.getId() == null) {
			System.out.println("Nova tarefa!!");

			tarefa.setEap(getNewEap(tarefa.getTarefaPai()!=null?tarefa.getTarefaPai().getId():null, tarefa
					.getProjeto().getId()));
			qTask = em
					.createNativeQuery("insert into tarefa(eap,tarefapaiid,projetoid,nome,inicio,fim,porcentcomp,milestone) values (?1,?2,?3,?4,?5,?6,?7,?8)");
			qTask.setParameter(3, tarefa.getProjeto().getId());
			qTask.setParameter(1, tarefa.getEap());
			qTask.setParameter(7, new Double(0.0));
		} else {
			System.out.println("Update de tarefa!!");
			qTask = em
					.createNativeQuery("update tarefa set tarefapaiid=?2,nome=?4,inicio=?5,fim=?6,porcentcomp=?7,milestone=?8 where id = ?9");
			qTask.setParameter(7, tarefa.getPorcentcomp());
			qTask.setParameter(9, tarefa.getId());

			// verificando mudanca de tarefa pai
			if (tarefa.getTarefaPai()!=null && tarefa.getTarefaPai().getId() != null) {
				q = em.createNativeQuery(
						"select * from tarefa where tarefapaiid = ?1 and id = ?2",
						Tarefa.class);
				q.setParameter(1, tarefa.getTarefaPai().getId());
			} else {
				q = em.createNativeQuery(
						"select * from tarefa where tarefapaiid is null and id = ?2",
						Tarefa.class);
			}
			q.setParameter(2, tarefa.getId());
			int pSize = q.getResultList().size();
			System.out.println("Verificando se mudou de tarefa pai: " + pSize);
			if (pSize == 0) {
				System.out.println("mudou!");
				tarefa.setEap(getNewEap(tarefa.getTarefaPai()!=null?tarefa.getTarefaPai().getId():null, tarefa
						.getProjeto().getId()));

				q = em.createNativeQuery(
						"select p.* from tarefa t join tarefa p on p.id = t.tarefapaiid where t.id = ?",
						Tarefa.class);
				q.setParameter(1, tarefa.getId());
				List<Tarefa> r = q.getResultList();
				Tarefa paiNovo = tarefa.getTarefaPai();
				Tarefa paiVelho = r.size() > 0 ? r.get(0) : null;
				System.out.println("Pai novo: " + paiNovo);
				System.out.println("Pai velho:" + paiVelho);

				if (paiNovo == null) {
					q = em.createNativeQuery("update tarefa set tarefapaiid = null, eap = ? where id = ?");
					q.setParameter(1, tarefa.getEap());
					q.setParameter(2, tarefa.getId());
					q.executeUpdate();
					organizeChildren(tarefa);
					organizeChildren(paiVelho);
					adjustParentTime(paiVelho.getId());
					calcPercent(paiVelho.getId());
				} else if (paiVelho == null) {
					q = em.createNativeQuery("update tarefa set tarefapaiid = ?, eap = ? where id = ?");
					q.setParameter(1, paiNovo.getId());
					q.setParameter(2, tarefa.getEap());
					q.setParameter(3, tarefa.getId());
					q.executeUpdate();

					q = em.createNativeQuery(
							"select * from tarefa where tarefapaiid is null and eap > ? and projetoid = ?",
							Tarefa.class);
					q.setParameter(1, tarefa.getEap());
					q.setParameter(2, tarefa.getProjeto().getId());
					List<Tarefa> tasksToFix = q.getResultList();
					for (Tarefa t : tasksToFix) {
						Integer eap = Integer.parseInt(t.getEap());
						t.setEap(String.valueOf(--eap));
						organizeChildren(t);
					}
					organizeChildren(tarefa);
				} else {
					q = em.createNativeQuery("update tarefa set tarefapaiid = ?, eap = ? where id = ?");
					q.setParameter(1, paiNovo.getId());
					q.setParameter(2, tarefa.getEap());
					q.setParameter(3, tarefa.getId());
					q.executeUpdate();
					organizeChildren(tarefa);
					organizeChildren(paiVelho);
					adjustParentTime(paiVelho.getId());
					calcPercent(paiVelho.getId());
				}
			}
		}
		qTask.setParameter(2, tarefa.getTarefaPai()!=null?tarefa.getTarefaPai().getId():null);
		qTask.setParameter(4, tarefa.getNome());
		qTask.setParameter(5, tarefa.getInicio());
		qTask.setParameter(6, tarefa.getFim());
		qTask.setParameter(8,
				tarefa.getMilestone() != null ? tarefa.getMilestone() : false);
		qTask.executeUpdate();

		if (tarefa.getId() == null) {
			// se foi insercao
			tarefa.setId(getLastInsertedId());
		}

		// recursos
		q = em.createNativeQuery(
				"select * from utilizacaorecurso where tarefaid = ?",
				Utilizacaorecurso.class);
		q.setParameter(1, tarefa.getId());
		List<Utilizacaorecurso> recursos = q.getResultList();
		for (Utilizacaorecurso r : tarefa.getUtilizacaorecursos()) {
			if (!recursos.contains(r)) {
				// insere
				q = em.createNativeQuery("insert into utilizacaorecurso(recursoid,tarefaid) values(?,?)");
				q.setParameter(1, r.getRecurso().getId());
				q.setParameter(2, tarefa.getId());
				q.executeUpdate();
			}
		}
		for (Utilizacaorecurso r : recursos) {
			if (!tarefa.getUtilizacaorecursos().contains(r)) {
				// deleta
				q = em.createNativeQuery("delete from utilizacaorecurso where recursoid = ? and tarefaid = ?");
				q.setParameter(1, r.getRecurso().getId());
				q.setParameter(2, tarefa.getId());
				q.executeUpdate();
			}
		}

		// predecessores
		q = em.createNativeQuery("delete from tarefapredecessora where tarefaid = ?");
		q.setParameter(1, tarefa.getId());
		int r = q.executeUpdate();
		System.out.println("deletou " + r + " tarefas predecessoras");
		System.out.println("predecessores para inserir: "+tarefa.getTarefaspredecessoras().size());
		for (Tarefa t : tarefa.getTarefaspredecessoras()) {
			System.out.println("inserindo :"+t.getId()+" - "+tarefa.getId());
			q = em.createNativeQuery("insert into tarefapredecessora(tarefapredecessoraid,tarefaid) values(?,?)");
			q.setParameter(1, t.getId());
			q.setParameter(2, tarefa.getId());
			q.executeUpdate();
		}

//		em.clear();
//		em.flush();

		adjustParentTime(tarefa.getTarefaPai()!=null?tarefa.getTarefaPai().getId():null);
		calcPercent(tarefa.getTarefaPai()!=null?tarefa.getTarefaPai().getId():null);
	}

	@Override
	public void excluirTarefa(Tarefa tarefa) {
		Query q = em
				.createNativeQuery("delete from utilizacaorecurso where tarefaid = ?");
		q.setParameter(1, tarefa.getId());
		q.executeUpdate();

		q = em.createNativeQuery("delete from tarefapredecessora where tarefaid = ?1 or tarefapredecessoraid = ?1");
		q.setParameter(1, tarefa.getId());
		q.executeUpdate();

		q = em.createNativeQuery("delete from tarefa where id = ?");
		q.setParameter(1, tarefa.getId());
		q.executeUpdate();

		if (tarefa.getTarefaPai() != null
				&& tarefa.getTarefaPai().getId() != null) {
			organizeChildren(tarefa.getTarefaPai());
		} else {
			q = em.createNativeQuery(
					"select * from tarefa where tarefapaiid is null and eap > ? and projetoid = ?",
					Tarefa.class);
			q.setParameter(1, tarefa.getEap());
			q.setParameter(2, tarefa.getProjeto().getId());
			List<Tarefa> parents = q.getResultList();
			for (Tarefa p : parents) {
				Integer oldEap = Integer.parseInt(p.getEap());
				p.setEap(String.valueOf(--oldEap));
				q = em.createNativeQuery("update tarefa set eap = ? where id = ?");
				q.setParameter(1, p.getEap());
				q.setParameter(2, p.getId());
				q.executeUpdate();
				organizeChildren(p);
			}
		}
//		em.clear();
//		em.flush();
		adjustParentTime(tarefa.getTarefaPai() != null ? tarefa.getTarefaPai()
				.getId() : null);
		calcPercent(tarefa.getTarefaPai() != null ? tarefa.getTarefaPai()
				.getId() : null);
	}

	@Override
	public Long createRecurso(Recurso r) {
		Query q = em
				.createNativeQuery("insert into recurso(nome,tiporecursoid,projetoid) values(?,?,?)");
		q.setParameter(1, r.getNome());
		q.setParameter(2, r.getTiporecurso().getId());
		q.setParameter(3, r.getProjeto().getId());
		q.executeUpdate();
		return getLastInsertedId();
	}

	@Override
	@Remove
	public void destroy() {

	}

	private void calcPercent(Long parentId) {
		if (parentId == null)
			return;
		Query q = em
				.createNativeQuery("select sum(porcentcomp)/(select count(*) from tarefa where tarefapaiid = ?1) from  tarefa where tarefapaiid = ?1");
		q.setParameter(1, parentId);
		Object percent = q.getResultList().get(0);
		if (percent != null) {
			q = em.createNativeQuery("update tarefa set porcentcomp = ? where id = ?");
			q.setParameter(1, percent);
			q.setParameter(2, parentId);
			q.executeUpdate();
		}
		q = em.createNativeQuery("select tarefapaiid from tarefa where id = ?");
		q.setParameter(1, parentId);
		List<BigInteger> result = q.getResultList();
		if (result.size() > 0) {
			if (result.get(0) != null) {
				calcPercent(result.get(0).longValue());
			}
		}
	}

	private void organizeChildren(Tarefa parent) {
		System.out.println("organizando os filhos da eap " + parent.getEap());
		Query q = em.createNativeQuery(
				"select * from tarefa where tarefapaiid = ? order by eap",
				Tarefa.class);
		q.setParameter(1, parent.getId());
		List<Tarefa> filhos = q.getResultList();
		System.out.println("possui " + filhos.size() + " filhos!");
		for (int i = 1; i <= filhos.size(); i++) {
			String eap = parent.getEap() + "." + i;
			Tarefa filho = filhos.get(i - 1);
			if (!eap.equals(filho.getEap())) {
				// se o filho esta com o eap errado
				System.out.println("alterando eap de " + filho.getEap()
						+ " para " + eap);
				q = em.createNativeQuery("update tarefa set eap = ? where id = ?");
				filho.setEap(eap);
				q.setParameter(1, filho.getEap());
				q.setParameter(2, filho.getId());
				q.executeUpdate();
				organizeChildren(filho);
			} else {
				System.out.println("nao precisa alterar eap " + filho.getEap()
						+ " para " + eap);
			}
		}
		System.out.println("filhos da eap " + parent.getEap()
				+ " estao organizado!");
	}

	private void adjustParentTime(Long parentId) {
		System.out.println("Ajustando tempo para: " + parentId);
		if (parentId == null) {
			System.out.println("nao precisa ajustar");
			return;
		}
		Query q = em
				.createNativeQuery("select min(inicio), max(fim) from tarefa where tarefapaiid = ?");
		q.setParameter(1, parentId);
		List<Object[]> times = q.getResultList();
		if (times.size() > 0) {
			Object inicio = times.get(0)[0];
			Object fim = times.get(0)[1];
			if (inicio != null || fim != null) {
				q = em.createNativeQuery("update tarefa set inicio = ?, fim = ? where id = ?");
				q.setParameter(1, inicio);
				q.setParameter(2, fim);
				q.setParameter(3, parentId);
				q.executeUpdate();
			}
			System.out.println("Novo tempo para " + parentId + " eh : "
					+ inicio + " - " + fim);
		}
		q = em.createNativeQuery("select tarefapaiid from tarefa where id = ?");
		q.setParameter(1, parentId);
		List<BigInteger> result = q.getResultList();
		if (result.size() > 0) {
			if (result.get(0) != null) {
				System.out.println(parentId + " Possui tarefa pai: "
						+ result.get(0).longValue());
				adjustParentTime(result.get(0).longValue());
			} else {
				System.out.println("nao possui tarefa pai");
			}
		}
		System.out.println("tarefa pai: " + parentId + " ajustado!");
	}

	private String getNewEap(Long parentId, Long projetoId) {
		Query q = null;
		if (parentId == null) {
			q = em.createNativeQuery("select max(eap) from tarefa where tarefapaiid is null and projetoid = ?");
			q.setParameter(1, projetoId);
		} else {
			q = em.createNativeQuery("select max(eap) from tarefa where tarefapaiid = ?");
			q.setParameter(1, parentId);
		}
		List<String> result = q.getResultList();
		System.out.println("tamanho da lista: " + result.size());
		String newEap = "";
		if (result.size() > 0 && result.get(0) != null) {
			String maxEap = result.get(0);
			System.out.println("maximo EAP da base: " + maxEap);
			newEap = maxEap.substring(0, maxEap.length() - 1);
			Integer minorNumber = Integer.parseInt(maxEap.substring(
					maxEap.length() - 1, maxEap.length()));
			newEap += (minorNumber + 1);
		} else {
			System.out.println("Tarefa pai: " + parentId
					+ " ainda nao tem filhos");
			q = em.createNativeQuery("select eap from tarefa where id = ?");
			q.setParameter(1, parentId);
			newEap = (String) q.getResultList().get(0);
			newEap += ".1";
		}
		System.out.println("Eap gerado: " + newEap);
		return newEap;
	}

	private Long getLastInsertedId() {
		StringBuilder sql = new StringBuilder();
		sql.append("select last_insert_id() from dual");
		Query q = em.createNativeQuery(sql.toString());
		Object r = q.getSingleResult();
		return Long.valueOf(r.toString());
	}

}