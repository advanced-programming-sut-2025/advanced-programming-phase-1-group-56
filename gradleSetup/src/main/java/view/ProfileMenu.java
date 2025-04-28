package view;

import controller.MenuController.ProfileMenuController;
import model.App;
import model.Enums.Menu;
import model.Enums.commands.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if(ProfileMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println("you are in profile menu");
        } else if((matcher = ProfileMenuCommands.changeEmail.getMatcher(input)) != null) {
            System.out.println(ProfileMenuController.manageChangeEmail(matcher.group(1)).getMessage());
        } else if((matcher = ProfileMenuCommands.changePassword.getMatcher(input)) != null) {
            System.out.println(ProfileMenuController.manageChangePassword(matcher.group(1),matcher.group(2)).getMessage());
        } else if((matcher = ProfileMenuCommands.changeUserName.getMatcher(input)) != null) {
            System.out.println(ProfileMenuController.manageChangeUsername(matcher.group(1)).getMessage());
        } else if(ProfileMenuCommands.changeNickName.getMatcher(input) != null) {
            System.out.println(ProfileMenuController.manageChangeNickName(matcher.group(1)).getMessage());
        } else if(ProfileMenuCommands.showUserInformation.getMatcher(input) != null) {
            System.out.println(ProfileMenuController.UserInfo().getMessage());
        } if (ProfileMenuCommands.BackToMainMenu.getMatcher(input) != null) {
            System.out.println("Bezan Berim Be Main Menu...");
            App.setCurrentMenu(Menu.mainMenu);
        }else {
            System.out.println("invalid command bro!..");
        }
    }
}
