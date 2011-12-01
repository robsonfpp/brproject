package br.edu.eseg.brproject.control;

import org.jboss.seam.mock.AbstractSeamTest.ComponentTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import no.knowit.seam.openejb.mock.SeamOpenEjbTest;

public class FileManagerBeanTest extends SeamOpenEjbTest{

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
	public void deveEncontrarArquivo() throws Exception {

		new ComponentTest() {
			@Override
			protected void testComponents() throws Exception {
				
				assert invokeMethod("#{fileManager.download(1)}")==null : "Nao fez o download";
			}
		};
	}
}