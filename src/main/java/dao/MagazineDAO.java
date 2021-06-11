package dao;

import dao.Interface.IMagazineDAO;
import doMain.Magazine;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class MagazineDAO implements IMagazineDAO {

    private EntityManager entityManager = DaoConnection.getEntityManager();

    private Logger logger = Logger.getLogger(MagazineDAO.class);

    @Override
    public Magazine insert(Magazine magazine) throws DAOException {
        logger.info("Creating new magazine in database...");

        try {
            logger.trace("Opening transaction...");
            entityManager.getTransaction().begin();
            logger.trace("Persisting entity...");
            entityManager.persist(magazine);
            logger.trace("Committing transaction to database...");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Creating magazine failed!");
            throw new DAOException("Creating magazine failed!", e);
        }

        logger.trace("Returning Magazine...");
        logger.info(magazine + " is added to database!");
        return magazine;
    }


    @Override
    public List<Magazine> readAll() throws DAOException {
        logger.info("Getting all magazines from database...");

        List<Magazine> magazineList = new ArrayList<>();

        try {
            logger.trace("Finding entities...");
            Query query = entityManager.createQuery("SELECT e FROM Magazine e");
            magazineList = query.getResultList();
        } catch (Exception e) {
            logger.error("Getting list of magazines failed!");
            throw new DAOException("Getting list of magazines failed!", e);
        }

        logger.trace("Returning list of magazines...");
        return magazineList;
    }

    @Override
    public Magazine readByID(int id) throws DAOException {
        logger.info("Getting magazine by id from database...");

        Magazine magazine = null;

        try {
            logger.trace("Finding entity...");
            magazine = entityManager.find(Magazine.class, id);
        } catch (Exception e) {
            logger.error("Getting magazine by id failed!");
            throw new DAOException("Getting magazine by id failed!", e);
        }

        logger.trace("Returning Magazine...");
        logger.info(magazine + " is got from database!");
        return magazine;
    }

    @Override
    public boolean updateByID(Magazine magazine) throws DAOException {
        logger.info("Updating magazine by id in database...");

        boolean result = false;

        try {
            logger.trace("Opening transaction...");
            entityManager.getTransaction().begin();
            logger.trace("Updating entity...");
            entityManager.merge(magazine);
            logger.trace("Committing transaction to database...");
            entityManager.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            logger.error("Updating magazine failed!");
            throw new DAOException("Updating magazine failed!", e);
        }

        if (result == false) {
            logger.info("Updating magazine failed!");
        } else {
            logger.trace("Returning result...");
            logger.info("Magazine with ID#" + magazine.getId() + " is updated in database!");
        }
        return result;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        logger.info("Deleting magazine by id from database...");

        boolean result = false;

        try {
            logger.info("Getting user by id from database...");
            Magazine magazine = readByID(id);
            logger.trace("Opening transaction...");
            entityManager.getTransaction().begin();
            logger.trace("Removing entity...");
            entityManager.remove(magazine);
            logger.trace("Committing transaction to database...");
            entityManager.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            logger.error("Deleting magazine failed!");
            throw new DAOException("Deleting magazine failed!", e);
        }

        if (result == false) {
            logger.info("Deleting magazine failed!");
        } else {
            logger.trace("Returning result...");
            logger.info("Magazine with ID#" + id + " is deleted from database!");
        }
        return result;
    }

}
