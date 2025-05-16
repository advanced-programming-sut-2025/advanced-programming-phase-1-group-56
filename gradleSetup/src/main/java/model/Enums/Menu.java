package model.Enums;

import view.*;
import view.GameMenus.GameMenu;
import view.GameMenus.GreenHouseMenu;
import view.GameMenus.HouseMenu;
import view.GameMenus.ShopMenus.*;
import view.GameMenus.TradeMenu;

import java.util.Scanner;

public enum Menu {
    loginMenu(new LoginMenu()),
    mainMenu(new MainMenu()),
    profileMenu(new ProfileMenu()),
    avatarMenu(new AvatarMenu()),
    gameMenu(new GameMenu()),
    HouseMenu(new HouseMenu()),
    GreenHouseMenu(new GreenHouseMenu()),
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
    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }
}
