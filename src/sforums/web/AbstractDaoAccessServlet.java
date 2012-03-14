package sforums.web;

import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import sforums.dao.DaoRepository;

public abstract class AbstractDaoAccessServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected final Log logger = LogFactory.getLog(this.getClass());

    private static DaoRepository daoRepository;

    protected final DaoRepository getDaoRepository() throws DataAccessException {
        if (daoRepository == null) {
            daoRepository = WebApplicationContextUtils
                    .getRequiredWebApplicationContext(super.getServletContext())
                    .getBean(DaoRepository.class);
        }
        return daoRepository;
    }
}
