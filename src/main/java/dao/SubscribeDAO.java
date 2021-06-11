package dao;


import java.util.ArrayList;
import java.util.List;

import dao.Interface.ISubscribeDAO;
import doMain.Subscribe;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SubscribeDAO  implements ISubscribeDAO {

    private Logger logger = Logger.getLogger(SubscribeDAO.class);

    private EntityManager entityManager = DaoConnection.getEntityManager();

    @Override
    public Subscribe insert(Subscribe subscribe) throws DAOException {
        logger.info("Creating new subscribe in database...");

        try {
            logger.trace("Opening transaction...");
            entityManager.getTransaction().begin();
            logger.trace("Persisting entity...");
            entityManager.persist(subscribe);
            logger.trace("Committing transaction to database...");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Creating subscribe failed!");
            throw new DAOException("Creating subscribe failed!", e);
        }

        logger.trace("Returning Subscribe...");
        logger.info(subscribe + " is added to database!");
        return subscribe;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Subscribe> readAll() throws DAOException {
        logger.info("Getting all subscribes from database...");

        List<Subscribe> subscribeList = new ArrayList<>();

        try {
            logger.trace("Finding entities...");
            Query query = entityManager.createQuery("SELECT e FROM Subscribe e");
            subscribeList = query.getResultList();
        } catch (Exception e) {
            logger.error("Getting list of subscribes failed!");
            throw new DAOException("Getting list of subscribes failed!", e);
        }

        logger.trace("Returning list of subscribes...");
        return subscribeList;
    }

    @Override
    public Subscribe readByID(int id) throws DAOException {
        logger.info("Getting subscribe by id from database...");

        Subscribe subscribe = null;

        try {
            logger.trace("Finding entity...");
            subscribe = entityManager.find(Subscribe.class, id);
        } catch (Exception e) {
            logger.error("Getting subscribe by id failed!");
            throw new DAOException("Getting subscribe by id failed!", e);
        }

        logger.trace("Returning Subscribe...");
        logger.info(subscribe + " is got from database!");
        return subscribe;
    }

    @Override
    public boolean updateByID(Subscribe subscribe) throws DAOException {
        logger.info("Updating subscribe by id in database...");

        boolean result = false;

        try {
            logger.trace("Opening transaction...");
            entityManager.getTransaction().begin();
            logger.trace("Updating entity...");
            entityManager.merge(subscribe);
            logger.trace("Commiting transaction to database...");
            entityManager.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            logger.error("Updating subscribe failed!");
            throw new DAOException("Updating subscribe failed!", e);
        }

        if (result == false) {
            logger.info("Updating subscribe failed!");
        } else {
            logger.trace("Returning result...");
            logger.info("Subscribe with ID#" + subscribe.getId() + " is updated in database!");
        }
        return result;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        logger.info("Deleting subscribe by id from database...");

        boolean result = false;

        try {
            logger.info("Getting user by id from database...");
            Subscribe subscribe = readByID(id);
            logger.trace("Opening transaction...");
            entityManager.getTransaction().begin();
            logger.trace("Removing entity...");
            entityManager.remove(subscribe);
            logger.trace("Commiting transaction to database...");
            entityManager.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            logger.error("Deleting subscribe failed!");
            throw new DAOException("Deleting subscribe failed!", e);
        }

        if (result == false) {
            logger.info("Deleting subscribe failed, no rows affected!");
        } else {
            logger.trace("Returning result...");
            logger.info("Subscribe with ID#" + id + " is deleted from database!");
        }
        return result;
    }


}
