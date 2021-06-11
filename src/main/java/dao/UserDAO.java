package dao;

import java.util.ArrayList;
import java.util.List;

import dao.Interface.IUserDAO;
import doMain.User;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UserDAO implements IUserDAO {

    private EntityManager entityManager = DaoConnection.getEntityManager();

    private Logger logger = Logger.getLogger(UserDAO.class);

    @Override
    public User insert(User user) throws DAOException {
        logger.info("Creating new user ...");

        try {
            logger.trace("Stating transaction...");
            entityManager.getTransaction().begin();
            logger.trace("Persisting entity...");
            entityManager.persist(user);
            logger.trace("Committing transaction to database...");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Creating user failed!");
            throw new DAOException("Creating user failed!", e);
        }

        logger.trace("Returning User...");
        logger.info(user + " is added to database!");
        return user;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> readAll() throws DAOException {
        logger.info("Getting all users from database...");

        List<User> userList = new ArrayList<>();

        try {
            logger.trace("Finding entities...");
            Query query = entityManager.createQuery("SELECT e FROM User e");
            userList = query.getResultList();
        } catch (Exception e) {
            logger.error("Getting list of users failed!");
            throw new DAOException("Getting list of users failed!", e);
        }

        logger.trace("Returning list of users...");
        return userList;
    }

    @Override
    public User readByID(int id) throws DAOException {
        logger.info("Getting user by id from database...");

        User user = null;

        try {
            logger.trace("Finding entity...");
            user = entityManager.find(User.class, id);
        } catch (Exception e) {
            logger.error("Getting user by id failed!");
            throw new DAOException("Getting user by id failed!", e);
        }

        logger.trace("Returning User...");
        logger.info(user + " is got from database!");
        return user;
    }

    public User readByEmail(String email) throws DAOException {
        logger.info("Getting user by email ...");

        User user = null;

        try {
            logger.trace("Building query and finding entity...");
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> from = criteriaQuery.from(User.class);
            criteriaQuery.select(from);
            criteriaQuery.where(criteriaBuilder.equal(from.get("email"), email));
            TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
            user = typedQuery.getSingleResult();
        } catch (Exception e) {
            logger.error("Getting user by email failed!");
            throw new DAOException("Getting user by email failed!", e);
        }

        logger.trace("Returning User...");
        logger.info(user + " is got from database!");
        return user;
    }

    @Override
    public boolean updateByEmail(User t) throws DAOException {
        return false;
    }

    @Override
    public boolean updateByID(User user) throws DAOException {
        logger.info("Updating user by id in database...");

        boolean result = false;

        try {
            logger.trace("Opening transaction...");
            entityManager.getTransaction().begin();
            logger.trace("Updating entity...");
            entityManager.merge(user);
            logger.trace("Committing transaction to database...");
            entityManager.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            logger.error("Updating user failed!");
            throw new DAOException("Updating user failed!", e);
        }

        if (result == false) {
            logger.info("Updating user failed!");
        } else {
            logger.trace("Returning result...");
            logger.info("User with ID#" + user.getId() + " is updated in database!");
        }
        return result;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        logger.info("Deleting user by id from database...");

        boolean result = false;

        try {
            logger.info("Getting user by id from database...");
            User user = readByID(id);
            logger.trace("Opening transaction...");
            entityManager.getTransaction().begin();
            logger.trace("Removing entity...");
            entityManager.remove(user);
            logger.trace("Committing transaction to database...");
            entityManager.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            logger.error("Deleting user failed!");
            throw new DAOException("Deleting user failed!", e);
        }

        if (result == false) {
            logger.info("Deleting user failed!");
        } else {
            logger.trace("Returning result...");
            logger.info("User with ID=" + id + " is deleted from database!");
        }
        return result;
    }
}