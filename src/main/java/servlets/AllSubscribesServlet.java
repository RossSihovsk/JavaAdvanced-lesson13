package servlets;

import com.google.gson.Gson;
import dao.DAOException;
import doMain.Magazine;
import doMain.Subscribe;
import dto.SubscribesDTO;
import org.apache.log4j.Logger;
import service.IMagazineService;
import service.ISubscribeService;
import service.impl.MagazineServiceImpl;
import service.impl.SubscribeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/subscribes")
public class AllSubscribesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Logger logger = Logger.getLogger(AllSubscribesServlet.class);

    private IMagazineService magazineService = MagazineServiceImpl.getMagazineService();
    private ISubscribeService subscribeService = SubscribeServiceImpl.getSubscribeService();

    public List<SubscribesDTO> map(List<Subscribe> subscribes, Map<Integer, Magazine> magazinesMap) {

        return subscribes.stream().map(value -> {
            SubscribesDTO subscribesDTO = new SubscribesDTO();

            subscribesDTO.id = value.getId();
            subscribesDTO.title = magazinesMap.get(value.getMagazine().getId()).getTitle();
            subscribesDTO.description = magazinesMap.get(value.getMagazine().getId()).getDescription();
            subscribesDTO.publishDate = magazinesMap.get(value.getMagazine().getId()).getDate();
            subscribesDTO.subscribePrice = magazinesMap.get(value.getMagazine().getId()).getPriceofSubscribe();
            subscribesDTO.subscribeStatus = value.getSubscribeStatus();
            subscribesDTO.subscribeDate = value.getSubscribeDate();
            subscribesDTO.subscribePeriod = value.getSubscribePeriod();

            return subscribesDTO;
        }).collect(Collectors.toList());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Subscribe> subscribes = null;

        try {
            logger.trace("Getting list of subscribes from database...");
            subscribes = subscribeService.readAll();
        } catch (DAOException e) {
            logger.error("Getting list of subscribes failed!", e);
        }

        Map<Integer, Magazine> magazinesMap = null;

        try {
            logger.trace("Getting map of magazines from database...");
            magazinesMap =  magazineService.readAllMap();
        } catch (DAOException e) {
            logger.error("Getting map of magazines failed!", e);
        }

        List<SubscribesDTO> subscribesDTO = map(subscribes, magazinesMap);

        if (subscribes == null || magazinesMap == null) {
            logger.warn("There is no any magazine or subscribe in database registered!");
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Немає жодної підписки :(");
        } else {
            logger.trace("Returning list of subscribes DTO...");
            String json = new Gson().toJson(subscribesDTO);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }


}
