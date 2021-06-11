package servlets;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loggingOut")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = Logger.getLogger(LogoutServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.trace("Logging out...");
        HttpSession session = request.getSession();

        logger.trace("Invalidating current session...");
        if (session != null) {
            session.invalidate();
        }

        logger.trace("Redirecting to Login page...");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("login.jsp");
    }

}
