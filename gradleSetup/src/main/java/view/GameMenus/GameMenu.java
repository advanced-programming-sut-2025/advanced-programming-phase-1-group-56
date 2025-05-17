package view.GameMenus;

import controller.GameMenuController.*;
import model.App;
import model.Enums.commands.GameCommands.*;
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
            System.out.println("Invalid command!");
        }


    }

    public boolean GameCheck(String input, Scanner scanner) {
        Matcher matcher;
        if ((matcher = PreGameMenuCommands.newGameRegex.getMatcher(input)) != null) {
            try {
                String message = PreGameMenuController.manageNewGame(matcher.group(1).trim(), scanner).message();
                System.out.println(message);
            } catch (Exception e) {
                System.out.println("exception threw by manageNewGame");
                System.out.println(e.toString());
                return true;
            }
        } else if (PreGameMenuCommands.loadGame.getMatcher(input) != null) {//load game
            System.out.println(PreGameMenuController.loadGame().message());
            return true;
        } else if (GameCommands.exitGame.getMatcher(input).find())//exit game
        {
            System.out.println(GameController.exitGame().message());
            return true;
        } else if (GameCommands.deleteGame.getMatcher(input).find())//delete game
        {
            System.out.println(GameController.terminateGame(scanner).message());
            return true;
        } else if (GameCommands.nextTurn.getMatcher(input).find()) {//skip turn
            System.out.println(GameController.manageNextTurn().message());
            return true;
        } else if ((matcher = GameCommands.Walk.getMatcher(input)).find()) {
            boolean isCorrect;
            System.out.println(MapController.calculateMoveEnergy(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
            isCorrect = MapController.calculateMoveEnergy(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))).isSuccess();
            if (isCorrect) {
                String input1 = scanner.nextLine();
                if (input1.equalsIgnoreCase("yes")) {
                    System.out.println(MapController.movePlayer(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
                }
            }
            return true;
        } else if ((GameCommands.printMap.getMatcher(input)).find()) {
            System.out.println(MapController.printMap());
            return true;
        } else if ((GameCommands.helpReadingMap.getMatcher(input)).find()) {
            System.out.println(MapController.manageHelpReadingMap());
            return true;
        }
        return false;

    }

    public boolean ToolsCheck(String input) {
        Matcher matcher;
        if ((matcher = ToolCommands.toolsEquip.getMatcher(input)) != null) {
            System.out.println(ToolsController.equipTool(matcher.group(1)));
            return true;
        } else if ((ToolCommands.toolsShowCurrent.getMatcher(input)) != null) {
            System.out.println(ToolsController.showCurrentTools());
            return true;
        } else if ((ToolCommands.toolsShowAvailable.getMatcher(input)) != null) {
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
        if ((EnergyAndSkillsCommands.showEnergy.getMatcher(input)).find()) {
            System.out.println(EnergyController.energyShow());
            return true;
        } else if ((matcher = EnergyAndSkillsCommands.cheatEnergy.getMatcher(input)).find()) {
            System.out.println(EnergyController.cheatEnergySet(matcher.group(1)));
            return true;
        } else if ((EnergyAndSkillsCommands.unlimitedEnergy.getMatcher(input)).find()) {
            System.out.println(EnergyController.toggleUnlimitedEnergy());
            return true;
        } else if ((EnergyAndSkillsCommands.showInventory.getMatcher(input)).find()) {
            System.out.println(InventoryController.inventoryShow());
            return true;
        } else if ((EnergyAndSkillsCommands.trashInventory.getMatcher(input)).find()) {
            System.out.println(InventoryController.manageInventoryTrash(matcher.group(1),matcher.group(2)));
            return true;
        }
        //Complete trash and show inventory , and add xp for skills!!!
        return false;
    }

    public boolean DateTimeCheck(String input) {
        Matcher matcher;
        if ((TimeAndDateCommands.showTime.getMatcher(input)) != null) {
            System.out.println(TimeAndDateController.manageShowTime());
            return true;
        } else if ((TimeAndDateCommands.showDate.getMatcher(input)) != null) {
            System.out.println(TimeAndDateController.manageShowDate());
            return true;
        } else if ((TimeAndDateCommands.showDateTime.getMatcher(input)) != null) {
            System.out.println(TimeAndDateController.manageShowDateAndTime());
            return true;
        } else if ((TimeAndDateCommands.showSeason.getMatcher(input)) != null) {
            System.out.println(TimeAndDateController.showCurrentSeason());
            return true;
        } else if ((TimeAndDateCommands.showDayOfWeek.getMatcher(input)) != null) {
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
        if ((WeatherCommands.showTodayWeather.getMatcher(input)) != null) {
            System.out.println(WeatherController.showTodayWeather());
            return true;
        } else if ((WeatherCommands.showTomorrowWeather.getMatcher(input)) != null) {
            System.out.println(WeatherController.showTomorrowWeather());
            return true;
        } else if ((WeatherCommands.buildGreenHouse.getMatcher(input)) != null) {
            System.out.println(GreenHouseController.greenHouseBuild());
            return true;
        }
        //Cheat
        else if ((matcher = WeatherCommands.cheatWeather.getMatcher(input)) != null) {
            System.out.println(WeatherController.cheatWeather(matcher.group(1)));
            return true;
        } else if ((matcher = WeatherCommands.cheatThor.getMatcher(input)) != null) {
            System.out.println(WeatherController.cheatThor(App.getMe().getCurrentGameLocation(), matcher.group(1), matcher.group(2)));
            return true;
        }
        return false;
    }

    public boolean HusbandryCheck(String input) {
        Matcher matcher;
        if ((matcher = HusbandryCommands.pettingAnimal.getMatcher(input)) != null) {
            System.out.println(HusbandryController.petting(matcher));
            return true;
        } else if ((HusbandryCommands.showInfoAnimals.getMatcher(input)) != null) {
            System.out.println(HusbandryController.showInfoOfAnimal());
            return true;
        } else if ((matcher = HusbandryCommands.shepherdAnimals.getMatcher(input)) != null) {
            System.out.println(HusbandryController.shepherdAnimals(matcher));
            return true;
        } else if ((matcher = HusbandryCommands.feedHay.getMatcher(input)) != null) {
            System.out.println(HusbandryController.feedHay(matcher));
            return true;
        } else if ((HusbandryCommands.produces.getMatcher(input)) != null) {
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
            if (!App.getMe().getCurrentGameLocation().isPlantingLand()) {
                System.out.println("you are not in a planting land!");
                return false;
            }
            System.out.println(FarmingController.craftInfo(matcher));
            return true;
        } else if ((matcher = FarmingCommands.PLANT.getMatcher(input)).find()) {
            System.out.println(FarmingController.managePlantSeed(matcher));
            return true;
        } else if ((matcher = FarmingCommands.showPlant.getMatcher(input)).find()) {
            if (!App.getMe().getCurrentGameLocation().isPlantingLand()) {
                System.out.println("you are not in the farm!");
                return true;
            }
            System.out.println(FarmingController.showPlant(matcher));
            return true;
        } else if ((matcher = FarmingCommands.feritilize.getMatcher(input)).find()) {
            if (!App.getMe().getCurrentGameLocation().isPlantingLand()) {
                System.out.println("you are not in the farm!");
                return true;
            }
            System.out.println(FarmingController.manageFertilize(matcher));
            return true;
        } else if ((FarmingCommands.showWater.getMatcher(input)).find()) {
            System.out.println(FarmingController.howMuchWaterIsExist());
            return true;
        }
        return false;
    }

    public boolean TradeCheck(String input) {
        Matcher matcher;
        if ((TradeCommands.startTrade.getMatcher(input)) != null) {
            System.out.println(TradeController.startTrade().getMessage());
            return true;
        } else if ((matcher = TradeCommands.cheatAddMoney.getMatcher(input)) != null) {
            System.out.println(TradeController.cheatAddMoney(matcher.group(1)).getMessage());
            return true;
        } else {
            return false;
        }
    }

    public boolean RelationShipCheck(String input) {
        Matcher matcher;
        if ((matcher = RelationshipCommands.talk.getMatcher(input)) != null) {
            System.out.println(FriendshipController.TalkWithPlayer(matcher.group(1), matcher.group(2)).getMessage());
            return true;
        } else if ((matcher = RelationshipCommands.talkHistory.getMatcher(input)) != null) {
            System.out.println(FriendshipController.historyOfTalking(matcher.group(1)).getMessage());
            return true;
        } else if ((matcher = RelationshipCommands.gift.getMatcher(input)) != null) {
            System.out.println(FriendshipController.sendGift(matcher.group(1), matcher.group(2), matcher.group(3)).message());
            return true;
        } else if ((RelationshipCommands.giftList.getMatcher(input)) != null) {
            System.out.println(FriendshipController.giftList().message());
            return true;
        } else if ((RelationshipCommands.giftHistory.getMatcher(input)) != null) {
            System.out.println(FriendshipController.giftHistory().message());
            return true;
        } else if ((matcher = RelationshipCommands.giftRate.getMatcher(input)) != null) {
            System.out.println(FriendshipController.manageRatingGift(matcher.group(1), matcher.group(2)).message());
            return true;
        } else if ((matcher = RelationshipCommands.flower.getMatcher(input)) != null) {
            System.out.println(FriendshipController.buyFlower(matcher.group(1)).message());
            return true;
        } else if ((matcher = RelationshipCommands.hug.getMatcher(input)) != null) {
            System.out.println(FriendshipController.hugPlayer(matcher.group(1)).message());
            return true;
        } else if ((RelationshipCommands.showAllFriendships.getMatcher(input)) != null) {
            System.out.println(FriendshipController.showFriendships().message());
            return true;
        } else if ((matcher = RelationshipCommands.marryRequest.getMatcher(input)) != null) {
            System.out.println(FriendshipController.askMarriage(matcher.group(1), matcher.group(2)).message());
            return true;
        } else if ((matcher = RelationshipCommands.marryRespond.getMatcher(input)) != null) {
            System.out.println(FriendshipController.askMarriage(matcher.group(1), matcher.group(2)).message());
            return true;
        } else {
            return false;
        }
    }

    private boolean NPCCheck(String input) {
        Matcher matcher;
        if ((NpcCommands.MeetNPC.getMatcher(input)) != null) {
            System.out.println(NpcController.meetNPC());
            return true;
        } else if ((matcher = NpcCommands.GiftNPC.getMatcher(input)) != null) {
            System.out.println(NpcController.giftNPC(matcher.group(1), matcher.group(2)).getMessage());
            return true;
        } else if ((NpcCommands.NpcFriendship.getMatcher(input)) != null) {
            System.out.println(NpcController.manageFriendshipNPCList().getMessage());
            return true;
        } else if ((NpcCommands.ListQuest.getMatcher(input)) != null) {
            System.out.println(NpcController.manageShowAllQuests().getMessage());
            System.out.println("----------------");
            System.out.println(NpcController.manageShowAllQuests().getMessage());
            return true;
        } else if ((matcher = NpcCommands.FinishQuest.getMatcher(input)) != null) {
            System.out.println(NpcController.finishingQuest(matcher.group(1)).getMessage());
            return true;
        } else {
            return false;
        }
    }
}


