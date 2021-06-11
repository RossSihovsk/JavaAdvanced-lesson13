package service;

import dao.DAOException;
import dao.IDaoCrud;
import doMain.Magazine;

import java.util.Map;

public interface IMagazineService  extends IDaoCrud<Magazine> {
    public Map<Integer, Magazine> readAllMap() throws DAOException;
}
