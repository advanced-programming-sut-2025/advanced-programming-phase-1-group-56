package io.src.controller.GameMenuController;

import io.src.controller.CommandController;
import io.src.model.App;
import io.src.model.Enums.Direction;
import io.src.model.Enums.Items.FishType;
import io.src.model.Enums.Items.ItemQuality;
import io.src.model.Enums.Items.ToolMaterial;
import io.src.model.Enums.Items.ToolType;
import io.src.model.Enums.Skills;
import io.src.model.Enums.TileType;
import io.src.model.GameObject.ArtesianMachine;
import io.src.model.MapModule.Tile;
import io.src.model.Player;
import io.src.model.Result;
import io.src.model.items.Fish;
import io.src.model.items.Item;
import io.src.model.items.Tool;
import io.src.model.skills.Skill;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class FishingController extends CommandController {
    public static Result fishing(Matcher matcher) {
        String fishingPoleName = matcher.group(1).trim();
        Item item = App.getMe().getInventory().findItemByName(fishingPoleName);
        if (item == null) {
            return new Result(false , "you don't have a this fishing pole");
        }
        if (App.getMe().getCurrentItem() != item) {
            return new Result(false , "you do");
        }
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                if (App.getMe().getCurrentGameLocation().getTileByPosition((int)App.getMe().getPosition().getX() - 1 + i, (int)App.getMe().getPosition().getY() - 1 + j).getTileType() == TileType.Water) {
                    ((Tool) App.getMe().getCurrentItem()).use(App.getMe().getCurrentGameLocation().getTileByPosition((int)App.getMe().getPosition().getX() - 1 + i, (int)App.getMe().getPosition().getY() - 1 + j));
                    return new Result(true , "you have use your fishing pole");
                }
            }
        }

        return new Result(false , "you aren't near any water tile");
    }

    public static Fish catchFish(Direction dir) {
        Player player = App.getMe();
        Tile tile;
        switch (dir) {
            case Direction.UP: {
                tile = player.getCurrentGameLocation().getTileByPosition((int) player.getPosition().getX(), (int) player.getPosition().getY() + 1);
            }
            break;
            case Direction.DOWN: {
                tile = player.getCurrentGameLocation().getTileByPosition((int) player.getPosition().getX(), (int) player.getPosition().getY() - 1);
            }
            break;
            case Direction.LEFT: {
                tile = player.getCurrentGameLocation().getTileByPosition((int) player.getPosition().getX() - 1, (int) player.getPosition().getY());
            }
            break;
            case Direction.RIGHT: {
                tile = player.getCurrentGameLocation().getTileByPosition((int) player.getPosition().getX() + 1, (int) player.getPosition().getY());
            }
            break;
            default: {
                tile = player.getCurrentGameLocation().getTileByPosition((int) player.getPosition().getX(), (int) player.getPosition().getY());

            }
        }
        ToolType toolType = ((Tool)player.getCurrentItem()).getToolType();
        Skill playerSkill = player.getSkillByName(Skills.Fishing.toString());
        if (tile.getTileType() == TileType.Water) {
            int rand = (int) (Math.random() * ItemQuality.values().length);
//            int quantity = (int) (Math.random() * App.getCurrentUser().getCurrentGame().getWeatherState().getEnergyMultiplier() * (playerSkill.getLevel() + 2));
            if (toolType.getToolMaterial() == ToolMaterial.Training) {
                FishType fishType = FishType.getCheapestFishOfSeason(App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason());
                Fish fish = new Fish(fishType);
                fish.setItemQuality(ItemQuality.values()[rand]);
                return fish;
//                player.getInventory().add(new Fish(fishType), quantity);
            } else {
                ArrayList<FishType> seasonFishes = FishType.getSeasonFishes(App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason());
//                player.getInventory().add(new Fish(seasonFishes.get((int) (Math.random() * seasonFishes.size()))), quantity);
                Fish fish = new Fish(seasonFishes.get((int) (Math.random() * seasonFishes.size())));
                fish.setItemQuality(ItemQuality.values()[rand]);
                return fish;
            }
        }
        return null;
//        player.subtractEnergy(toolType.getUsedEnergy());
//        App.getMe().getSkillByName(Skills.Fishing.toString()).setXp(App.getMe().getSkillByName(Skills.Fishing.toString()).getXp() + 5);
//        if (playerSkill.getLevel() == 3) {
//            player.addEnergy(1);
//        }
    }

    public static void Fishing(Fish fish , boolean successful) {
        Player player = App.getMe();
        ToolType toolType = ((Tool) player.getCurrentItem()).getToolType();
        Skill playerSkill = player.getSkillByName(Skills.Fishing.toString());
        player.subtractEnergy(toolType.getUsedEnergy());
        if (successful){
            player.getInventory().add(fish, 1);
            playerSkill.setXp(playerSkill.getXp() + 5);
            if (playerSkill.getLevel() == 3) {
                player.addEnergy(1);
            }
        }

    }

}
