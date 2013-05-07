
package com.marakana.sforums.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.marakana.sforums.dao.DaoRepository;
import com.marakana.sforums.domain.User;

public class UserGetController implements Controller {

    private final DaoRepository daoRepository;

    @Autowired
    public UserGetController(DaoRepository daoRepository) {
        this.daoRepository = daoRepository;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = ServletRequestUtils.getRequiredLongParameter(req, "id");
        User user = this.daoRepository.getUserDao().getById(id);
        if (user == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No user by id: " + id);
            return null;
        } else {
            ModelAndView mav = new ModelAndView("userView");
            mav.addObject("user", user);
            return mav;
        }
    }
}
