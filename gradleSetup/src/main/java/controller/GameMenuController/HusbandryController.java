package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.WeatherAndTime.WeatherType;

import model.GameObject.Animal;
import model.items.AnimalProduct;
import model.MapModule.Position;
import model.Result;

import java.util.regex.Matcher;

public class HusbandryController extends CommandController {
    public static Result buildCoopOrBarn(Matcher matcher) {
        String buildingName = matcher.group(1);
        int x = Integer.parseInt(matcher.group(2));
        int y = Integer.parseInt(matcher.group(3));

        //TODO check to can it build in this place
        if(buildingName.equals("Barn")){
            //TODO check have ingredients
            //TODO check have player money to purchase
        }else if(buildingName.equals("Coop")){
            //TODO check have ingredients
            //TODO check have player money to purchase

        }
        return new Result(true,"this building has been built");
    }

    public static Result manageBuyAnimal(Matcher matcher) {
        String animalName = matcher.group(1);
        String nickName = matcher.group(2);
        Animal animal = returnAnimal(animalName);
        //TODO hava space in Coop or Barn
        if(!isUniqueAnimalNickName(nickName)){
            return new Result(false,"you have already built this animal with that name");
        }
        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getAnimals()
                .add(animal);
        return new Result(true,"you buy this animal");
    }

    public static Result petting(Matcher matcher) {
        String name = matcher.group(1);

        Animal animal = returnAnimal(name);
        for(Animal animal2 : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()){
            if(animal2.getName().equals(name)){
                animal2.addFriendShip(15);
            }
        }
        return new  Result(true,"you petting "+ animal.getName());
    }

    public static Result showInfoOfAnimal() {
        StringBuilder tmpString = new StringBuilder();
        for (Animal animal : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            tmpString.append("animal's Name : " + animal.getName() + "\n" + "your friendShip with him : " + animal.getFriendship())
                    .append("\n")
                    .append("cuddled or not : "+ animal.getIsCaressed())
                    .append("\n")
                    .append("eaten or not : "+ animal.getIsFed()).append("\n");

        }
        return null;
    }

    public static Result shepherdAnimals(Matcher matcher) {
        String animalName = matcher.group(1);
        int  x = Integer.parseInt(matcher.group(2));
        int  y = Integer.parseInt(matcher.group(3));
        Animal animal = returnAnimal(animalName);
        if (App.getCurrentUser()
                .getCurrentGame()
                .getWeatherState()
                .getTodayWeather() == WeatherType.Snow
        ||App.getCurrentUser()
                .getCurrentGame()
                .getWeatherState()
                .getTodayWeather() == WeatherType.Storm
        ||App.getCurrentUser()
                .getCurrentGame()
                .getWeatherState()
                .getTodayWeather() == WeatherType.Rainy){
            return new Result (true,"your animal can't go out in this weather!");
        } else if (animal == null) {
            return new  Result (false,"there is no animal with that name");
        }
        //TODO check to can it build in this place

        for(Animal animal2 : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()){
            if(animal2.getName().equals(animalName)){
                animal2.setPosition(new Position(x,y));
            }
        }
        return new  Result (true,"your animal has go out in this weather!");
    }

    public static Result feedHay(Matcher matcher) {
        String animalName = matcher.group(1);
        Animal animal = returnAnimal(animalName);

        if (animal == null) {
            return  new  Result (false,"there is no animal with that name");
        }

        for(Animal animal2 : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()){
            if(animal2.getName().equals(animalName)){
                animal2.setFed(true);//TODO on day changed
            }
        }
        return null;
    }

    public static Result showProduces() {
        StringBuilder tmpString = new StringBuilder();
        for (Animal animal  : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            if(animal.getProducts().isEmpty()){
                continue;
            }else {
                tmpString.append("animal's Name : " + animal.getName() + "\n");
                for(AnimalProduct animalProduct : animal.getProducts()){
                    tmpString.append("animal's Product Name : " + animalProduct.getName() + "\n")
                            .append("animal's Product quality : " + animalProduct.getQuality()+"\n");
                }
                tmpString.append("------------------------------");
            }
        }
        if (tmpString.isEmpty()) {
            tmpString.append("there is no animal's products ! yoho");
        }
        return new Result (true,tmpString.toString());
    }

    public static Result collectProduce(Matcher matcher) {
        String userName = matcher.group(1);
        Animal animal = returnAnimal(userName);
        //TODO
        return null;
    }

    public static Result sellAnimal(Matcher matcher) {
        String userName = matcher.group(1);
        Animal animal = returnAnimal(userName);
        if(animal == null){
            return  new  Result (false,"there is no animal with that name");
        }
        int sellPrice = (int) (animal.getAnimalInfo().getPrice()*(animal.getFriendship()/1000+0.3));
        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getAnimals()
                .remove(animal);
        return null;
    }
    //CHEAT

    public static Result cheatSetFriendship(){

        return null;
    }

    private static Animal returnAnimal(String animalName){
        for (Animal animal : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            if(animal.getName().equals(animalName)){
                return animal;
            }
        }
        return null;
    }
    private static boolean isUniqueAnimalNickName(String uniqueAnimalName){
        for (Animal animal : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getAnimals()) {
            if(animal.getNickName().equals(uniqueAnimalName)){
                return false;
            }
        }
        return true;
    }



}
