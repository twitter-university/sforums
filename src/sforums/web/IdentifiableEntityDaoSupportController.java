package sforums.web;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import sforums.dao.IdentifiableEntityDao;
import sforums.domain.IdentifiableEntity;

public abstract class IdentifiableEntityDaoSupportController extends
		ParameterizableViewController {

	protected IdentifiableEntityDao<? extends IdentifiableEntity> dao;

	@Required
	public void setDao(IdentifiableEntityDao<?> dao) {
		this.dao = dao;
	}
}
