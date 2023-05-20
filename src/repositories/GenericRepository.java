package repositories;

import config.DatabaseConfiguration;

import java.util.ArrayList;
import java.util.List;

public class GenericRepository<T> implements IGenericRepository<T>{
    protected List<T> objectList = new ArrayList<T>();
    protected final DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();

    public List<T> getAll() {
        return objectList;
    }
    public void add(T object) {
        objectList.add(object);
    }
    public void remove(T object) {
        objectList.remove(object);
    }
    public void update(T oldObject, T newObject) {
        int index = objectList.indexOf(oldObject);
        objectList.set(index, newObject);
    }
    public GenericRepository(List<T> objectList) {
        this.objectList = objectList;
    }
    public GenericRepository() {}

    public List<T> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<T> objectList) {
        this.objectList = objectList;
    }
}
