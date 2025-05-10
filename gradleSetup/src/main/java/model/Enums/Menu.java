package model.Enums;

import view.*;
import view.GameMenus.GameMenu;
import view.GameMenus.HouseMenu;

import java.util.Scanner;

public enum Menu {
    loginMenu(new LoginMenu()),
    mainMenu(new MainMenu()),
    profileMenu(new ProfileMenu()),
    avatarMenu(new AvatarMenu()),
    gameMenu(new GameMenu()),
    HouseMenu(new HouseMenu()),
    exitMenu(new ExitMenu()),
    TradeMenu(new TradeMenu());

    private final AppMenu menu;
    Menu(AppMenu appMenu) {
        this.menu = appMenu;
    }
    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }
}
