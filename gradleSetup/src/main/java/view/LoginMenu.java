package view;

import controller.MenuController.LoginMenuController;
import model.App;
import model.Enums.Menu;
import model.Enums.MenusRegexes.LoginMenuCommands;


import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = LoginMenuCommands.register.getMatcher(input)).find()) {
            System.out.println(LoginMenuController.manageRegisterUser(matcher));
            String input1 = scanner.nextLine();
            Matcher matcher1;
            if ((matcher1 = LoginMenuCommands.pickSecurityQuestion.getMatcher(input)).find()&&LoginMenuController.manageRegisterUser(matcher).isSuccess()) {
                System.out.println(LoginMenuController.peakSecurityQuestion(matcher1, matcher));
            }
        } else if ((matcher = LoginMenuCommands.goMenu.getMatcher(input)).find()) {
            String menu = matcher.group(1);
            System.out.println(LoginMenuController.goToMenu(menu));
        } else if((matcher = LoginMenuCommands.login.getMatcher(input)).find()) {
            System.out.println(LoginMenuController.manageLoginUser(matcher,false));
        } else if((matcher = LoginMenuCommands.loginWithStayLoggedOut.getMatcher(input)).find()){
            System.out.println(LoginMenuController.manageLoginUser(matcher,true));
        } else if((matcher = LoginMenuCommands.forgetPassword.getMatcher(input)).find()) {
            System.out.println(LoginMenuController.manageForgotPassword(matcher));
            Matcher matcher1;
            String input1 = scanner.nextLine();
            if((matcher1 = LoginMenuCommands.answerSecurityQuestion.getMatcher(input)).find()&&LoginMenuController.manageForgotPassword(matcher).isSuccess()) {
                System.out.println(LoginMenuController.answer(matcher,matcher1));
                String input2 = scanner.nextLine();
                System.out.println(LoginMenuController.manageAnswerForgotPassword(input2,matcher));
            }
        }else if((input.equals("menu exit"))){
            App.setCurrentMenu(Menu.exitMenu);
            System.out.println("why do you want to exit? );  ");
        } else if((matcher = LoginMenuCommands.ShowCurrentMenu.getMatcher(input)).find()){
            System.out.println("you are in login menu now!");
        }else {
            System.out.println("invalid command bro!..");
        }
    }
}
