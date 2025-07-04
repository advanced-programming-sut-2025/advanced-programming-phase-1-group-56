package io.src.controller.GameMenuController;

import io.src.controller.CommandController;
import io.src.model.App;
import io.src.model.Player;
import io.src.model.Result;
import io.src.model.States.Energy;

public class EnergyController extends CommandController {
    public static Result energyShow(){
        Energy energy = App.getCurrentUser().getCurrentGame().getCurrentPlayer().getEnergy();
        return new Result(true,"your current energy : "+(energy.isUnlimited()?"8/8 (8 = infinity)":
                energy.getEnergy()+" / "+energy.getMaxEnergy()));
    }

    //CHEAT
    public static Result cheatEnergySet(String energyStr){
        try{
            double energy = Double.parseDouble(energyStr.trim());
            Player player =App.getCurrentUser().getCurrentGame().getCurrentPlayer();
            if(energy> App.getMe().getMaxEnergy()){
                return new Result(false,"you can't cheat more than max energy");
            }
            player.getEnergy().setEnergy(Math.min(energy, player.getEnergy().getMaxEnergy()));
            return new Result(true,"Energy cheated successfully");
        }
        catch(NumberFormatException e){
            return new Result(false, "Invalid energy value");
        }
    }

    public static Result toggleUnlimitedEnergy(){
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        player.toggleUnlimitedEnergy();
        return new Result(true, "your energy is now:" + (player.getEnergy().isUnlimited()?"unlimited":"limited"));
    }

    public void GhashKardan(){
        App.getMe().setFainted(true);
    }
}
