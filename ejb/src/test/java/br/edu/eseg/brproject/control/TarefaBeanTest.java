package br.edu.eseg.brproject.control;

import java.util.List;

import no.knowit.seam.openejb.mock.SeamOpenEjbTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.edu.eseg.brproject.model.Tarefa;

public class TarefaBeanTest extends SeamOpenEjbTest {

	private static Tarefa tarefa;
	private static Long projetoId = new Long(1);

	@BeforeClass
	public void beforeClass() {
		System.out.println("===== Testando o componente TarefaBean =====");
	}

	@Test
	public void criarTarefa() throws Exception {

		System.out.println("Deve criar uma tarefa");

		new FacesRequest() {
			protected void updateModelValues() throws Exception {
				setValue("#{tarefa.projetoId}", projetoId);
				setValue("#{tarefa.tarefaId}", new Long(0));
				invokeMethod("#{tarefa.prepareEditor()}");
				setValue("#{tarefa.tarefa.nome}", "Tarefa Teste");

			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{tarefa.salvarTarefa()}");
			};

			protected void renderResponse() throws Exception {
				assert getValue("#{facesContext.maximumSeverity.toString()}")
						.equals("INFO 0") : "Ocorreu um erro ao criar a tarefa"
						+ getValue("#{facesContext.maximumSeverity}");
			};

		}.run();

	}

	@Test(dependsOnMethods = "criarTarefa")
	public void buscarTarefa() throws Exception {
		System.out.println("Deve buscar a tarefa criada");
		new FacesRequest() {
			protected void invokeApplication() throws Exception {
				List<Tarefa> tarefas = (List<Tarefa>) invokeMethod("#{tarefa.getTarefas("
						+ projetoId + ")}");
				for (Tarefa t : tarefas) {
					if (t.getNome().equals("Tarefa Teste")) {
						tarefa = t;
						break;
					}
				}
				assert tarefa != null : "tarefa esta null";
				assert tarefa.getId() != null : "ID da tarefa esta null";
				assert tarefa.getEap().length() == 1 : "Eap incorreta: "
						+ tarefa.getEap();

			};
		}.run();
	}

	@Test(dependsOnMethods = "buscarTarefa")
	public void cadastrarRecurso() throws Exception {
		System.out.println("Deve cadastrar um recurso");
		new FacesRequest() {
			@Override
			protected void updateModelValues() throws Exception {
				setValue("#{tarefa.projetoId}", projetoId);
				setValue("#{tarefa.nomeRecurso}", "Robson");
				setValue("#{tarefa.tipoId}", new Long(1));
			}

			protected void invokeApplication() throws Exception {
				invokeMethod("#{tarefa.addRecurso()}");
			};

			protected void renderResponse() throws Exception {
				List recursos = (List) getValue("#{tarefa.recursos}");
				assert recursos.size() > 0 : "nao adicio nou o recurso";
			};
		}.run();
	}

	@Test(dependsOnMethods = "cadastrarRecurso")
	public void alterarTarefa() throws Exception {
		System.out.println("Deve alterar uma tarefa");
		new FacesRequest() {
			protected void updateModelValues() throws Exception {
				setValue("#{tarefa.projetoId}", projetoId);
				setValue("#{tarefa.tarefaId}", tarefa.getId());
				invokeMethod("#{tarefa.prepareEditor()}");
				setValue("#{tarefa.tarefa.nome}", "Tarefa Teste com outro nome");
				setValue("#{tarefa.tarefapaiId}", new Long(3));
				setValue("#{tarefa.percentComp}", 50);
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{tarefa.salvarTarefa()}");
			};

			protected void renderResponse() throws Exception {
				assert getValue("#{facesContext.maximumSeverity.toString()}")
						.equals("INFO 0") : "Ocorreu um erro ao alterar a tarefa"
						+ getValue("#{facesContext.maximumSeverity}");
			};
		}.run();

	}

	@Test(dependsOnMethods = "alterarTarefa")
	public void buscarTarefaAlterada() throws Exception {
		System.out.println("Deve buscar uma tarefa");
		new FacesRequest() {
			protected void invokeApplication() throws Exception {
				List<Tarefa> tarefas = (List<Tarefa>) invokeMethod("#{tarefa.getTarefas("
						+ projetoId + ")}");
				for (Tarefa t : tarefas) {
					if (t.getNome().equals("Tarefa Teste com outro nome")) {
						tarefa = t;
						break;
					}
				}
				assert tarefa != null : "tarefa esta null";
				assert tarefa.getId() != null : "ID da tarefa esta null";
				assert tarefa.getTarefaPai() != null : "Nao adicionaou tarefa pai";
				assert tarefa.getEap().length() == (tarefa.getTarefaPai()
						.getEap().length() + 2) : "Eap incorreta: "
						+ tarefa.getEap();
				assert tarefa.getPorcentcomp() > 0 : "nao alterou o andamento da tarefa";
			};
		}.run();
	}

	@Test(dependsOnMethods = "buscarTarefaAlterada")
	public void deletarTarefa() throws Exception {
		System.out.println("Deve deletar uma tarefa");
		new FacesRequest() {
			protected void updateModelValues() throws Exception {
				setValue("#{tarefa.projetoId}", projetoId);
				setValue("#{tarefa.tarefaId}", tarefa.getId());
				invokeMethod("#{tarefa.prepareEditor()}");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{tarefa.excluirTarefa()}");
			};

			protected void renderResponse() throws Exception {
				assert getValue("#{facesContext.maximumSeverity.toString()}")
						.equals("INFO 0") : "Erro ao deletar a tarefa: "
						+ getValue("#{facesContext.maximumSeverity}");
			};

		}.run();

	}
}
