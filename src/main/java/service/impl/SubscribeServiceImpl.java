package service.impl;

import dao.DAOException;
import dao.Interface.ISubscribeDAO;
import dao.SubscribeDAO;
import doMain.Subscribe;
import service.ISubscribeService;

import java.util.List;

public class SubscribeServiceImpl implements ISubscribeService{

    private static ISubscribeService subscribeService;
    private ISubscribeDAO subscribeDAO;

    private SubscribeServiceImpl() {
        this.subscribeDAO = new SubscribeDAO();
    }

    public static ISubscribeService getSubscribeService() {
        if (subscribeService == null) {
            subscribeService = new SubscribeServiceImpl();
        }

        return subscribeService;
    }
    @Override
    public Subscribe insert(Subscribe subscribe) throws Exception {
        return subscribeDAO.insert(subscribe);
    }

    @Override
    public List<Subscribe> readAll() throws DAOException {
        return subscribeDAO.readAll();
    }

    @Override
    public Subscribe readByID(int id) throws Exception {
        return subscribeDAO.readByID(id);
    }

    @Override
    public boolean updateByID(Subscribe subscribe) throws DAOException {
        return subscribeDAO.updateByID(subscribe);
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return subscribeDAO.delete(id);
    }
}
