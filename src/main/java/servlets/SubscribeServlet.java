package servlets;

import com.google.gson.Gson;
import dao.DAOException;
import doMain.Magazine;
import doMain.Subscribe;
import doMain.User;
import org.apache.log4j.Logger;
import service.IMagazineService;
import service.ISubscribeService;
import service.IUserService;
import service.impl.MagazineServiceImpl;
import service.impl.SubscribeServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/subscribe")
public class SubscribeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Logger logger = Logger.getLogger(SubscribeServlet.class);

    private ISubscribeService subscribeService = SubscribeServiceImpl.getSubscribeService();
    private IMagazineService magazineService = MagazineServiceImpl.getMagazineService();
    private IUserService userService = UserServiceImpl.getUserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.trace("Getting object fields");
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");
        User user = null;
        try {
            logger.trace("Getting user by id ...");
            user = userService.readByID(userID);
        } catch (Exception e) {
            logger.error("Getting user  failed!", e);
        }

        String magazineID = request.getParameter("magazineID");
        Magazine magazine = null;
        try {
            logger.trace("Getting magazine by id ...");
            magazine = magazineService.readByID(Integer.parseInt(magazineID));
        } catch (Exception e) {
            logger.error("Getting magazine  failed!", e);
        }

        String subscribePeriod = request.getParameter("subscribePeriod");

        if (user == null || magazine == null) {
            logger.warn("There is no user with id=" + userID + " or magazine with id=" + magazineID + " in database!");
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("There is no user with id=" + userID + " or magazine with id=" + magazineID + " in database!");
        } else {
            Subscribe subscribe = new Subscribe(user, magazine, false, LocalDate.now(), Integer.parseInt(subscribePeriod));

            try {
                logger.trace("Saving subscribe in database...");
                subscribeService.insert(subscribe);
            } catch (Exception e) {
                logger.error("Creating subscribe failed!", e);
            }

            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Success");
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        Subscribe subscribe = null;
        boolean subscribeUpdate = false;
        boolean subscribeStatus = false;

        try {
            logger.trace("Getting subscribe by id ...");
            subscribe = subscribeService.readByID(Integer.parseInt(id));
        } catch (Exception e) {
            logger.error("Getting subscribe failed!", e);
        }

        if (subscribe == null) {
            logger.warn("There is no subscribe with id=" + id + " in database!");
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("There is no subscribe with id=" + id + " in database!");
        } else {
            try {
                logger.info("Updating subscribe by id ...");
                if (subscribe.getSubscribeStatus()) {
                    subscribeStatus = false;
                } else {
                    subscribeStatus = true;
                }

                subscribeUpdate = subscribeService.updateByID(new Subscribe(subscribe.getId(), subscribe.getUser(),
                        subscribe.getMagazine(), subscribeStatus, subscribe.getSubscribeDate(), subscribe.getSubscribePeriod()));
            } catch (DAOException e) {
                logger.error("Updating subscribe failed!", e);
            }

            if (subscribeUpdate) {
                String json = new Gson().toJson(subscribeStatus);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            }
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        try {
            logger.trace("Deleting subscribe item ...");
            subscribeService.delete(Integer.parseInt(id));
        } catch (NumberFormatException | DAOException e) {
            logger.error("Deleting subscribe failed!", e);
        }

        response.setContentType("text");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Success");
    }

}
