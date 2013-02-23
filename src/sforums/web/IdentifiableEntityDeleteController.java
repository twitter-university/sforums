package sforums.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

public class IdentifiableEntityDeleteController extends
		IdentifiableEntityDaoSupportController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long id = ServletRequestUtils.getRequiredLongParameter(request, "id");
		this.dao.deleteById(id);
		if (super.logger.isDebugEnabled()) {
			super.logger.debug("Deleted  entity by id [" + id + "]");
		}
		return new ModelAndView(super.getViewName());
	}
}
