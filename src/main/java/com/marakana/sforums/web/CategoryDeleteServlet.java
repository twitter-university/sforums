
package com.marakana.sforums.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.marakana.sforums.domain.Category;

public class CategoryDeleteServlet extends AbstractDaoAccessServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Specify 'id' request parameter");
        } else {
            Category category = super.getDaoRepository().getCategoryDao().getById(new Long(id));
            if (category == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                super.getDaoRepository().getCategoryDao().delete(category);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
    }
}
