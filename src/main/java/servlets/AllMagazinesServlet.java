package servlets;


import com.google.gson.Gson;
import dao.DAOException;
import doMain.Magazine;
import org.apache.log4j.Logger;
import service.IMagazineService;
import service.impl.MagazineServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/magazines")
public class AllMagazinesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Logger logger = Logger.getLogger(AllMagazinesServlet.class);

    private IMagazineService magazineService = MagazineServiceImpl.getMagazineService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Magazine> magazines = null;

        try {
            magazines = magazineService.readAll();
        } catch (DAOException e) {
            logger.error("Getting list of magazines failed!", e);
        }

        if (magazines == null) {
            logger.warn("There is no any magazine in database registered!");
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("В базі данних не знайдено жодного журналу!");
        } else {
            String json = new Gson().toJson(magazines);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }
}
