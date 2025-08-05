package io.src.model;

import java.io.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.src.StardewValley;
import io.src.model.Enums.Menu;

public class App {
    private static final String FILE_PATH = "assets//users.json";
    //TODO
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser = null;
    private static Menu currentMenu = Menu.loginMenu;
    private static StardewValley stardewValley;

    public static void init() {
        users = getUsers();
    }

    public static ArrayList<User> getUsers() {
        Gson gson = new Gson();
        ArrayList<User> tmpUsers = new ArrayList<>();
        try (Reader reader = new FileReader(FILE_PATH)) {
            tmpUsers = gson.fromJson(reader, new TypeToken<ArrayList<User>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            tmpUsers = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpUsers;
    }

    public static void addUser(User user) {
        Gson gson = new Gson();
        ArrayList<User> tmpUsers = new ArrayList<>();
        try (Reader reader = new FileReader(FILE_PATH)) {
            tmpUsers = gson.fromJson(reader, new TypeToken<ArrayList<User>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            tmpUsers = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tmpUsers.add(user);
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tmpUsers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static User getUserByUsername(String username) {
        for (User user : users)
            if (user.getUsername().equals(username))
                return user;
        for (User user : getUsers())
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    public static Player getMe() {
        return App.getCurrentUser().getCurrentGame().getCurrentPlayer();
    }

    public static void saveUsers() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StardewValley getStardewValley() {
        return stardewValley;
    }

    public static void setStardewValley(StardewValley stardewValley) {
        App.stardewValley = stardewValley;
    }
}
