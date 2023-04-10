package Repositories;

import Models.User;

import java.util.List;

public class UserRepository extends GenericRepository<User>{

    public UserRepository(List<User> objectList) {
        super(objectList);
    }
    public UserRepository() {
        super();
    }
    public User getUserByUsername(String username){
        for(User user : objectList){
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }
}
