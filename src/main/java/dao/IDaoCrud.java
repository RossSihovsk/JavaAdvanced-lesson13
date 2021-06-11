package dao;

import java.util.List;

public interface IDaoCrud<T> {
    T insert(T t) throws Exception;

    List<T> readAll() throws DAOException;

    T readByID(int id) throws Exception;

    boolean updateByID(T t) throws DAOException;

    boolean delete(int id) throws DAOException;
}
