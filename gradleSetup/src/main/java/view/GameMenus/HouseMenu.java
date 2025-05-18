package view.GameMenus;

import controller.GameMenuController.CookingController;
import controller.GameMenuController.CraftingController;
import model.App;
import model.Enums.Menu;
import controller.GameMenuController.GameController;
import model.App;
import model.Enums.commands.GameCommands.CraftingCommand;
import model.Enums.commands.GameCommands.HouseMenuCommands;
import model.MapModule.Position;
import view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HouseMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
            App.setCurrentMenu(Menu.gameMenu);
            System.out.println("exiting home redirecting to farm...");
            App.getMe().setPosition(new Position(App.getMe().getPosition().getX(),App.getMe().getPosition().getY()+2));
        }
        if (!(cookingMenu(input) || craftingMenu(input))) {
            System.out.println("Invalid command!");
        }
    }

    public boolean cookingMenu(String input) {
        Matcher matcher;
        if ((matcher = HouseMenuCommands.refrigeratorPut.getMatcher(input)).find()) {
            System.out.println(CookingController.refrigeratorPut(matcher));
            return true;
        } else if ((matcher = HouseMenuCommands.refrigeratorPick.getMatcher(input)).find()) {
            System.out.println(CookingController.refrigeratorPick(matcher));
            return true;

        } else if ((HouseMenuCommands.showRecipes.getMatcher(input)).find()) {
            System.out.println(CookingController.showRecipes());
            return true;

        } else if ((matcher = HouseMenuCommands.prepareRecipe.getMatcher(input)).find()) {
            System.out.println(CookingController.prepareCooking(matcher));
            if (App.getMe().getEnergyUsage() > 50 || App.getMe().isFainted()) {
                GameController.manageNextTurn();
            }
            return true;

        } else if((matcher = HouseMenuCommands.addRecipe.getMatcher(input)).find()) {
            String name = matcher.group(1).trim();
            System.out.println(CookingController.addRecipe(name));
            return true;
        }
        return false;
    }

    public boolean craftingMenu(String input) {
        Matcher matcher;
        if ((CraftingCommand.ShowRecipe.getMatcher(input)) != null) {
            System.out.println(CraftingController.showCraftingRecipes());
            return true;
        } else if ((matcher = CraftingCommand.craftItem.getMatcher(input)) != null) {
            System.out.println(CraftingController.craftingItem(matcher));
            if (App.getMe().getEnergyUsage() > 50 || App.getMe().isFainted()) {
                App.getMe().setEnergyUsage(0);
                GameController.manageNextTurn();
            }
            return true;
        } else if ((matcher = CraftingCommand.dropItem.getMatcher(input)) != null) {
            System.out.println(CraftingController.placeItem(matcher));
            //Complete
            return true;
        } else if ((matcher = CraftingCommand.cheatCode.getMatcher(input)) != null) {
            System.out.println(CraftingController.cheatAddItem(matcher));
            return true;
        } else if ((matcher = CraftingCommand.cheatAddRecipe.getMatcher(input)) != null) {
            System.out.println(CraftingController.addRecipe(matcher.group(1)));
            return true;
        }
        return false;
    }
}
