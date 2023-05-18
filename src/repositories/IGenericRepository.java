package repositories;

import java.util.List;

public interface IGenericRepository<T> {
    public void add(T object);
    public void update(T oldObject, T newObject);
    public void remove(T object);
    public List<T> getAll();
}
