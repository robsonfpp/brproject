package br.edu.eseg.brproject.control;

import java.util.Arrays;

import javax.ejb.Stateless;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import br.edu.eseg.brproject.model.Usuario;
import br.edu.eseg.brproject.model.action.UsuarioList;

@Stateless
@Name("authenticator")
public class AuthenticatorBean implements Authenticator {
	@Logger
	private Log log;
	@In
	private Identity identity;
	@In
	private Credentials credentials;
	@In(create = true)
	private UsuarioList usuarioList;
	@Out(scope=ScopeType.SESSION)
	private Usuario loggedUser = new Usuario();
	
	public boolean authenticate() {
		log.info("Fazendo login de '{0}'", credentials.getUsername());

		usuarioList.setRestrictionExpressionStrings(Arrays.asList("usuario.login = #{usuarioList.usuario.login}","usuario.senha = #{usuarioList.usuario.senha}"));
		usuarioList.getUsuario().setLogin(credentials.getUsername());
		usuarioList.getUsuario().setSenha(credentials.getPassword());
		
		if (usuarioList.getResultList().size()>0) {
			Usuario u = usuarioList.getResultList().get(0);
			loggedUser.setId(u.getId());
			loggedUser.setNome(u.getNome());
			loggedUser.setGp(u.getGp());
			loggedUser.setDdd(u.getDdd());
			loggedUser.setEmail(u.getEmail());
			loggedUser.setEndereco(u.getEndereco());
			loggedUser.setLogin(u.getLogin());
			loggedUser.setSenha(u.getSenha());
			
			if(loggedUser.getGp()){
				identity.addRole("gerenteProjeto");	
			}else{
				identity.addRole("stakeholder");
			}
			
			log.info("Usuario '{0}' autenticado com sucesso!", credentials.getUsername());
			return true;
		}
		log.info("Usuario '{0}' não autenticado.", credentials.getUsername());
		return false;
	}
}