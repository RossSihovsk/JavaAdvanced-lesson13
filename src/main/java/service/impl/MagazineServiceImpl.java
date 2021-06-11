package service.impl;

import dao.DAOException;
import dao.Interface.IMagazineDAO;
import dao.MagazineDAO;
import doMain.Magazine;
import service.IMagazineService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MagazineServiceImpl implements IMagazineService {

    private static IMagazineService magazineService;
    private IMagazineDAO magazineDAO;

    private MagazineServiceImpl() {
        this.magazineDAO = new MagazineDAO();
    }


    public static IMagazineService getMagazineService() {
        if (magazineService == null) {
            magazineService = new MagazineServiceImpl();
        }

        return magazineService;
    }

    @Override
    public Magazine insert(Magazine magazine) throws Exception {
        return magazineDAO.insert(magazine);
    }

    @Override
    public List<Magazine> readAll() throws DAOException {
        return magazineDAO.readAll();
    }

    @Override
    public Magazine readByID(int id) throws Exception {
        return magazineDAO.readByID(id);
    }

    @Override
    public boolean updateByID(Magazine magazine) throws DAOException {
        return magazineDAO.updateByID(magazine);
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return magazineDAO.delete(id);
    }

    @Override
    public Map<Integer, Magazine> readAllMap() throws DAOException {
        List<Magazine> magazines = readAll();
        return magazines.stream().collect(Collectors.toMap(Magazine::getId, Function.identity()));
    }
}
