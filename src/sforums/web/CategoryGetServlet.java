package sforums.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sforums.domain.Category;

public class CategoryGetServlet extends AbstractDaoAccessServlet {

    private static final long serialVersionUID = -8439064187144571214L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Category category = null;
        String key = null;
        if ((key = req.getParameter("id")) != null) {
            category = super.getDaoRepository().getCategoryDao().getById(
                    new Long(key));
        } else if ((key = req.getParameter("name")) != null) {
            category = super.getDaoRepository().getCategoryDao().getByName(key);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Specify either 'id' or 'name' request parameter");
            return;
        }
        if (category == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "No such category: " + key);
        } else {
            req.setAttribute("category", category);
            String view = req.getRequestURI().contains("edit") ? "/categoryForm.jsp"
                    : "/categoryView.jsp";
            req.getRequestDispatcher(view).forward(req, resp);
        }
    }
}
