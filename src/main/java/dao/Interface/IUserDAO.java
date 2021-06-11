package dao.Interface;

import dao.DAOException;
import dao.IDaoCrud;
import doMain.User;

public interface IUserDAO extends IDaoCrud<User> {

    User readByEmail(String email) throws DAOException;

    boolean updateByEmail(User t) throws DAOException;
}
