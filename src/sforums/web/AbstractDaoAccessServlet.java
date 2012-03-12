package sforums.web;

import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sforums.dao.DaoRepository;
import sforums.dao.DataAccessException;

public abstract class AbstractDaoAccessServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected final Log logger = LogFactory.getLog(this.getClass());

    protected static DaoRepository daoRepository;

    protected final DaoRepository getDaoRepository() throws DataAccessException {
        synchronized (AbstractDaoAccessServlet.class) {
            if (daoRepository == null) {
                String daoRepositoryClassName = super.getServletContext().getInitParameter(
                        "daoRepositoryClassName");
                try {
                    Class<?> daoRepositoryClass = Class.forName(daoRepositoryClassName);
                    daoRepository = (DaoRepository) daoRepositoryClass.newInstance();
                    if (logger.isDebugEnabled()) {
                        logger.debug("Initialized dao repository to: "
                                + daoRepositoryClassName);
                    }
                } catch (Exception e) {
                    throw new DataAccessException(
                            "Failed to initialize dao repository from init parameter [daoClassName]=["
                                    + daoRepositoryClassName + "]", e);
                }
            }
            return daoRepository;
        }
    }
}
