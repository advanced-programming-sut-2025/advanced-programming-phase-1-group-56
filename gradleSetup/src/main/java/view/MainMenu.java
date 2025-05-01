package view;

import controller.MenuController.MainMenuController;
import model.App;
import model.Enums.Menu;
import model.Enums.commands.MainMenuCommands;


import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = MainMenuCommands.ShowCurrentMenu.getMatcher(input)).find()) {
            System.out.println("you are in Main Menu BROOOOO!");
        } else if ((matcher = MainMenuCommands.goMenu.getMatcher(input)).find()) {
            String menu = matcher.group(1);
            System.out.println(MainMenuController.goToMenu(menu));
        } else if ((matcher = MainMenuCommands.logout.getMatcher(input)).find()) {
            System.out.println(MainMenuController.manageUserLogout());
        } else if ((matcher = MainMenuCommands.back.getMatcher(input)).find()) {
            App.setCurrentMenu(Menu.loginMenu);
            System.out.println("you are in login menu now!");
        } else {
            System.out.println("invalid command bro!..");
        }
    }
}
