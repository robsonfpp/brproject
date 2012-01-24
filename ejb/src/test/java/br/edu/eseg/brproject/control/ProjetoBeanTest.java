package br.edu.eseg.brproject.control;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import no.knowit.seam.openejb.mock.SeamOpenEjbTest;

import org.jboss.seam.Component;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.edu.eseg.brproject.model.Stakeholder;

public class ProjetoBeanTest extends SeamOpenEjbTest {

	private static Long projetoId;
	private static Stakeholder stakeholder;

	@BeforeClass
	public void beforeClass() {
		System.out.println("===== Testando o componente ProjetoBean =====");
	}

	@Test
	public void criarProjeto() throws Exception {
		System.out.println("Deve criar um projeto");
		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
			};

		}.run();

		new FacesRequest() {
			@Override
			protected void updateModelValues() throws Exception {
				setValue("#{statusprojetoHome.statusprojetoId}", new Long(1));
				setValue("#{usuarioHome.usuarioId}",
						getValue("#{loggedUser.id}"));
				setValue("#{projeto.projeto.nome}", "Novo projeto teste");
				setValue("#{projeto.projeto.cliente}",
						"Cliente do projeto teste");
				setValue("#{projeto.projeto.inicio}", new Date());
			}

			protected void invokeApplication() throws Exception {
				assert invokeMethod("#{projeto.salvarProjeto()}").equals(
						"salvou") : "Nao salvou o projeto";
				setOutcome("salvou");
			};

			protected void renderResponse() throws Exception {
				projetoId = (Long) getValue("#{projetoHome.projetoId}");
				assert projetoId == 4 : "id invalido do projeto: " + projetoId;
			};
		}.run();
	}

	@Test(dependsOnMethods = "criarProjeto")
	public void alterarFaseProjeto() throws Exception {
		System.out.println("Deve alterar a fase de um projeto");

		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
			};

		}.run();

		new FacesRequest() {
			protected void updateModelValues() throws Exception {
				assert projetoId != null;
				setValue("#{projetoHome.projetoId}", projetoId);
				setValue("#{projeto.changeStatusId}", new Long(2));
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{projeto.changeStatus()}");
			};

			protected void renderResponse() throws Exception {
				assert getValue("#{facesContext.maximumSeverity.toString()}")
						.equals("INFO 0") : "Erro ao muda o projeto de fase "
						+ getValue("#{facesContext.maximumSeverity.toString()}");
			};
		}.run();
	}

	@Test(dependsOnMethods = "criarProjeto")
	public void adicionarStakeholder() throws Exception {

		System.out.println("Deve adicionar um stakeholder");
		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
			};

		}.run();

		new FacesRequest() {
			protected void updateModelValues() throws Exception {
				setValue("#{projetoHome.projetoId}", projetoId);
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{projeto.addStakeholder(2)}");
			};

			protected void renderResponse() throws Exception {
				assert getValue("#{facesContext.maximumSeverity.toString()}")
						.equals("INFO 0") : "Erro ao muda o projeto de fase "
						+ getValue("#{facesContext.maximumSeverity.toString()}");
			};
		}.run();
	}

	@Test(dependsOnMethods = "criarProjeto")
	public void naoDeveEncontrarLicoes() throws Exception {
		System.out.println("Não deve encontrar licoes");

		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
			};

		}.run();

		new NonFacesRequest() {
			protected void renderResponse() throws Exception {
				Set licoes = (Set) getValue("#{projeto.projeto.licoes}");
				assert licoes.size() == 0 : "tem licoes";
			};
		}.run();
	}

	@Test(dependsOnMethods = "criarProjeto")
	public void naoDeveEncontrarSolicitacoes() throws Exception {

		System.out.println("Nao deve encintrar solicitacoes");
		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
			};

		}.run();

		new NonFacesRequest() {
			protected void renderResponse() throws Exception {
				Set solicitacoes = (Set) getValue("#{projeto.projeto.solicitacaomudancas}");
				assert solicitacoes.size() == 0 : "tem solicitacoes";
			};
		}.run();
	}

	@Test(dependsOnMethods = "adicionarStakeholder")
	public void buscarStakeholder() throws Exception {
		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
			};

		}.run();

		new FacesRequest() {
			protected void updateModelValues() throws Exception {
				setValue("#{projetoHome.projetoId}", projetoId);
			};

			protected void invokeApplication() throws Exception {
				Set<Stakeholder> stakeholders = (Set) getValue("#{projeto.projeto.stakeholders}");
				assert stakeholders.size() > 0 : "nao tem stakeholders";
				for (Stakeholder s : stakeholders) {
					if (s.getUsuario().getId().equals(new Long(2))) {
						stakeholder = s;
						break;
					}
				}
				assert stakeholder != null : "nao encontrou o stakeholder";
			};
		}.run();
	}

	@Test(dependsOnMethods = "buscarStakeholder")
	public void updateStakeholder() throws Exception {
		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
			};

		}.run();

		new FacesRequest() {
			protected void updateModelValues() throws Exception {
				setValue("#{projetoHome.projetoId}", projetoId);
				stakeholder.setPapel("Alterado o papel deste stakeholder");
				setValue("#{projeto.projeto.stakeholders}",
						new HashSet<Stakeholder>(Arrays.asList(stakeholder)));
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{projeto.updateStakeholder("
						+ stakeholder.getId() + ")}");
			};

			protected void renderResponse() throws Exception {
				Stakeholder s = (Stakeholder) ((Set) getValue("#{projeto.projeto.stakeholders}"))
						.toArray()[0];
				assert s.getPapel()
						.equals("Alterado o papel deste stakeholder") : "naum deu certo =(";
			};
		}.run();
	}

	@Test(dependsOnMethods = "criarProjeto")
	public void removeStakeholder() throws Exception {
		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
			};

		}.run();

		new FacesRequest() {
			protected void updateModelValues() throws Exception {
				setValue("#{projetoHome.projetoId}", projetoId);
			};

			protected void invokeApplication() throws Exception {
				ProjetoBean p = (ProjetoBean) Component.getInstance("projeto");
				for (Stakeholder s : p.getProjeto().getStakeholders()) {
					if (s.getId() == stakeholder.getId()) {
						p.removeStakeholder(s);
						break;
					}
				}
			};

		}.run();

		new NonFacesRequest() {
			protected void renderResponse() throws Exception {
				assert getValue("#{projeto.projeto.stakeholders.size()}")
						.equals(0) : "nao removeu";
			};
		}.run();
	}

	@Test(dependsOnMethods = "criarProjeto")
	public void encerrarProjeto() throws Exception {

		System.out.println("deve encerrqar o projeto");

		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
			};

		}.run();

		new FacesRequest() {
			protected void updateModelValues() throws Exception {
				setValue("#{projetoHome.projetoId}", projetoId);
				setValue("#{projeto.motivoencerrado}",
						"Este processo foi um sucesso!");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{projeto.encerrarProjeto()}");
			};

			protected void renderResponse() throws Exception {
				assert getValue("#{projeto.projeto.statusprojeto.id}").equals(
						new Long(5)) : "Nao encerrou";
			};

		}.run();
	}
}
