package model;

import java.util.ArrayList;

public class User {
    private String userName;
    private String nickName;
    private String password;
    private String email;
    private ArrayList<Game> game;

    User(String userName, String nickName, String password, String email) {
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public ArrayList<Game> getGame() {
        return game;
    }

    public void addGame(Game game) {
        this.game.add(game);
    }
}
