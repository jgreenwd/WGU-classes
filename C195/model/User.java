/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private final   SimpleIntegerProperty       userId = new SimpleIntegerProperty();
    private final   SimpleStringProperty        userName = new SimpleStringProperty();
    private final   SimpleStringProperty        password = new SimpleStringProperty();
    private final   SimpleIntegerProperty       active = new SimpleIntegerProperty();
    
    public int      getUserID()                 { return userId.get(); }
    public void     setUserID(int ID)           { userId.set(ID); }
    public String   getUsername()               { return userName.get(); }
    public void     setUsername(String name)    { userName.set(name); }
    public String   getPassword()               { return password.get(); }
    public void     setPassword(String pwd)     { password.set(pwd); }
    
    public User() {}
    
    public User(int ID, String usr, String pwd, int active) {
        this.userId.set(ID);
        this.userName.set(usr);
        this.password.set(pwd);
        this.active.set(active);
    }
}
