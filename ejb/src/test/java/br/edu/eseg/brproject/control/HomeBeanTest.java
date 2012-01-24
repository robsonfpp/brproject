package br.edu.eseg.brproject.control;

import java.util.List;

import no.knowit.seam.openejb.mock.SeamOpenEjbTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomeBeanTest extends SeamOpenEjbTest {

	@BeforeClass
	public void beforeClass() throws Exception {
		System.out.println("===== Testando o componente HomeBean =====");
	}

	@Test
	public void deveEncontrarProjetos() throws Exception {

		System.out.println("Deve encontrar projetos");

		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
				setOutcome("/view/home.xhtml");
			};

		}.run();

		new FacesRequest("/view/home.xhtml") {

			@Override
			protected void renderResponse() throws Exception {
				List projetos = (List) getValue("#{home.projetos}");
				assert projetos.size() > 0 : "Nao encontrou projetos";
			}
		}.run();

	}

	@Test(dependsOnMethods = "deveEncontrarProjetos")
	public void deveEncontrarAvaliacoes() throws Exception {

		System.out.println("Deve encontrar avaliacoes");

		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "mrezende");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
				setOutcome("/view/home.xhtml");
			};

		}.run();

		new FacesRequest("/view/home.xhtml") {

			@Override
			protected void renderResponse() throws Exception {
				List avaliacoes = (List) getValue("#{home.avaliacoes}");
				assert avaliacoes.size() > 0 : "nao encontrou avaliacoes";
			}
		}.run();
	}

	@Test(dependsOnMethods = "deveEncontrarAvaliacoes")
	public void deveEncontrarSolicitacoesMudancas() throws Exception {
		System.out.println("Deve encontrar solicitacoes");
		new FacesRequest("/login.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
			};

			protected void invokeApplication() throws Exception {
				invokeMethod("#{identity.login()}");
				setOutcome("/view/home.xhtml");
			};

		}.run();

		new FacesRequest("/view/home.xhtml") {

			@Override
			protected void renderResponse() throws Exception {
				List solicitacoes = (List) getValue("#{home.solicitacoes}");
				assert solicitacoes.size() > 0 : "Nao encontrou solicitacoes";
			}
		}.run();
	}

}