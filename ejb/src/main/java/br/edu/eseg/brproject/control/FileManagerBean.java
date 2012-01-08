package br.edu.eseg.brproject.control;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import br.edu.eseg.brproject.model.Arquivo;
import br.edu.eseg.brproject.model.Projeto;
import br.edu.eseg.brproject.model.Statusprojeto;
import br.edu.eseg.brproject.model.action.ArquivoHome;
import br.edu.eseg.brproject.model.action.ArquivoList;
import br.edu.eseg.brproject.model.action.StatusprojetoHome;

@Name("fileManager")
@Scope(ScopeType.CONVERSATION)
public class FileManagerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@In(create = true)
	private ArquivoHome arquivoHome;

	@In(create = true)
	private ArquivoList arquivoList;

	@In(value = "#{facesContext.externalContext}")
	private ExternalContext extCtx;

	@In(value = "#{facesContext}")
	FacesContext facesContext;

	@In(create = true)
	StatusprojetoHome statusprojetoHome;

	private List<Arquivo> arquivos = new ArrayList<Arquivo>(0);
	private Long projetoid;
	private Long statusprojetoid;
	private String etapa;

	@Begin(join = true)
	public String prepareArquivos(String etapa, Long projetoid,
			Long statusprojetoid) {
		arquivos = findArquivos(null, etapa, projetoid, statusprojetoid);
		this.projetoid = projetoid;
		this.statusprojetoid = statusprojetoid;
		this.etapa = etapa;
		return etapa;
	}

	public void upload(UploadEvent event) throws Exception {
		UploadItem item = event.getUploadItem();
		String nome = item.getFileName();
		byte[] dados = null;
		if (item.isTempFile()) {
			dados = new byte[(int) item.getFile().length()];
			FileInputStream fileInputStream = new FileInputStream(
					item.getFile());
			fileInputStream.read(dados);
			fileInputStream.close();
		} else {
			dados = item.getData();
		}
		Long tamanho = item.getFile().length();
		String extension = nome.substring(nome.lastIndexOf(".") + 1);
		String tipo = "application/octet-stream";
		if ("bmp".equals(extension)) {
			tipo = "image/bmp";
		} else if ("jpg".equals(extension)) {
			tipo = "image/jpeg";
		} else if ("gif".equals(extension)) {
			tipo = "image/gif";
		} else if ("png".equals(extension)) {
			tipo = "image/png";
		} else if ("doc".equals(extension) || "docx".equals(extension)) {
			tipo = "application/msword";
		} else if ("pdf".equals(extension)) {
			tipo = "application/pdf";
		}

		List<Arquivo> result = findArquivos(nome, etapa, projetoid,
				statusprojetoid);

		if (result.size() == 0) {
			arquivoHome.setArquivoId(null);
			arquivoHome.load();
			arquivoHome.getInstance().setNome(nome);
			arquivoHome.getInstance().setTipo(tipo);
			arquivoHome.getInstance().setDados(dados);
			arquivoHome.getInstance().setTamanho(tamanho);
			arquivoHome.getInstance().setEtapa(etapa);
			arquivoHome.getInstance().setProjeto(new Projeto(projetoid));
			arquivoHome.getInstance().setStatusprojeto(
					new Statusprojeto(statusprojetoid));
			System.out.println(arquivoHome.persist());
			arquivos.add(arquivoHome.getInstance());
		} else {
			arquivoHome.setArquivoId(result.get(0).getId());
			arquivoHome.load();
			arquivoHome.getInstance().setTipo(tipo);
			arquivoHome.getInstance().setDados(dados);
			arquivoHome.getInstance().setTamanho(tamanho);
			System.out.println(arquivoHome.update());
		}
	}

	public String download(Long arquivoId) throws Exception {
		System.out.println("Procurando " + arquivoId);
		arquivoHome.setArquivoId(arquivoId);
		Arquivo arquivo = arquivoHome.find();
		HttpServletResponse response = (HttpServletResponse) extCtx
				.getResponse();
		response.setContentType(arquivo.getTipo());
		response.addHeader("Content-disposition", "attachment; filename=\""
				+ arquivo.getNome() + "\"");
		try {
			ServletOutputStream os = response.getOutputStream();
			os.write(arquivo.getDados());
			os.flush();
			os.close();
			facesContext.responseComplete();
		} catch (Exception e) {
			log.error("\nFailure : " + e.toString() + "\n");
			throw e;
		}

		return null;
	}

	private List<Arquivo> findArquivos(String nome, String etapa,
			Long projetoid, Long statusprojetoid) {
		arquivoList.getArquivo().setProjeto(new Projeto(projetoid));
		if (!etapa.equals("monitoriacontrole")) {
			arquivoList.getArquivo().setNome(nome);
			arquivoList.getArquivo().setEtapa(etapa);
			arquivoList.getArquivo().setStatusprojeto(
					new Statusprojeto(statusprojetoid));
		}
		List<String> restrictions = Arrays
				.asList("lower(arquivo.nome) like lower(concat(#{arquivoList.arquivo.nome},'%'))",
						"lower(arquivo.etapa) like lower(concat(#{arquivoList.arquivo.etapa},'%'))",
						"arquivo.projeto.id = #{arquivoList.arquivo.projeto.id}",
						"arquivo.statusprojeto.id = #{arquivoList.arquivo.statusprojeto.id}");
		arquivoList.setRestrictionExpressionStrings(restrictions);
		arquivoList.setOrderColumn("etapa");
		return arquivoList.getResultList();
	}

	public String getStatusprojetoName(Long id) {
		statusprojetoHome.setStatusprojetoId(id);
		return statusprojetoHome.find().getNome();
	}

	public List<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}

	public String getEtapa() {
		return etapa;
	}

}
