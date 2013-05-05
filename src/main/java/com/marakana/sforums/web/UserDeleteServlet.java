
package com.marakana.sforums.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.marakana.sforums.domain.User;

public class UserDeleteServlet extends AbstractDaoAccessServlet {

    private static final long serialVersionUID = 5872143094158479965L;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Specify 'id' request parameter");
        } else {
            User user = new User();
            user.setId(new Long(id));
            super.getDaoRepository().getUserDao().delete(user);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
