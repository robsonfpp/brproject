package br.edu.eseg.brproject.control.transactions;

import javax.ejb.Local;

import br.edu.eseg.brproject.model.Projeto;

@Local
public interface ProjetoTx {
	
	public Long createProjeto(Projeto p);
	
	public void updateProjeto(Projeto p);
	
	public void changeStatus(Long projetoId, Long statusId);
	

}
