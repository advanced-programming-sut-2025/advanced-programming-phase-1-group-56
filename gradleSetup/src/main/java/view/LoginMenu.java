package view;

import com.google.gson.Gson;
import controller.MenuController.LoginMenuController;
import model.App;
import model.Enums.Menu;
import model.Enums.commands.LoginMenuCommands;
import model.User;


import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu {
    private static final String FILE_PATH_FOR_STAY_LOGGED = "StayLoggedIn.json";

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        User tmpUser;
        Gson gson = new Gson();
        try (Reader reader = new FileReader(FILE_PATH_FOR_STAY_LOGGED)) {
            tmpUser = gson.fromJson(reader, User.class);
            App.setCurrentUser(tmpUser);
            if (!App.getCurrentUser().equals(tmpUser)) {
                System.out.println("hello " + tmpUser.getUsername());
            }
        } catch (FileNotFoundException e) {
            App.setCurrentUser(null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if ((matcher = LoginMenuCommands.register.getMatcher(input)).find()) {
            System.out.println(LoginMenuController.manageRegisterUser(matcher));
            if (LoginMenuController.manageRegisterUser(matcher).isSuccess()) {
                String input1 = scanner.nextLine();
                Matcher matcher1;
                if ((matcher1 = LoginMenuCommands.pickSecurityQuestion.getMatcher(input1)).find() && LoginMenuController.manageRegisterUser(matcher).isSuccess()) {
                    System.out.println(LoginMenuController.peakSecurityQuestion(matcher1, matcher));
                } else {
                    System.out.println("ok you don't want it!");
                }
            }
        } else if ((matcher = LoginMenuCommands.goMenu.getMatcher(input)).find()) {
            String menu = matcher.group(1).trim();
            System.out.println(LoginMenuController.goToMenu(menu));
        } else if ((matcher = LoginMenuCommands.loginWithStayLoggedin.getMatcher(input)).find()) {
            System.out.println(LoginMenuController.manageLoginUser(matcher, true));
        } else if ((matcher = LoginMenuCommands.login.getMatcher(input)).find()) {
            System.out.println(LoginMenuController.manageLoginUser(matcher, false));
        } else if ((matcher = LoginMenuCommands.forgetPassword.getMatcher(input)).find()) {
            System.out.println(LoginMenuController.manageForgotPassword(matcher));
            if (LoginMenuController.manageForgotPassword(matcher).isSuccess()) {
                Matcher matcher1;
                String input1 = scanner.nextLine();
                if ((matcher1 = LoginMenuCommands.answerSecurityQuestion.getMatcher(input1)).find() && LoginMenuController.manageForgotPassword(matcher).isSuccess()) {
                    System.out.println(LoginMenuController.answer(matcher, matcher1));
                    String input2 = scanner.nextLine();
                    System.out.println(LoginMenuController.manageAnswerForgotPassword(input2, matcher));
                } else {
                    System.out.println("ok you don't want it!");
                }
            }

        } else if ((input.equals("menu exit"))) {
            App.setCurrentMenu(Menu.exitMenu);
            System.out.println("why do you want to exit? );  ");
        } else if ((LoginMenuCommands.ShowCurrentMenu.getMatcher(input)).find()) {
            System.out.println("you are in login menu now!");
        } else if ((LoginMenuCommands.back.getMatcher(input)).find()) {
            System.out.println("you can't go back!");
        } else {
            System.out.println("invalid command bro!..");
        }
    }
}
