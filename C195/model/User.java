/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package model;

public class User {
    private int     userID;
    private String  userName;
    private String  password;
    
    public int getUserID()                  { return userID; }
    public String getUsername()             { return userName; }
    public String getPassword()             { return password; }
    
    public void setUserID(int ID)           { userID = ID; }
    public void setUsername(String name)    { userName = name; }
    public void setPassword(String pwd)     { password = pwd; }
}
