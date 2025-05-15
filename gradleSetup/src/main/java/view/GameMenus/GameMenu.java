package view.GameMenus;

import controller.GameMenuController.FishingController;
import controller.GameMenuController.HusbandryController;
import model.Enums.commands.GameCommands.Artisan;
import model.Enums.commands.GameCommands.GameCommands;
import model.Enums.commands.GameCommands.HusbandryCommands;
import view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        if (!(GameCheck(input)
//                ||
//                DateTimeCheck(input) ||
//                WeatherCheck(input) ||
//                MapCheck(input) ||
//                EnergyAndSkillsCheck(input) ||
//                ToolsCheck(input) ||
//                FarmingCheck(input) ||
//                HusbandryCheck(input) ||
//                ArtisanCheck(input) ||
//                TradeCheck(input) ||
//                RelationShipCheck(input) ||
//                NPCCheck(input)

        )) {

            System.out.println("invalid command");
        }


    }

    public boolean GameCheck(String input) {
        Matcher matcher;

    }


    public boolean HusbandryCheck(String input) {
        Matcher matcher;
        if ((matcher = HusbandryCommands.buildBarnOrCoop.getMatcher(input)) != null) {
            System.out.println(HusbandryController.buildCoopOrBarn(matcher));
            return true;
        } else if ((matcher = HusbandryCommands.buyAnimal.getMatcher(input)) != null) {
            System.out.println(HusbandryController.manageBuyAnimal(matcher));
            return true;
        } else if ((matcher = HusbandryCommands.pettingAnimal.getMatcher(input)) != null) {
            System.out.println(HusbandryController.petting(matcher));
            return true;
        } else if ((matcher = HusbandryCommands.showInfoAnimals.getMatcher(input)) != null) {
            System.out.println(HusbandryController.showInfoOfAnimal());
            return true;
        } else if ((matcher = HusbandryCommands.shepherdAnimals.getMatcher(input)) != null) {
            System.out.println(HusbandryController.shepherdAnimals(matcher));
            return true;
        } else if ((matcher = HusbandryCommands.feedHay.getMatcher(input)) != null) {
            System.out.println(HusbandryController.feedHay(matcher));
            return true;
        } else if ((matcher = HusbandryCommands.produces.getMatcher(input)) != null) {
            System.out.println(HusbandryController.showProduces());
            return true;
        } else if ((matcher = HusbandryCommands.collectProduce.getMatcher(input)) != null) {
            System.out.println(HusbandryController.collectProduce(matcher));
            return true;
        } else if ((matcher = HusbandryCommands.sellAnimal.getMatcher(input)) != null) {
            System.out.println(HusbandryController.sellAnimal(matcher));
            return true;
        } else if ((matcher = HusbandryCommands.fishing.getMatcher(input)) != null) {
            System.out.println(FishingController.fishing(matcher));
        }
        return false;
    }

    public boolean ArtisanCheck(String input) {
        Matcher matcher;
        if ((matcher = Artisan.use.getMatcher(input)) != null) {

            return true;
        } else if ((matcher = Artisan.get.getMatcher(input)) != null) {

            return true;
        }
        return false;
    }

    public boolean FarmingCheck(String input) {
        Matcher matcher;
        if ()
    }

}


