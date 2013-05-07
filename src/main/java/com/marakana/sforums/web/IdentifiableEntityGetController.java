
package com.marakana.sforums.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.marakana.sforums.domain.IdentifiableEntity;

public class IdentifiableEntityGetController extends AbstractIdentifiableEntityController {
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        long id = ServletRequestUtils.getRequiredLongParameter(request, "id");
        IdentifiableEntity result = super.dao.getById(id);
        super.logger.debug("Got {} by id {}", result, id);
        return new ModelAndView(super.getViewName()).addObject(result);
    }
}
