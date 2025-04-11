package model.Enums;

import view.*;

public enum Menu {
    loginMenu(new LoginMenu()),
    mainMenu(new MainMenu()),
    profileMenu(new ProfileMenu()),
    avatarMenu(new AvatarMenu()),
    gameMenu(new GameMenu());

    private final AppMenu menu;
    Menu(AppMenu appMenu) {
        this.menu = appMenu;
    }
    public void checkCommand() {
        this.menu.check();
    }
}
