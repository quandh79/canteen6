package dao.interfaces;

import java.util.ArrayList;

public interface IRepository<S> {
    ArrayList<S> all();

    S create();

    boolean update(S s);

    boolean delete(S s);

    S findOne(Integer id);

}
