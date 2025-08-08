package io.src.view.GameMenus;

import io.src.controller.GameMenuController.CookingController;
import io.src.controller.GameMenuController.CraftingController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.controller.GameMenuController.GameController;
import io.src.model.Enums.commands.GameCommands.CraftingCommand;
import io.src.model.Enums.commands.GameCommands.HouseMenuCommands;
import io.src.model.MapModule.Position;
import io.src.model.Result;
import io.src.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HouseMenu implements AppMenu {

    private Result result;

    @Override
    public Result check(Scanner scanner, String cmd) {
        if (cmd.equalsIgnoreCase("exit")) {
            App.setCurrentMenu(Menu.gameMenu);
            App.getMe().setPosition(new Position(App.getMe().getPosition().getX(), App.getMe().getPosition().getY() + 2));
            return new Result(true, "exiting home redirecting to farm...");
        }
        if (!(cookingMenu(cmd) || craftingMenu(cmd))) {
            return new Result(true, "Invalid command!");
        }
        return result;
    }

    public boolean cookingMenu(String input) {
        Matcher matcher;
        if ((matcher = HouseMenuCommands.refrigeratorPut.getMatcher(input)).find()) {
            result = CookingController.refrigeratorPut(matcher);
            return true;
        } else if ((matcher = HouseMenuCommands.refrigeratorPick.getMatcher(input)).find()) {
            result = CookingController.refrigeratorPick(matcher);
            return true;

        } else if ((HouseMenuCommands.showRecipes.getMatcher(input)).find()) {
            result = CookingController.showRecipes();
            return true;

        } else if ((matcher = HouseMenuCommands.prepareRecipe.getMatcher(input)).find()) {
            result = CookingController.prepareCooking(matcher);
            if (App.getMe().getEnergyUsage() > 50 || App.getMe().isFainted()) {
                GameController.manageNextTurn();
            }
            return true;

        } else if ((matcher = HouseMenuCommands.addRecipe.getMatcher(input)).find()) {
            String name = matcher.group(1).trim();
            result = CookingController.addRecipe(name);
            return true;
        }
        return false;
    }

    public boolean craftingMenu(String input) {
        Matcher matcher;
        if ((CraftingCommand.ShowRecipe.getMatcher(input)) != null) {
            result = CraftingController.showCraftingRecipes();
            return true;
        } else if ((matcher = CraftingCommand.craftItem.getMatcher(input)) != null) {
            result = CraftingController.craftingItem(matcher);
            if (App.getMe().getEnergyUsage() > 50 || App.getMe().isFainted()) {
                App.getMe().setEnergyUsage(0);
                GameController.manageNextTurn();
            }
            return true;
        } else if ((matcher = CraftingCommand.dropItem.getMatcher(input)) != null) {
            result = CraftingController.placeItem(matcher);
            //Complete
            return true;
        } else if ((matcher = CraftingCommand.cheatCode.getMatcher(input)) != null) {
            result = CraftingController.cheatAddItem(matcher);
            return true;
        } else if ((matcher = CraftingCommand.cheatAddRecipe.getMatcher(input)) != null) {
            result = CraftingController.addRecipe(matcher.group(1));
            return true;
        }
        return false;
    }
}
