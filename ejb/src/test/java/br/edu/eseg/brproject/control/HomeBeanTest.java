package br.edu.eseg.brproject.control;

import no.knowit.seam.openejb.mock.SeamOpenEjbTest;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class HomeBeanTest extends SeamOpenEjbTest {

	@Override
	@BeforeSuite
	public void beforeSuite() throws Exception {

		System.out.println("**** HomeBeanTest.beforeSuite()");

		contextProperties.put("log4j.category.org.jboss.seam.transaction",
				"DEBUG");
		contextProperties.put("log4j.category.org.jboss.seam.mock", "DEBUG");
		contextProperties.put("log4j.category.no.knowit.seam.openejb.mock",
				"DEBUG");
		contextProperties.put("log4j.category.no.knowit.seam.example", "DEBUG");
		contextProperties.put("log4j.category.org.jboss.seam.Component",
				"DEBUG");
		super.beforeSuite();
	}

	@Test
	public void deveEncontrarProjetos() throws Exception {

		new ComponentTest() {
			
			@Override
			protected void testComponents() throws Exception {
				// TODO Auto-generated method stub
				assert !invokeMethod("#{home.projetos.size()}").equals(0) : "Nao encontrou projetos";
			}
		};
		
//		new FacesRequest("/view/home.xhtml") {
//			protected void invokeApplication() throws Exception {
//				assert !invokeMethod("#{home.projetos.size()}").equals(0) : "Nao encontrou projetos";
//			};
//
//		}.run();
	}
	
	@Test
	public void deveEncontrarSolicitacoesMudancas() throws Exception {

		new ComponentTest() {
			
			@Override
			protected void testComponents() throws Exception {
				// TODO Auto-generated method stub
				assert !invokeMethod("#{home.solicitacoes.size()}").equals(0) : "Nao encontrou solicitacoes de mudanca";
			}
		};
		
//		new FacesRequest("/view/home.xhtml") {
//			protected void invokeApplication() throws Exception {
//				assert !invokeMethod("#{home.solicitacoes.size()}").equals(0) : "Nao encontrou solicitacoes de mudanca";
//			};
//
//		}.run();
	}
}