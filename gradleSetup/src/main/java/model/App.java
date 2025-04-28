package model;
import java.util.ArrayList;

import model.Enums.Menu;

public class App {
    //TODO
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser = null;
    private static Menu currentMenu = Menu.loginMenu;



    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        App.users = users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }

}
