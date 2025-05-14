package model.Enums;

import view.*;
import view.GameMenus.ShopMenus.*;

import java.util.Scanner;

public enum Menu {
    loginMenu(new LoginMenu()),
    mainMenu(new MainMenu()),
    profileMenu(new ProfileMenu()),
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
    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }
}
