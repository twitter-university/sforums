
package com.marakana.sforums.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.marakana.sforums.dao.DaoRepository;
import com.marakana.sforums.domain.Category;

public class CategoryDeleteController extends AbstractController {

    private final DaoRepository daoRepository;

    @Autowired
    public CategoryDeleteController(DaoRepository daoRepository) {
        this.daoRepository = daoRepository;
        super.setSupportedMethods(new String[] {
            "DELETE"
        });
    }

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = ServletRequestUtils.getRequiredLongParameter(req, "id");
        Category category = this.daoRepository.getCategoryDao().getById(id);
        if (category == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No category by id: " + id);
        } else {
            this.daoRepository.getCategoryDao().delete(category);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        return null;
    }
}
