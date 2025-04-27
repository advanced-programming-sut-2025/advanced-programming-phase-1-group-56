package controller;

import model.App;
import model.Enums.Menu;
import model.Result;

public class CommandController {

    public static Result goToMenu(String name) {
        if (App.getCurrentMenu().equals(Menu.gameMenu)) {
            if (name.equals("game Menu")) {
                return new Result(true, "You already in it!");
            } else {
                return new Result(false, "you can't go there!");
            }
        } else if (App.getCurrentMenu().equals(Menu.loginMenu)) {
            if (name.equals("login Menu")) {
                return new Result(true, "You already in it!");
            } else if (name.equals("main Menu")) {
                App.setCurrentMenu(Menu.mainMenu);
                return new Result(true, "you are in the main Menu now!");
            } else {
                return new Result(false, "you can't go there!");
            }
        } else if (App.getCurrentMenu().equals(Menu.mainMenu)) {
            if (name.equals("main Menu")) {
                return new Result(true, "You already in it!");
            } else if (name.equals("game Menu")) {
                App.setCurrentMenu(Menu.gameMenu);
                return new Result(true, "you are in the game Menu now!");
            } else if (name.equals("profile Menu")) {
                App.setCurrentMenu(Menu.profileMenu);
                return new Result(true, "you are in the profile Menu now!");
            } else if (name.equals("avatar Menu")) {
                App.setCurrentMenu(Menu.avatarMenu);
                return new Result(true, "you are in the avatar Menu now!");
            } else {
                return new Result(false, "you can't go there!");
            }
        } else if (App.getCurrentMenu().equals(Menu.profileMenu)) {
            if (name.equals("profile Menu")) {
                return new Result(true, "You already in it!");
            } else {
                return new Result(false, "you can't go there!");
            }
        }
        return new Result(false, "oh shittt , here we go again!");
    }

}
