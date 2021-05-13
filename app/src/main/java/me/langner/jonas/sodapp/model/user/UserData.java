package me.langner.jonas.sodapp.model.user;

import java.util.HashMap;
import java.util.Map;

/**
 * Speichert die Nutzerdaten an einem zentralen Ort ab.
 * @author Jonas Langner
 * @version 0.1.1
 * @since 0.1.1
 */
public class UserData {

    private String userName, password, group, error;
    private boolean loggedIn = false;

    public static final UserData USER_DATA = new UserData();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, String> getURLMap() {
        Map<String, String> map = new HashMap<>();
        map.put("name", userName);
        map.put("password", password);

        return map;
    }
}
