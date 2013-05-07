
package com.marakana.sforums.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.marakana.sforums.dao.DaoRepository;
import com.marakana.sforums.domain.User;

public class UserDeleteController extends AbstractController {

    private final DaoRepository daoRepository;

    @Autowired
    public UserDeleteController(DaoRepository daoRepository) {
        this.daoRepository = daoRepository;
        super.setSupportedMethods(new String[] {
            "DELETE"
        });
    }

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = ServletRequestUtils.getRequiredLongParameter(req, "id");
        User user = this.daoRepository.getUserDao().getById(id);
        if (user == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No user by id: " + id);
        } else {
            this.daoRepository.getUserDao().delete(user);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        return null;
    }
}
