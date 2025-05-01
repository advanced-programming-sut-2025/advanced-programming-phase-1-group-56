package view;

import controller.GameMenuController.CookingController;
import model.Enums.commands.GameCommands.HouseMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HouseMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input  = scanner.nextLine();
        Matcher matcher;
        if((matcher = HouseMenuCommands.refrigeratorPut.getMatcher(input)).find()) {
            System.out.println(CookingController.refrigeratorPut(matcher));
        } else if((matcher = HouseMenuCommands.refrigeratorPick.getMatcher(input)).find()) {
            System.out.println(CookingController.refrigeratorPick(matcher));
        } else if ((matcher = HouseMenuCommands.refrigeratorPick.getMatcher(input)).find()) {
            System.out.println(CookingController.showRecipes());
        } else if((matcher = HouseMenuCommands.prepareRecipe.getMatcher(input)).find()) {
            System.out.println(CookingController.showRecipes()+""+CookingController.prepareCooking(matcher));
        }
    }
}
