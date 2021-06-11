package service;

import dao.DAOException;
import dao.IDaoCrud;
import doMain.User;

public interface IUserService extends IDaoCrud<User> {

    User readByEmail(String email) throws DAOException;

    boolean updateByEmail(User t) throws DAOException;
}
