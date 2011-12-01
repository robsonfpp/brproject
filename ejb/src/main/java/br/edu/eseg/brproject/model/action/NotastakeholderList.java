package br.edu.eseg.brproject.model.action;

import br.edu.eseg.brproject.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("notastakeholderList")
public class NotastakeholderList extends EntityQuery<Notastakeholder> {

	private static final String EJBQL = "select notastakeholder from Notastakeholder notastakeholder";

	private static final String[] RESTRICTIONS = {};

	private Notastakeholder notastakeholder = new Notastakeholder();

	public NotastakeholderList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Notastakeholder getNotastakeholder() {
		return notastakeholder;
	}
}
