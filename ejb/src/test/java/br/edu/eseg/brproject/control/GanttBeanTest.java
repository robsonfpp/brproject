package br.edu.eseg.brproject.control;

import no.knowit.seam.openejb.mock.SeamOpenEjbTest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GanttBeanTest extends SeamOpenEjbTest {

	@BeforeClass
	public void beforeClass() {
		System.out.println("===== Testando o componente GanttBean =====");
	}

	@Test
	public void macrotarefas() throws Exception {
		System.out.println("Deve buscar macro tarefas do projeto");

		new NonFacesRequest() {

			protected void renderResponse() throws Exception {
				String json = (String) getValue("#{gantt.getTarefas('1','visao_geral')}");
				assert !json.equals("Error") : "json deu erro!";
			};

		}.run();

	}

	@Test
	public void todasTarefas() throws Exception {
		System.out.println("Deve buscar todas as tarefas do projeto");
		new NonFacesRequest() {

			protected void renderResponse() throws Exception {
				String json = (String) getValue("#{gantt.getTarefas('1','visao_detalhada')}");
				assert !json.equals("Error") : "json deu erro!";
			};

		}.run();
	}

}
