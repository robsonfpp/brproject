package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("usuarioList")
public class UsuarioList extends EntityQuery<Usuario> {

	private static final String EJBQL = "select usuario from Usuario usuario";

	private static final String[] RESTRICTIONS = {
			"lower(usuario.ddd) like lower(concat(#{usuarioList.usuario.ddd},'%'))",
			"lower(usuario.email) like lower(concat(#{usuarioList.usuario.email},'%'))",
			"lower(usuario.endereco) like lower(concat(#{usuarioList.usuario.endereco},'%'))",
			"lower(usuario.login) like lower(concat(#{usuarioList.usuario.login},'%'))",
			"lower(usuario.nome) like lower(concat(#{usuarioList.usuario.nome},'%'))",
			"lower(usuario.senha) like lower(concat(#{usuarioList.usuario.senha},'%'))",
			"lower(usuario.telefone) like lower(concat(#{usuarioList.usuario.telefone},'%'))", };

	private Usuario usuario = new Usuario();

	public UsuarioList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
