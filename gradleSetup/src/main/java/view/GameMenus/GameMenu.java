package view.GameMenus;

import controller.GameMenuController.*;
import model.App;
import model.Enums.FarmPosition;
import model.Enums.Menu;
import model.Enums.TileType;
import model.Enums.commands.GameCommands.*;
import model.MapModule.Buildings.*;
import model.MapModule.GameLocations.Farm;
import model.MapModule.Position;
import model.Player;
import model.States.Energy;
import view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        if (!(
                GameCheck(input, scanner) ||
                        DateTimeCheck(input) ||
                        WeatherCheck(input) ||
                        EnergyAndSkillsCheck(input) ||
                        ToolsCheck(input) ||
                        FarmingCheck(input) ||
                        HusbandryCheck(input) ||
                        ArtisanCheck(input) ||
                        TradeCheck(input) ||
                        RelationShipCheck(input) ||
                        NPCCheck(input)
        )
        ) {
            System.out.println("Invalid command!");
        }
    }

    public boolean GameCheck(String input, Scanner scanner) {
        Matcher matcher;
        if ((matcher = PreGameMenuCommands.newGameRegex.getMatcher(input)) != null) {
            try {
                String message = PreGameMenuController.manageNewGame(matcher.group(1).trim().trim(), scanner).message();
                System.out.println(message);
            } catch (Exception e) {
                System.out.println("exception threw by manageNewGame");
                System.out.println(e.getMessage());
            }
            return true;
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
        } else if (input.equalsIgnoreCase("pwd")) {
            System.out.println("X: " + App.getMe().getPosition().getX() + "Y:" + App.getMe().getPosition().getY());
            System.out.println("X: " + App.getMe().getPlayerFarm().getTiles()[0].length + "Y: " + App.getMe().getPlayerFarm().getTiles().length);
            System.out.println("HOME DOOR" + App.getMe().getDefaultHome().getDoorPosition().getX() + ":" + App.getMe().getDefaultHome().getDoorPosition().getY());
            System.out.println(App.getMe().getPlayerFarm().getTileByPosition(App.getMe().getDefaultHome().getDoorPosition()).isWalkable());
            System.out.println("GREEN HOUSE DOOR" + App.getMe().getPlayerFarm().getGreenHouse().getDoorPosition().getX() + ":" + App.getMe().getPlayerFarm().getGreenHouse().getDoorPosition().getY());
            return true;
        } else if ((matcher = Pattern.compile("\\s*sp\\s*(\\d+)\\s*(\\d+)\\s*").matcher(input)).matches()) {
            int x = Integer.parseInt(matcher.group(1).trim());
            int y = Integer.parseInt(matcher.group(2).trim());
            App.getMe().setPosition(new Position(x, y));
            System.out.println(MapController.printMap());
            setCurrentStoreOrBuilding();
            return true;
        } else if((matcher = Pattern.compile("\\s*ef\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s*").matcher(input)).matches()) {
            int x1 =Integer.parseInt(matcher.group(1).trim());
            int y1 = Integer.parseInt(matcher.group(2).trim());
            int x2 = Integer.parseInt(matcher.group(3).trim());
            int y2 =Integer.parseInt(matcher.group(4).trim());
            System.out.printf("emptying field: from <%d,%d> to <%d, %d>\n",x1,y1,x2,y2);
            for (int i = x1; i < x2 ; i++) {
                for (int j = y1; j < y2; j++) {
                    App.getMe().getPlayerFarm().getTileByPosition(new Position(i, j)).setWalkable(true);
                    App.getMe().getPlayerFarm().getTileByPosition(new Position(i, j)).setTileType(TileType.Soil);
                    App.getMe().getPlayerFarm().getTileByPosition(new Position(i, j)).setFixedObject(null);
                }
            }
            return true;
        }else if ((matcher = CraftingCommand.cheatCode.getMatcher(input)) != null) {
            System.out.println(CraftingController.cheatAddItem(matcher));
            return true;
        } else if ((matcher = GameCommands.Walk.getMatcher(input)).find()) {

            boolean isCorrect;
            Energy energy = new Energy(0);
            isCorrect = MapController.calculateMoveEnergy(Integer.parseInt(matcher.group(1).trim()), Integer.parseInt(matcher.group(2).trim()),energy).isSuccess();
            if (isCorrect) {
                if(energy.getEnergy() > App.getMe().getEnergy().getEnergy()) {
                    System.out.println(MapController.calculateMoveEnergy(Integer.parseInt(matcher.group(1).trim()), Integer.parseInt(matcher.group(2).trim()),energy));
                    String input1 = scanner.nextLine();
                    if (input1.equalsIgnoreCase("yes")) {
                        System.out.println(MapController.movePlayer(Integer.parseInt(matcher.group(1).trim()), Integer.parseInt(matcher.group(2).trim())));
                    }
                }
                else{
                    System.out.println(MapController.movePlayer(Integer.parseInt(matcher.group(1).trim()), Integer.parseInt(matcher.group(2).trim())));
                }
            }
            else{
                System.out.println(MapController.calculateMoveEnergy(Integer.parseInt(matcher.group(1).trim()), Integer.parseInt(matcher.group(2).trim()),energy));
            }


            if (App.getMe().getEnergyUsage() > 50 || App.getMe().isFainted()) {
                App.getMe().setEnergyUsage(0);
                GameController.manageNextTurn();
            }

            return true;
        } else if ((GameCommands.printMap.getMatcher(input)).find()) {
            System.out.println(MapController.printMap());
            return true;
        } else if ((GameCommands.helpReadingMap.getMatcher(input)).find()) {
            System.out.println(MapController.printMapHint().message());
            return true;
        } else if ((matcher = CraftingCommand.dropItem.getMatcher(input)) != null) {
            System.out.println(CraftingController.placeItem(matcher));
            return true;
        } else if ((matcher = GameCommands.eatFood.getMatcher(input)).find()) {
            System.out.println(CookingController.eatFood(matcher));
            return true;
        }
        return false;

    }

    private void setCurrentStoreOrBuilding() {
        Player player = App.getMe();
        if (player.getCurrentGameLocation().getTileByPosition(player.getPosition()).getTileType() == TileType.Wrapper) {
//            Tile t = player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX() , player.getPosition().getY());
            if(player.getCurrentGameLocation().equals(App.getMe().getPlayerFarm().getGreenHouse().getIndoor())){
                player.setCurrentGameLocation(App.getMe().getPlayerFarm());
                Position position = App.getMe().getPlayerFarm().getGreenHouse().getPosition();
                player.setPosition(new Position(position.getX(), position.getY() +2));
                System.out.println("backing to farm from greenhouse");
            }
             else if (player.getCurrentGameLocation() instanceof Farm) {
                player.setCurrentGameLocation(App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown());
                if ((player.getFarmPosition() == FarmPosition.LEFT)) {
//                    player.getPosition().getX() == 0 player.getPosition().getY() == 52;
                    player.setPosition(new Position(1, 53));
                } else if ((player.getFarmPosition() == FarmPosition.RIGHT)) {
                    player.setPosition(new Position(110, 76));
                } else if ((player.getFarmPosition() == FarmPosition.UP)) {
                    player.setPosition(new Position(81, 1));
                } else {
                    player.setPosition(new Position(54, 108));
                }
            } else {
                if (player.getPosition().getX() == 0 && (player.getPosition().getY() == 52 || player.getPosition().getY() == 53 || player.getPosition().getY() == 54)) {
                    if (player.getFarmPosition() == FarmPosition.LEFT) {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.LEFT) {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                    player.setPosition(new Position(78, 17));
                } else if (player.getPosition().getX() == 111 && player.getPosition().getY() == 76) {
                    if (player.getFarmPosition() == FarmPosition.RIGHT) {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.RIGHT) {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                    player.setPosition(new Position(78, 17));
                } else if (player.getPosition().getY() == 0 && (player.getPosition().getX() == 80 || player.getPosition().getX() == 81 || player.getPosition().getX() == 82)) {
                    if (player.getFarmPosition() == FarmPosition.UP) {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.UP) {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                    player.setPosition(new Position(41, 63));
                } else if (player.getPosition().getY() == 109 && (player.getPosition().getX() == 53 || player.getPosition().getX() == 54 || player.getPosition().getX() == 55)) {
                    if (player.getFarmPosition() == FarmPosition.DOWN) {
                        player.setCurrentGameLocation(player.getPlayerFarm());
                    } else if (player.getPartner().getFarmPosition() == FarmPosition.DOWN) {
                        player.setCurrentGameLocation(player.getPartner().getPlayerFarm());
                    }
                    player.setPosition(new Position(41, 1));
                }
            }

        } else if (player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX(), player.getPosition().getY()).getFixedObject() instanceof Building building) {
            if (player.getPosition().equals(building.getDoorPosition())) {
                if (building instanceof JojaMart) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((JojaMart) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((JojaMart) building).getClosingHour()) {
                        System.out.println("The shop is closed, please come from 9 am to 11 pm.");

                    }
                    App.setCurrentMenu(Menu.JojaMartMenu);
                    System.out.println("welcome to Joja Mart");
                } else if (building instanceof Blacksmith) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((Blacksmith) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((Blacksmith) building).getClosingHour()) {
                        System.out.println("The shop is closed, please come from 9 am to 4 pm.");
                    }
                    App.setCurrentMenu(Menu.BlackSmithMenu);
                    System.out.println("welcome to Black Smith");
                } else if (building instanceof CarpentersShop) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((CarpentersShop) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((CarpentersShop) building).getClosingHour()) {
                        System.out.println("The shop is closed, please come from 9 am to 8 pm.");
                    }
                    App.setCurrentMenu(Menu.CarpenterShopMenu);
                    System.out.println("welcome to Carpenters Shop");
                } else if (building instanceof MarniesRanch) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((MarniesRanch) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((MarniesRanch) building).getClosingHour()) {
                        System.out.println("The shop is closed, please come from 9 am to 4 pm.");
                    }
                    App.setCurrentMenu(Menu.MarniesRanchMenu);
                    System.out.println("welcome to Marnies Ranch");
                } else if (building instanceof PierresGeneralStore) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((PierresGeneralStore) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((PierresGeneralStore) building).getClosingHour()) {
                        System.out.println("The shop is closed, please come from 9 am to 5 pm.");
                    }
                    App.setCurrentMenu(Menu.PierresGeneralStoreMenu);
                    System.out.println("welcome to Pirrer Store");
                } else if (building instanceof TheSaloonStardrop) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((TheSaloonStardrop) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((TheSaloonStardrop) building).getClosingHour()) {
                        System.out.println("The shop is closed, please come from 12 am to 12 pm.");
                    }
                    App.setCurrentMenu(Menu.TheSaloonStarDropMenu);
                    System.out.println("welcome to Saloon Star Drop");
                } else if (building instanceof FishShop) {
                    if (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < ((FishShop) building).getOpeningHour() ||
                            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() > ((FishShop) building).getClosingHour()) {
                        System.out.println("The shop is closed, please come from 9 am to 5 pm.");
                    }
                    App.setCurrentMenu(Menu.FishShopMenu);
                    System.out.println("welcome to Fish Shop");
                } else if (building instanceof Home) {
                    App.setCurrentMenu(Menu.HouseMenu);
                    System.out.println("welcome to your home");
                } else if (building instanceof GreenHouse) {
                    App.setCurrentMenu(Menu.gameMenu);
                    if (((GreenHouse) building).isBroken()) {
                        System.out.println("the green house is broken and you cant come into it.");
                    }
                    player.setCurrentGameLocation(((GreenHouse) building).getIndoor());
                    player.setPosition(new Position(5, 9));
                    System.out.println("welcome to green house");
                }
            }
        }
    }

    public boolean ToolsCheck(String input) {
        Matcher matcher;
        if ((matcher = ToolCommands.toolsEquip.getMatcher(input)) != null) {
            System.out.println(ToolsController.equipTool(matcher.group(1).trim()));
            if (App.getMe().getEnergyUsage() > 50) {
                App.getMe().setEnergyUsage(0);
                GameController.manageNextTurn();
            }
            return true;
        } else if ((ToolCommands.toolsShowCurrent.getMatcher(input)) != null) {
            System.out.println(ToolsController.showCurrentTools());
            return true;
        } else if ((ToolCommands.toolsShowAvailable.getMatcher(input)) != null) {
            System.out.println(ToolsController.showToolsAvailable());
            return true;
        } else if ((matcher = ToolCommands.toolsUpgrade.getMatcher(input)) != null) {
            System.out.println(ToolsController.upgradeTools(matcher.group(1).trim()));
            return true;
        } else if ((matcher = ToolCommands.toolsUse.getMatcher(input)) != null) {
            System.out.println(ToolsController.useTools(matcher.group(1).trim()));
            if (App.getMe().getEnergyUsage() > 50 || App.getMe().isFainted()) {
                GameController.manageNextTurn();
            }
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
            System.out.println(EnergyController.cheatEnergySet(matcher.group(1).trim()));
            return true;
        } else if ((EnergyAndSkillsCommands.unlimitedEnergy.getMatcher(input)).find()) {
            System.out.println(EnergyController.toggleUnlimitedEnergy());
            return true;
        } else if ((EnergyAndSkillsCommands.showInventory.getMatcher(input)).find()) {
            System.out.println(InventoryController.inventoryShow());
            return true;
        } else if ((matcher = EnergyAndSkillsCommands.trashInventory.getMatcher(input)).find()) {
            System.out.println(InventoryController.manageInventoryTrash(matcher.group(1).trim(), matcher.group(2).trim()));
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
            System.out.println(TimeAndDateController.cheatAdvanceDate(matcher.group(1).trim()));
            return true;
        } else if ((matcher = TimeAndDateCommands.cheatAdvancedTime.getMatcher(input)) != null) {
            System.out.println(TimeAndDateController.cheatAdvanceTime(matcher.group(1).trim()));
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
            System.out.println(WeatherController.cheatWeather(matcher.group(1).trim()));
            return true;
        } else if ((matcher = WeatherCommands.cheatThor.getMatcher(input)) != null) {
            System.out.println(WeatherController.cheatThor(App.getMe().getCurrentGameLocation(), matcher.group(1).trim(), matcher.group(2).trim()));
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
        } else if ((matcher = HusbandryCommands.cheatSetFriendship.getMatcher(input)) != null) {
            System.out.println(HusbandryController.cheatSetFriendship(matcher));
            return true;
        } else if ((matcher = HusbandryCommands.collectProduce.getMatcher(input)) != null) {
            System.out.println(HusbandryController.collectProduce(matcher));
            return true;
        } else if ((matcher = HusbandryCommands.fishing.getMatcher(input)) != null) {
            System.out.println(FishingController.fishing(matcher));
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
            if (App.getMe().getEnergyUsage() >= 50 || App.getMe().isFainted()) {
                GameController.manageNextTurn();
            }
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
            System.out.println(TradeController.cheatAddMoney(matcher.group(1).trim()).getMessage());
            return true;
        } else if ((matcher = TradeCommands.sell.getMatcher(input)) != null) {
            System.out.println(TradeController.sellProducts(matcher).getMessage());
            return true;
        } else if ((matcher = ShopCommands.cheatMoney.getMatcher(input)) != null) {//skip turn
            App.getMe().addGold(Integer.parseInt(matcher.group(1)));
            System.out.println("cheat money");
            return true;
        } else {
            return false;
        }
    }

    public boolean RelationShipCheck(String input) {
        Matcher matcher;
        if ((matcher = RelationshipCommands.talk.getMatcher(input)) != null) {
            System.out.println(FriendshipController.TalkWithPlayer(matcher.group(1).trim(), matcher.group(2).trim()).getMessage());
            return true;
        } else if ((matcher = RelationshipCommands.talkHistory.getMatcher(input)) != null) {
            System.out.println(FriendshipController.historyOfTalking(matcher.group(1).trim()).getMessage());
            return true;
        } else if ((matcher = RelationshipCommands.gift.getMatcher(input)) != null) {
            System.out.println(FriendshipController.sendGift(matcher.group(1).trim(), matcher.group(2).trim(), matcher.group(3).trim()).message());
            return true;
        } else if ((RelationshipCommands.giftList.getMatcher(input)) != null) {
            System.out.println(FriendshipController.giftList().message());
            return true;
        } else if ((RelationshipCommands.giftHistory.getMatcher(input)) != null) {
            System.out.println(FriendshipController.giftHistory().message());
            return true;
        } else if ((matcher = RelationshipCommands.giftRate.getMatcher(input)) != null) {
            System.out.println(FriendshipController.manageRatingGift(matcher.group(1).trim(), matcher.group(2).trim()).message());
            return true;
        } else if ((matcher = RelationshipCommands.flower.getMatcher(input)) != null) {
            System.out.println(FriendshipController.buyFlower(matcher.group(1).trim()).message());
            return true;
        } else if ((matcher = RelationshipCommands.hug.getMatcher(input)) != null) {
            System.out.println(FriendshipController.hugPlayer(matcher.group(1).trim()).message());
            return true;
        } else if ((RelationshipCommands.showAllFriendships.getMatcher(input)) != null) {
            System.out.println(FriendshipController.showFriendships().message());
            return true;
        } else if ((matcher = RelationshipCommands.marryRequest.getMatcher(input)) != null) {
            System.out.println(FriendshipController.askMarriage(matcher.group(1).trim(), matcher.group(2).trim()).message());
            return true;
        } else if ((matcher = RelationshipCommands.marryRespond.getMatcher(input)) != null) {
            System.out.println(FriendshipController.askMarriage(matcher.group(1).trim(), matcher.group(2).trim()).message());
            return true;
        } else {
            return false;
        }
    }

    private boolean NPCCheck(String input) {
        Matcher matcher;
        if ((matcher = NpcCommands.MeetNPC.getMatcher(input)) != null) {
            try {
                System.out.println(NpcController.meetNPC(matcher.group(1).trim()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return true;
        } else if ((matcher = NpcCommands.GiftNPC.getMatcher(input)) != null) {
            System.out.println(NpcController.giftNPC(matcher.group(1).trim(), matcher.group(2).trim()).getMessage());
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
            System.out.println(NpcController.finishingQuest(matcher.group(1).trim()).getMessage());
            return true;
        } else {
            return false;
        }
    }
}


