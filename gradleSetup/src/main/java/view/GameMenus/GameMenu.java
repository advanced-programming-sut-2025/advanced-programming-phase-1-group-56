package view.GameMenus;

import controller.GameMenuController.*;
import model.App;
import model.Enums.commands.GameCommands.*;
import model.MapModule.GameLocations.Farm;
import view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        if (!(GameCheck(input, scanner) ||
                DateTimeCheck(input) ||
                WeatherCheck(input) ||
                EnergyAndSkillsCheck(input) ||
                ToolsCheck(input) ||
                FarmingCheck(input) ||
                HusbandryCheck(input) ||
                ArtisanCheck(input) ||
                TradeCheck(input) ||
                RelationShipCheck(input) ||
                NPCCheck(input))
        ) {

            System.out.println("invalid command");
        }


    }

    public boolean GameCheck(String input, Scanner scanner) {
        Matcher matcher;
        if ()//Complete loadGame
        else if ((matcher = GameCommands.Walk.getMatcher(input)).find()) {
            boolean isCorrect = false;
            System.out.println(MapController.calculateMoveEnergy(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
            isCorrect = MapController.calculateMoveEnergy(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))).isSuccess();
            if (isCorrect) {
                String input1 = scanner.nextLine();
                if (input1.toLowerCase().equals("yes")) {
                    System.out.println(MapController.movePlayer(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
                }
            }
            return true;
        } else if ((matcher = GameCommands.printMap.getMatcher(input)).find()) {
            //Complete
            return true;
        } else if ((matcher = GameCommands.helpReadingMap.getMatcher(input)).find()) {
            //Complete
            return true;
        }
        return false;

    }

    public boolean ToolsCheck(String input) {
        Matcher matcher;
        if ((matcher = ToolCommands.toolsEquip.getMatcher(input)) != null) {
            System.out.println(ToolsController.equipTool(matcher.group(1)));
            return true;
        } else if ((matcher = ToolCommands.toolsShowCurrent.getMatcher(input)) != null) {
            System.out.println(ToolsController.showCurrentTools());
            return true;
        } else if ((matcher = ToolCommands.toolsShowAvailable.getMatcher(input)) != null) {
            System.out.println(ToolsController.showToolsAvailable());
            return true;
        } else if ((matcher = ToolCommands.toolsUpgrade.getMatcher(input)) != null) {
            System.out.println(ToolsController.upgradeTools(matcher.group(1)));
            return true;
        } else if ((matcher = ToolCommands.toolsUse.getMatcher(input)) != null) {
            System.out.println(ToolsController.useTools(matcher.group(1)));
            //CorrectNess
            return true;
        }
        return false;
    }

    public boolean EnergyAndSkillsCheck(String input) {
        Matcher matcher;
        if ((matcher = EnergyAndSkillsCommands.showEnergy.getMatcher(input)).find()) {
            System.out.println(EnergyController.energyShow());
            return true;
        } else if ((matcher = EnergyAndSkillsCommands.cheatEnergy.getMatcher(input)).find()) {
            System.out.println(EnergyController.cheatEnergySet(matcher.group(1)));
            return true;
        } else if ((matcher = EnergyAndSkillsCommands.unlimitedEnergy.getMatcher(input)).find()) {
            System.out.println(EnergyController.toggleUnlimitedEnergy());
            return true;
        } else if ((matcher = EnergyAndSkillsCommands.showInventory.getMatcher(input)).find()) {
            System.out.println(InventoryController.inventoryShow());
            return true;
        } else if ((matcher = EnergyAndSkillsCommands.trashInventory.getMatcher(input)).find()) {
            System.out.println(InventoryController.manageInventoryTrash());
            return true;
        }
        return false;
        //Complete trash and show inventory , and add xp for skills!!!
    }

    public boolean DateTimeCheck(String input) {
        Matcher matcher;
        if ((matcher = TimeAndDateCommands.showTime.getMatcher(input)) != null) {
            System.out.println(TimeAndDateController.manageShowTime());
            return true;
        } else if ((matcher = TimeAndDateCommands.showDate.getMatcher(input)) != null) {
            System.out.println(TimeAndDateController.manageShowDate());
            return true;
        } else if ((matcher = TimeAndDateCommands.showDateTime.getMatcher(input)) != null) {
            System.out.println(TimeAndDateController.manageShowDateAndTime());
            return true;
        } else if ((matcher = TimeAndDateCommands.showSeason.getMatcher(input)) != null) {
            System.out.println(TimeAndDateController.showCurrentSeason());
            return true;
        } else if ((matcher = TimeAndDateCommands.showDayOfWeek.getMatcher(input)) != null) {
            System.out.println(TimeAndDateController.dayOfTheWeek());
            return true;

        }
        //Cheat
        else if ((matcher = TimeAndDateCommands.cheatAdvancedDate.getMatcher(input)) != null) {
            System.out.println(TimeAndDateController.cheatAdvanceDate(matcher.group(1)));
            return true;
        } else if ((matcher = TimeAndDateCommands.cheatAdvancedTime.getMatcher(input)) != null) {
            System.out.println(TimeAndDateController.cheatAdvanceTime(matcher.group(1)));
            return true;
        }
        return false;
    }

    public boolean WeatherCheck(String input) {
        Matcher matcher;
        if ((matcher = WeatherCommands.showTodayWeather.getMatcher(input)) != null) {
            System.out.println(WeatherController.showTodayWeather());
            return true;
        } else if ((matcher = WeatherCommands.showTomorrowWeather.getMatcher(input)) != null) {
            System.out.println(WeatherController.showTomorrowWeather());
            return true;
        } else if ((matcher = WeatherCommands.buildGreenHouse.getMatcher(input)) != null) {
            System.out.println(GreenHouseController.greenHouseBuild());
            return true;
        }
        //Cheat
        else if ((matcher = WeatherCommands.cheatWeather.getMatcher(input)) != null) {
            System.out.println(WeatherController.cheatWeather(matcher.group(1)));
            return true;
        } else if ((matcher = WeatherCommands.cheatThor.getMatcher(input)) != null) {
            System.out.println(WeatherController.cheatThor(matcher.group(1), matcher.group(2)));
            return true;
        }
        return false;
    }

    public boolean HusbandryCheck(String input) {
        Matcher matcher;
        if ((matcher = HusbandryCommands.pettingAnimal.getMatcher(input)) != null) {
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
        }
        return false;
    }

    public boolean ArtisanCheck(String input) {
        Matcher matcher;
        if ((matcher = ArtisanCommands.use.getMatcher(input)) != null) {
            System.out.println(ArtisanController.useArtisan(matcher));
            return true;
        } else if ((matcher = ArtisanCommands.get.getMatcher(input)) != null) {
            System.out.println(ArtisanController.getArtisan(matcher));
            return true;
        }
        return false;
    }

    public boolean FarmingCheck(String input) {
        Matcher matcher;
        if ((matcher = FarmingCommands.CRAFT_INFO.getMatcher(input)).find()) {
            if (!(App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation() instanceof Farm)) {
                System.out.println("you are not in the farm!");
                return false;
            }
            System.out.println(FarmingController.craftInfo(matcher));
            return true;
        } else if ((matcher = FarmingCommands.PLANT.getMatcher(input)).find()) {
            System.out.println(FarmingController.managePlantSeed(matcher));
            return true;
        } else if ((matcher = FarmingCommands.showPlant.getMatcher(input)).find()) {
            if (!(App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation() instanceof Farm)) {
                System.out.println("you are not in the farm!");
                return false;
            }
            System.out.println(FarmingController.showPlant(matcher));
            return true;
        } else if ((matcher = FarmingCommands.feritilize.getMatcher(input)).find()) {
            if (!(App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation() instanceof Farm)) {
                System.out.println("you are not in the farm!");
                return false;
            }
            System.out.println(FarmingController.manageFertilize(matcher));
            return true;
        } else if ((matcher = FarmingCommands.showWater.getMatcher(input)).find()) {
            System.out.println(FarmingController.howMuchWaterIsExist());
            return true;
        }
        return false;
    }

}


