package br.edu.eseg.brproject.control.transactions;

import javax.ejb.Local;

@Local
public interface AvaliacaoTx {

	public void salvarAvaliacao(Long usuarioId, Long stakeholderId,
			Long projetoId, Long notaId);
}
