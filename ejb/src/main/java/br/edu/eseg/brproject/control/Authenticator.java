package br.edu.eseg.brproject.control;

import javax.ejb.Local;

@Local
public interface Authenticator {

    boolean authenticate();
}
