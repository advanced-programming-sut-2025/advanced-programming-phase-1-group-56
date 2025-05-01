package view;

import model.Enums.commands.GameCommands.GameCommands;
import model.Enums.commands.GameCommands.HouseMenuCommands;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();

        if (GameCheck(input) ||
                DateTimeCheck(input) ||
                WeatherCheck(input) ||
                MapCheck(input) ||
                EnergyAndSkillsCheck(input) ||
                ToolsCheck(input) ||
                FarmingCheck(input) ||
                CraftingCheck(input) ||
                HusbandryCheck(input) ||
                ArtisanCheck(input) ||
                TradeCheck(input) ||
                RelationShipCheck(input) ||
                NPCCheck(input)
        ) {

            return;
        } else {
            System.out.println("invalid command");
        }


    }

    public boolean GameCheck(String input) {
        Matcher matcher;
        if((matcher = GameCommands.eat.getMatcher(input)).find()) {
            System.out.println(matcher);
        }
    }

}


