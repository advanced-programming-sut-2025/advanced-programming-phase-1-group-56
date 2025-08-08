package io.src.model.Enums;

import io.src.controller.MenuController.ProfileMenuController;
import io.src.model.Result;
import io.src.view.*;
import io.src.view.GameMenus.GameMenu;
import io.src.view.GameMenus.HouseMenu;
import io.src.view.GameMenus.ShopMenus.*;
import io.src.view.GameMenus.TradeMenu;

import java.util.Scanner;

public enum Menu {
    loginMenu(new LoginMenu()),
    mainMenu(new MainMenu()),
    profileMenu(new ProfileMenu(new ProfileMenuController())),
    avatarMenu(new AvatarMenu()),
    gameMenu(new GameMenu()),
    HouseMenu(new HouseMenu()),
    exitMenu(new ExitMenu()),
    TradeMenu(new TradeMenu()),
    CarpenterShopMenu(new CarpentersShopMenu()),
    TheSaloonStarDropMenu(new TheSaloonStarDropMenu()),
    PierresGeneralStoreMenu(new PierresGeneralStoreMenu()),
    MarniesRanchMenu(new MarniesRanchMenu()),
    JojaMartMenu(new JojaMartMenu()),
    BlackSmithMenu(new BlacksmithMenu()),
    FishShopMenu(new FishShopMenu());


    private final AppMenu menu;

    Menu(AppMenu appMenu) {
        this.menu = appMenu;
    }

    public Result checkCommand(Scanner scanner, String cmd) {
        return this.menu.check(scanner, cmd);
    }

    public AppMenu getMenu() {
        return menu;
    }
}
