package Repositories;

import java.util.ArrayList;
import java.util.List;

public class GenericRepository<T> implements IGenericRepository<T>{
    protected List<T> objectList = new ArrayList<T>();

    public List<T> getAll() {
        return objectList;
    }
    public void add(T object) {
        if(objectList == null)
            objectList = new ArrayList<>();
        objectList.add(object);
    }
    public void delete(T object) {
        if(objectList == null)
            return;
        objectList.remove(object);
    }
    public void update(T oldObject, T newObject) {
        if(objectList == null)
            return;
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
