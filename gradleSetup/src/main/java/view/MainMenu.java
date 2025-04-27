package view;

import controller.MenuController.MainMenuController;
import model.Enums.MenusRegexes.MainMenuRegexes;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = MainMenuRegexes.ShowCurrentMenu.getMatcher(input)).find()) {
            System.out.println("you are in Main Menu BROOOOO!");
        } else if ((matcher = MainMenuRegexes.goMenu.getMatcher(input)).find()) {
            String menu = matcher.group(1);
            System.out.println(MainMenuController.goToMenu(menu));
        } else if ((matcher = MainMenuRegexes.logout.getMatcher(input)).find()) {
            System.out.println(MainMenuController.manageUserLogout());
        }else {
            System.out.println("invalid command bro!..");
        }
    }
}
