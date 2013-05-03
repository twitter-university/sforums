
package com.marakana.sforums.web;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marakana.sforums.dao.DaoRepository;
import com.marakana.sforums.dao.DataAccessException;

public abstract class AbstractDaoAccessServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static DaoRepository daoRepository;

    protected final DaoRepository getDaoRepository() throws DataAccessException {
        synchronized (AbstractDaoAccessServlet.class) {
            if (daoRepository == null) {
                String daoRepositoryClassName = super.getServletContext().getInitParameter(
                        "daoRepositoryClassName");
                try {
                    Class<?> daoRepositoryClass = Class.forName(daoRepositoryClassName);
                    daoRepository = (DaoRepository) daoRepositoryClass.newInstance();
                    logger.debug("Initialized dao repository to {}", daoRepositoryClassName);
                } catch (Exception e) {
                    throw new DataAccessException(
                            "Failed to initialize dao repository from init parameter [daoClassName]=["
                                    + daoRepositoryClassName + "]", e);
                }
            }
            return daoRepository;
        }
    }

    protected boolean isEmpty(String s) {
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }
}
