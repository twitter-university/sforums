
package com.marakana.sforums.web;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.marakana.sforums.dao.DaoRepository;

public abstract class AbstractDaoAccessServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static DaoRepository daoRepository;

    protected final DaoRepository getDaoRepository() throws DataAccessException {
        if (daoRepository == null) {
            daoRepository = WebApplicationContextUtils.getRequiredWebApplicationContext(
                    super.getServletContext()).getBean(DaoRepository.class);
        }
        return daoRepository;
    }

    protected boolean isEmpty(String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }
}
