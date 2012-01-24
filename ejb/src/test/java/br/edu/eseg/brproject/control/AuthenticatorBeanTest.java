package br.edu.eseg.brproject.control;

import no.knowit.seam.openejb.mock.SeamOpenEjbTest;

import org.jboss.seam.core.Manager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class AuthenticatorBeanTest extends SeamOpenEjbTest {

	@Override
	@BeforeSuite
	public void beforeSuite() throws Exception {

		contextProperties.put("log4j.category.org.jboss.seam.transaction",
				"ERROR");
		contextProperties.put("log4j.category.org.jboss.seam.mock", "ERROR");
		contextProperties.put("log4j.category.no.knowit.seam.openejb.mock",
				"ERROR");
		contextProperties.put("log4j.category.no.knowit.seam.example", "ERROR");
		contextProperties.put("log4j.category.org.jboss.seam.Component",
				"ERROR");
		super.beforeSuite();
	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("===== Testando o componente AvaliacaoBean =====");
	}


	@Test
	public void logarDeslogar() throws Exception {

		System.out.println("Deve logar e deslogar");
		
		new NonFacesRequest() {

			@Override
			protected void renderResponse() {
				assert getValue("#{identity.loggedIn}").equals(false) : "Already logged in";
			}
		}.run();

		new FacesRequest("/login.xhtml") {
			@Override
			protected void updateModelValues() throws Exception {
				assert !isSessionInvalid() : "Invalid session";
				setValue("#{credentials.username}", "rcardoso");
				setValue("#{credentials.password}", null);
			}

			@Override
			protected void invokeApplication() throws Exception {
				assert invokeMethod("#{authenticator.authenticate}").equals(
						true) : "Authentication failed";
				assert invokeMethod("#{identity.login}").equals("loggedIn") : "Login failed";
			}

			@Override
			protected void renderResponse() {
				assert getValue("#{credentials.username}").equals("rcardoso") : "Wrong user name";
				assert !Manager.instance().isLongRunningConversation() : "!Manager.instance().isLongRunningConversation()";
				assert getValue("#{identity.loggedIn}").equals(true) : "Login failed";
			}
		}.run();

		new FacesRequest() {
			@Override
			protected void invokeApplication() throws Exception {
				assert getValue("#{identity.loggedIn}").equals(true) : "Not logged in";
				invokeMethod("#{identity.logout}");
				assert getValue("#{identity.loggedIn}").equals(false) : "Logout failed";
			}
		}.run();

	}

}