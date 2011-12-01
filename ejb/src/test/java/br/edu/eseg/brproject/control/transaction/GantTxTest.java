package br.edu.eseg.brproject.control.transaction;

import org.testng.annotations.BeforeSuite;

import no.knowit.seam.openejb.mock.SeamOpenEjbTest;

public class GantTxTest extends SeamOpenEjbTest {

	@Override
	@BeforeSuite
	public void beforeSuite() throws Exception {

		System.out.println("**** AuthenticatorTest.beforeSuite()");

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

	
	
}
