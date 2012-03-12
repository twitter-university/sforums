package sforums.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sforums.domain.Category;

public class CategoryListServlet extends AbstractDaoAccessServlet {

    private static final long serialVersionUID = 4413910671201419140L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Category> categoryList = super.getDaoRepository().getCategoryDao().getAll();
        req.setAttribute("categoryList", categoryList);
        req.getRequestDispatcher("categoryList.jsp").forward(req, resp);
    }
}
