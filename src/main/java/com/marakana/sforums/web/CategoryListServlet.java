
package com.marakana.sforums.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.marakana.sforums.domain.Category;

public class CategoryListServlet extends AbstractDaoAccessServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        List<Category> categoryList = super.getDaoRepository().getCategoryDao().getAll();
        req.setAttribute("categoryList", categoryList);
        req.getRequestDispatcher("/WEB-INF/jsp/categoryList.jsp").forward(req, resp);
    }
}
