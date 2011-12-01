package br.edu.eseg.brproject.control;

import javax.servlet.http.HttpServletRequest;

import no.knowit.seam.openejb.mock.SeamOpenEjbTest;

import org.testng.annotations.Test;

public class ProjetoBeanTest extends SeamOpenEjbTest {
	
	@Test
	public void deveAbrirAPagina(){
		
		
		new FacesRequest("/view/projetoinclude/matriz.xhtml"){
			
			
			@Override
			protected void beforeRequest() {
				// TODO Auto-generated method stub
				super.beforeRequest();
			}
			
			@Override
			protected HttpServletRequest createRequest() {
				// TODO Auto-generated method stub
				return super.createRequest();
			}
			
			@Override
			protected void afterRequest() {
				// TODO Auto-generated method stub
				super.afterRequest();
				System.out.println(getRenderedViewId());
			}
		};
		
		
	}

}
