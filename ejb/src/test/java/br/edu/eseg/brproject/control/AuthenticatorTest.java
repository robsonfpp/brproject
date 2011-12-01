package br.edu.eseg.brproject.control;

import no.knowit.seam.openejb.mock.SeamOpenEjbTest;

import org.jboss.seam.core.Manager;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class AuthenticatorTest extends SeamOpenEjbTest {

	@Override
	@BeforeSuite
	public void beforeSuite() throws Exception {
		
		System.out.println("**** AuthenticatorTest.beforeSuite()");
		
		contextProperties.put("log4j.category.org.jboss.seam.transaction", "DEBUG");
		contextProperties.put("log4j.category.org.jboss.seam.mock", "DEBUG");
		contextProperties.put("log4j.category.no.knowit.seam.openejb.mock", "DEBUG");
		contextProperties.put("log4j.category.no.knowit.seam.example", "DEBUG");
		contextProperties.put("log4j.category.org.jboss.seam.Component", "DEBUG");
		super.beforeSuite();
	}

	
	@Test
	public void shouldAuthenticate() throws Exception {
		new ComponentTest() {

			@Override
			protected void testComponents() throws Exception {
				assert getValue("#{identity.loggedIn}").equals(false);
				
				setValue("#{identity.username}", "rcardoso");
				setValue("#{identity.password}", "123456");
				invokeMethod("#{identity.login}");
				assert getValue("#{identity.loggedIn}").equals(true) : "Login failed";
				
				invokeMethod("#{identity.logout}");
				assert getValue("#{identity.loggedIn}").equals(false) : "Logout failed";
			}
		}.run();
	}
	
	@Test
	public void shouldLoginThenLogout() throws Exception {
		
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
				assert invokeMethod("#{authenticator.authenticate}").equals(true) : "Authentication failed";
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
