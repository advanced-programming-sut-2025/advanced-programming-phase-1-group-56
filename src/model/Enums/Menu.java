package model.Enums;

import view.AppMenu;
import view.LoginMenu;

public enum Menu {
    LoginMenu(new LoginMenu());

    private final AppMenu menu;
    Menu(AppMenu appMenu) {
        this.menu = appMenu;
    }
    public void checkCommand() {
        this.menu.check();
    }
}
