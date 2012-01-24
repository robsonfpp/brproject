package br.edu.eseg.brproject.control;

import java.util.Arrays;
import java.util.List;

import no.knowit.seam.openejb.mock.SeamOpenEjbTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AvaliacaoBeanTest extends SeamOpenEjbTest {

	// projetoId, stkeholderId, notaId
	List<String> entradas = Arrays.asList("3;7;2", "3;8;1");

	@BeforeClass
	public void beforeClass() {
		System.out.println("===== Testando o componente AvaliacaoBean =====");
	}

	@Test
	public void avaliar() throws Exception {
		System.out.println("Deve fazer login e avaliar um stakeholder");

		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
			};

		}.run();

		for (String e : entradas) {
			System.out.println("==== Avaliando entrada: "+e);
			final Long projetoId = new Long(e.split(";")[0]);
			final Long stakeholderId = new Long(e.split(";")[1]);
			final Long notaId = new Long(e.split(";")[2]);

			new FacesRequest() {

				protected void updateModelValues() throws Exception {
					setValue("#{projetoHome.projetoId}", projetoId);
					setValue("#{avaliacao.avaliadoId}", stakeholderId);
					setValue("#{avaliacao.notaid}", notaId);
				};

				protected void invokeApplication() throws Exception {
					invokeMethod("#{avaliacao.salvarNotaStakeholder()}");
				};

				protected void renderResponse() throws Exception {
					assert getValue("#{facesContext.maximumSeverity.toString()}").equals(
							"INFO 0") : "Algo deu Errado: "
							+ getValue("#{facesContext.maximumSeverity}");
				};

			}.run();
		}
	}
}
