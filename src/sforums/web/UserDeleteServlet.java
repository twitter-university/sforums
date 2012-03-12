package sforums.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserDeleteServlet extends AbstractDaoAccessServlet {

    private static final long serialVersionUID = 5872143094158479965L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Specify 'id' request parameter");
        } else {
            super.getDaoRepository().getUserDao().deleteById(new Long(id));
        }
        resp.sendRedirect(resp.encodeRedirectURL("users.html"));
    }
}
