package sforums.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import sforums.dao.IdentifiableEntityDao;
import sforums.domain.IdentifiableEntity;

public abstract class IdentifiableEntityDaoSupportController extends
		ParameterizableViewController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected IdentifiableEntityDao<? extends IdentifiableEntity> dao;

	@Required
	public void setDao(IdentifiableEntityDao<?> dao) {
		this.dao = dao;
	}
}
