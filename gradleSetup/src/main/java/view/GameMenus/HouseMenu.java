package view.GameMenus;

import controller.GameMenuController.CookingController;
import controller.GameMenuController.CraftingController;
import model.App;
import model.Enums.Menu;
import model.Enums.commands.GameCommands.CraftingCommand;
import model.Enums.commands.GameCommands.HouseMenuCommands;
import view.AppMenu;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.regex.Matcher;

public class HouseMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input  = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
            App.setCurrentMenu(Menu.gameMenu);
            System.out.println("exiting home redirecting to farm...");
        } if(!(cookingMenu(input)||craftingMenu(input))){
            System.out.println("Invalid command!");
        }
    }
    public boolean cookingMenu(String input) {
        Matcher matcher;
        if((matcher = HouseMenuCommands.refrigeratorPut.getMatcher(input)).find()) {
            System.out.println(CookingController.refrigeratorPut(matcher));
            return true;
        } else if((matcher = HouseMenuCommands.refrigeratorPick.getMatcher(input)).find()) {
            System.out.println(CookingController.refrigeratorPick(matcher));
            return true;

        } else if ((matcher = HouseMenuCommands.showRecipes.getMatcher(input)).find()) {
            System.out.println(CookingController.showRecipes());
            return true;

        } else if((matcher = HouseMenuCommands.prepareRecipe.getMatcher(input)).find()) {
            System.out.println(CookingController.prepareCooking(matcher));
            return true;

        }
        return false;
    }
    public boolean craftingMenu(String input) {
        Matcher matcher;
        if((matcher = CraftingCommand.ShowRecipe.getMatcher(input)) != null) {
            System.out.println(CraftingController.showCraftingRecipes());
            return true;
        }else if((matcher = CraftingCommand.craftItem.getMatcher(input)) != null) {
            System.out.println(CraftingController.craftingItem(matcher));
            return true;
        } else if ((matcher = CraftingCommand.dropItem.getMatcher(input)) != null) {
            System.out.println(CraftingController.placeItem(matcher));
            //Complete
            return true;
        } else if((matcher = CraftingCommand.cheatCode.getMatcher(input)) != null){
            System.out.println(CraftingController.cheatAddItem(matcher));
            return true;
        }
        return false;
    }
}
