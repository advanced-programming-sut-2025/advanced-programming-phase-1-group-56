package io.src.controller;

import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Result;

public class CommandController {

    public static Result goToMenu(String name) {
        if (App.getCurrentMenu().equals(Menu.gameMenu)) {
            if (name.equals("game menu")) {
                return new Result(true, "You already in it!");
            } else {
                return new Result(false, "you can't go there!");
            }
        } else if (App.getCurrentMenu().equals(Menu.loginMenu)) {
            if (name.equals("login menu")) {
                return new Result(true, "You already in it!");
            } else if (name.equals("main menu")) {
                if(App.getCurrentUser() == null){
                    return new Result(false, "You are not logged in!");
                }
                App.setCurrentMenu(Menu.mainMenu);
                return new Result(true, "you are in the main Menu now!");
            } else {
                return new Result(false, "you can't go there!");
            }
        } else if (App.getCurrentMenu().equals(Menu.mainMenu)) {
            if (name.equals("main menu")) {
                return new Result(true, "You already in it!");
            } else if (name.equals("game menu")) {
                App.setCurrentMenu(Menu.gameMenu);
                return new Result(true, "you are in the game Menu now!");
            } else if (name.equals("profile menu")) {
                App.setCurrentMenu(Menu.profileMenu);
                return new Result(true, "you are in the profile Menu now!");
//            } else if (name.equals("avatar menu")) {
//                App.setCurrentMenu(Menu.avatarMenu);
//                return new Result(true, "you are in the avatar Menu now!");
            } else {
                return new Result(false, "you can't go there!");
            }
        } else if (App.getCurrentMenu().equals(Menu.profileMenu)) {
            if (name.equals("profile menu")) {
                return new Result(true, "You already in it!");
            } else {
                return new Result(false, "you can't go there!");
            }
        }
        return new Result(false, "oh shittt , here we go again!");
    }

}
