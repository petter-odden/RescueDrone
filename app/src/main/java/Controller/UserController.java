package Controller;

import java.util.HashMap;

import Model.Person;

public class UserController {
    private HashMap<String, Person> userList;


    public HashMap<String, Person> getUserList() {
        return userList;
    }

    public void setUserList(HashMap<String, Person> userList) {
        this.userList = userList;
    }

    public UserController(HashMap<String, Person> userList) {
        this.userList = userList;
    }

    private boolean confirmPilot(String UID){
        try {
            userList.get(UID).setConfirmedPilot(true);
            //Code to  update the database
            return true;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return false;
        }
    }
    private boolean deConfirmPilot(String UID){
        try {
            userList.get(UID).setConfirmedPilot(false);
            //Code to  update the database
            return true;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return false;
        }
    }
    private boolean createUser(){
        try {

            return true;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return false;
        }
    }
}
