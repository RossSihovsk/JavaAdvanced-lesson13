package service.impl;

import dao.DAOException;
import dao.Interface.IUserDAO;
import dao.UserDAO;
import doMain.User;
import service.IUserService;

import java.util.List;

public class UserServiceImpl implements IUserService {

    private static IUserService userService;
    private IUserDAO userDAO;

    private UserServiceImpl() {
        this.userDAO = new UserDAO();
    }

    public static IUserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }

        return userService;
    }


    @Override
    public User readByEmail(String email) throws DAOException {
        return userDAO.readByEmail(email);
    }

    @Override
    public boolean updateByEmail(User t) throws DAOException {
        return userDAO.updateByEmail(t);
    }

    @Override
    public User insert(User user) throws Exception {
        return userDAO.insert(user);
    }

    @Override
    public List<User> readAll() throws DAOException {
        return userDAO.readAll();
    }

    @Override
    public User readByID(int id) throws Exception {
        return userDAO.readByID(id);
    }

    @Override
    public boolean updateByID(User user) throws DAOException {
        return userDAO.updateByID(user);
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return userDAO.delete(id);
    }
}
