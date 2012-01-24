package br.edu.eseg.brproject.control;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javassist.Modifier;

import javax.faces.context.ExternalContext;

import no.knowit.seam.openejb.mock.SeamOpenEjbTest;

import org.jboss.seam.Component;
import org.jboss.seam.mock.MockHttpServletResponse;
import org.richfaces.component.UIFileUpload;
import org.richfaces.component.html.HtmlFileUpload;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.richfaces.taglib.FileUploadTag;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.edu.eseg.brproject.model.Arquivo;

public class FileManagerBeanTest extends SeamOpenEjbTest {

	private static File file;
	private static byte[] data;
	private static Long arquivoId;

	@BeforeClass
	public void beforeClass() throws Exception {
		System.out.println("===== Testando o componente FileManagerBean =====");

		String path = new File(".").getAbsolutePath();
		path = path.substring(0, path.length() - 1);
		System.out.println(path + "testng.xml");
		file = new File(path + "testng.xml");
		data = new byte[(int) file.length()];
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(data);
		fileInputStream.close();
	}

	@Test
	public void arquivoNaoEncontrado() throws Exception {
		System.out.println("não deve encontrar arquivos");

		new FacesRequest() {
			protected void invokeApplication() throws Exception {
				invokeMethod("#{fileManager.prepareArquivos('aberturaprojeto',1,1)}");
			};

			protected void renderResponse() throws Exception {
				List<Arquivo> arquivos = (List<Arquivo>) getValue("#{fileManager.arquivos}");
				assert arquivos.size() == 0 : "encontrou arquivo(s)";
			};

		}.run();

	}

	@Test(dependsOnMethods = "arquivoNaoEncontrado")
	public void anexarArquivo() throws Exception {

		System.out.println("deve anexar um arquivo");

		new FacesRequest("/view/projetoinclude/fileManager.xhtml") {

			protected void invokeApplication() throws Exception {
				invokeMethod("#{fileManager.prepareArquivos('aberturaprojeto',1,1)}");

				FileManagerBean fm = (FileManagerBean) Component
						.getInstance("fileManager");
				UploadItem item = new UploadItem("testng.xml",
						(int) file.length(), "application/octet-stream", data);
				setField(item, "file", file);
				UIFileUpload component = new HtmlFileUpload();
				FileUploadTag tag = new FileUploadTag();
				UploadEvent event = new UploadEvent(component,
						Arrays.asList(item));
				fm.upload(event);
			};

			protected void renderResponse() throws Exception {
				List<Arquivo> arquivos = (List<Arquivo>) getValue("#{fileManager.arquivos}");
				assert arquivos.size() > 0 : "encontrou arquivo(s)";
				for (Arquivo a : arquivos) {
					if (a.getNome().contains("testng.xml")) {
						arquivoId = a.getId();
						break;
					}
				}
			};

		}.run();
	}

	@Test(dependsOnMethods = "anexarArquivo")
	public void encontrarArquivo() throws Exception {
		System.out.println("deve encontrar arquivos");

		new FacesRequest() {
			protected void invokeApplication() throws Exception {
				invokeMethod("#{fileManager.prepareArquivos('aberturaprojeto',1,1)}");
			};

			protected void renderResponse() throws Exception {
				List<Arquivo> arquivos = (List<Arquivo>) getValue("#{fileManager.arquivos}");
				assert arquivos.size() > 0 : "não encontrou arquivo(s)";
			};

		}.run();

	}

	@Test(dependsOnMethods = "encontrarArquivo")
	public void downloadArquivo() throws Exception {

		System.out.println("Deve fazer download do arquivo");
		
		new FacesRequest() {
			protected void invokeApplication() throws Exception {
				try {
					invokeMethod("#{fileManager.download(" + arquivoId + ")}");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			};

			protected void renderResponse() throws Exception {
				Arquivo a = (Arquivo) getValue("#{arquivoHome.instance}");
				assert a.getId().equals(arquivoId) : "nao fez download";
			};
		}.run();

	}

}