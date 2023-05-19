package models;

import java.sql.ResultSet;

public abstract class User {
    private static int idCounter = 0;
    protected int id;
    protected String name;
    protected String username;
    protected String password;
    protected String email;
    protected String phoneNumber;
    protected String address;

    public User(String name, String username, String password, String email, String phoneNumber, String address) {
        this.id = idCounter++;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    public User(ResultSet result){
        try{
            this.id = result.getInt("id");
            this.name = result.getString("name");
            this.username = result.getString("username");
            this.password = result.getString("password");
            this.email = result.getString("email");
            this.phoneNumber = result.getString("phoneNumber");
            this.address = result.getString("address");
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        User user = (User) obj;
        return user.getId() == this.id;
    }

    public String toString(){
       return "Name: " + this.name + "\n"
                + "Username: " + this.username + "\n"
                + "Password: " + this.password + "\n"
                + "Email: " + this.email + "\n"
                + "Phone Number: " + this.phoneNumber + "\n"
                + "Address: " + this.address + "\n";
    }
}
