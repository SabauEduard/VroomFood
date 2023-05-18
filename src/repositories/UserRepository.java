package repositories;

import models.User;

import java.util.Comparator;
import java.util.List;

public class UserRepository extends GenericRepository<User>{

    public UserRepository(List<User> objectList) {
        super(objectList);
    }
    public UserRepository() {
        super();
    }
    public void sortUsersByName(){
        objectList.sort(Comparator.comparing(User::getName));
    }
    public User getUserByUsername(String username){
        for(User user : objectList){
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }
    public User getUserByEmail(String email){
        for(User user : objectList){
            if(user.getEmail().equals(email))
                return user;
        }
        return null;
    }
    public void printUsers(){
        for(User user : objectList){
            System.out.println(user);
        }
    }
}
