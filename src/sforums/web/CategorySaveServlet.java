package sforums.web;

import static sforums.Util.isEmpty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;

import sforums.domain.Category;

public class CategorySaveServlet extends AbstractDaoAccessServlet {

    private static final long serialVersionUID = -8403383569839821791L;

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
            if (super.logger.isDebugEnabled()) {
                super.logger.debug("Not saving category. Name is blank");
            }
            req.setAttribute("error", "Name cannot be blank");
            req.getRequestDispatcher("/categoryForm.jsp").forward(req, resp);
        } else {
            category.setName(name);
            if (super.logger.isDebugEnabled()) {
                super.logger.debug("Saving " + category);
            }
            try {
                super.getDaoRepository().getCategoryDao().save(category);

                req.getRequestDispatcher("/categoryView.jsp").forward(req, resp);
            } catch (DataIntegrityViolationException e) {
                req.setAttribute("error", "Duplicate name: " + e.getMessage());
                req.getRequestDispatcher("/categoryForm.jsp").forward(req, resp);
            }
        }
    }

}
