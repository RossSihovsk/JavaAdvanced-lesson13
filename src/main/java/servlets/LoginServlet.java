
package servlets;


import com.google.gson.Gson;
import dao.DAOException;
import doMain.User;
import dto.UserLogin;
import org.apache.log4j.Logger;
import service.IUserService;
import service.impl.UserServiceImpl;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = Logger.getLogger(LoginServlet.class);

    private IUserService userService = UserServiceImpl.getUserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.trace("Opening Login Form...");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.trace("Getting fields values from Login Form...");
        request.setCharacterEncoding("UTF-8");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = null;

        try {
            logger.trace("Getting user from database...");
            user = userService.readByEmail(login);
        } catch (DAOException e) {
            logger.error("Getting user by email failed!", e);
        }

        if (user == null) {
            logger.warn("There is no user with login \"" + login + "\" in database!");
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Користувача з логіном \"" + login + "\" не знайдено");
        } else {
            logger.trace("Checking user's password for matching database...");
            if (user.getPassword().equals(password)) {
                logger.trace("Keeping user's ID in opened session...");
                HttpSession session = request.getSession(true);
                session.setAttribute("userID", user.getId());
                session.setAttribute("firstName", user.getFirstName());
                session.setAttribute("lastName", user.getLastName());
                session.setAttribute("accessLevel", user.getAccessLevel());

                logger.trace("Redirecting to User's account page...");
                UserLogin userLogin = new UserLogin(user.getEmail(), "cabinet.jsp");
                String json = new Gson().toJson(userLogin);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } else {
                logger.warn("User's password doesn't match database!");
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("Хибний пароль");
            }
        }

    }

}
