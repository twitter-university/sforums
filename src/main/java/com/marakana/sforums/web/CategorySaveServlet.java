
package com.marakana.sforums.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.marakana.sforums.dao.DuplicateIdException;
import com.marakana.sforums.domain.Category;

public class CategorySaveServlet extends AbstractDaoAccessServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Category category = new Category();
        req.setAttribute("category", category);
        String id = req.getParameter("id");
        if (!isEmpty(id)) {
            category.setId(new Long(id));
        }
        category.setDescription(req.getParameter("description"));
        String name = req.getParameter("name");
        if (isEmpty(name)) {
            super.logger.debug("Not saving category. Name is blank");
            req.setAttribute("error", "Name cannot be blank");
        } else {
            category.setName(name);
            super.logger.debug("Saving {}", category);
            try {
                super.getDaoRepository().getCategoryDao().save(category);
                req.setAttribute("success", Boolean.TRUE);
            } catch (DuplicateIdException e) {
                req.setAttribute("error", "Duplicate name: " + e.getMessage());
            }
        }
        req.getRequestDispatcher("/WEB-INF/jsp/categoryForm.jsp").forward(req, resp);
    }
}
