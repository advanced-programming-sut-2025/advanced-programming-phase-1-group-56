package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Player;
import model.Result;
import model.States.Energy;

public class EnergyController extends CommandController {
    public static Result energyShow(){
        Energy energy = App.getCurrentUser().getCurrentGame().getCurrentPlayer().getEnergy();
        return new Result(true,"your current energy : "+(energy.isUnlimited()?"∞/∞":
                energy.getEnergy()+" / "+energy.getMaxEnergy()));
    }

    //CHEAT
    public static Result cheatEnergySet(String energyStr){
        try{
            int energy = Integer.parseInt(energyStr.trim());
            Player player =App.getCurrentUser().getCurrentGame().getCurrentPlayer();
            player.getEnergy().setEnergy(energy);
        }
        catch(NumberFormatException e){
            return new Result(false, "Invalid energy value");
        }
        return null;
    }

    public static Result toggleUnlimitedEnergy(){
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        player.toggleUnlimitedEnergy();
        return new Result(true, "your energy is now:" + (player.getEnergy().isUnlimited()?"unlimited":"limited"));
    }

    public void GhashKardan(){

    }
}
