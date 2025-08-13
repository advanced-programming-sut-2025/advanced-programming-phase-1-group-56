package io.src.model.GameObject;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import io.src.StardewValley;
import io.src.controller.GameMenuController.HusbandryController;
import io.src.model.App;
import io.src.model.Clickable;
import io.src.model.Enums.Animals.AnimalType;
import io.src.model.Enums.Direction;
import io.src.model.Enums.Items.EtcType;
import io.src.model.MapModule.AStarPathFinding;
import io.src.model.MapModule.Buildings.AnimalHouse;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.Node;
import io.src.model.MapModule.Position;
import io.src.model.MapModule.Tile;
import io.src.model.SkinManager;
import io.src.model.TimeSystem.DateTime;
import io.src.model.TimeSystem.TimeObserver;
import io.src.model.items.AnimalProduct;
import io.src.model.items.Saleable;
import io.src.view.InnerMenus.AnimalMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.src.view.GameMenus.GameView.TILE_SIZE;

public class Animal extends LivingEntity implements Saleable, TimeObserver, Clickable, SensitiveToPlayer {
    private AnimalType animalInfo;
    private final ArrayList<AnimalProduct> dailyProducts = new ArrayList<>();
    private int friendship = 0;
    private boolean isFed;
    private boolean isCaressed;
    private String nickName;
    private boolean goOut;
    private AnimalHouse house;
    private int produce;
    private final List<List<Node>> precomputedPaths = new ArrayList<>();  // لیست مسیرهای پیش‌محاسبه
    private int pathListIndex = 0;               // ایندکس مسیر فعلی
    private List<Node> currentPath;              // مسیر کنونی نودها
    private int pathIndex = 0;                   // ایندکس در مسیر کنونی
    private final float pauseDuration = 1f;
    private float pauseTimer = 0f;
    private boolean isPaused = true;
    private Vector2 pixelPosition;
    private GameLocation gameLocation;
    private List<Position> movePoints;

    public Animal(Position position, String name, AnimalType animalInfo, AnimalHouse house) {
        super(position, true);
        this.nickName = name;
        this.animalInfo = animalInfo;
        this.isFed = false;
        this.isCaressed = false;
        this.goOut = false;
        this.produce = 0;
        this.movePoints = house.getListOfPathNodes();
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }

    public void initializePaths(GameLocation gameLocation) {
        this.gameLocation = gameLocation;
//        List<Position> movePoints = animalInfo.getPathPoints();
        // مسیر از نقطه شروع NPC به هر waypoint
        Node startNode = gameLocation.getTileByPosition(this.getPosition());
        Node endNode = null;
        for (Position wp : movePoints) {
            endNode = gameLocation.getTileByPosition(wp);
            List<Node> path = new AStarPathFinding(gameLocation, startNode, endNode).solve();
            precomputedPaths.add(path);
            startNode = gameLocation.getTileByPosition(wp);
        }
        // آماده‌سازی مسیر اول
        advanceToNextPath();
    }

    private void advanceToNextPath() {
        // مکث بعد از هر مسیر
        isPaused = true;
        pauseTimer = 0;
        // انتخاب مسیر بعدی
        currentPath = precomputedPaths.get(pathListIndex);

        pathListIndex = (pathListIndex + 1) % precomputedPaths.size();
        pathIndex = 0;
    }

    @Override
    public void update(float delta) {
        if (isPaused) {
            pauseTimer += delta;
            if (pauseTimer >= pauseDuration) {
                isPaused = false;
            } else {
                return;
            }
        }
        if (currentPath == null || pathIndex >= currentPath.size()) {
            advanceToNextPath();
            return;
        }

        // حرکت روان بر اساس currentPath
        Node nextTile = currentPath.get(pathIndex);
        Vector2 targetPos = new Vector2(((Tile) nextTile).getPosition().getX() * TILE_SIZE,
            ((Tile) nextTile).getPosition().getY() * TILE_SIZE);
        Vector2 currentPos = getPixelPosition();  // مختصات پیکسل NPC
        gameLocation.getTileByPosition(new Position(currentPos.x / TILE_SIZE, currentPos.y / TILE_SIZE)).setFixedObject(null);
        // بردار حرکت و تنظیم جهت
        Vector2 direction = targetPos.cpy().sub(currentPos).nor();
        if (Math.abs(direction.x) > Math.abs(direction.y)) {
            setMovingDirection(direction.x > 0 ? Direction.RIGHT : Direction.LEFT);
        } else {
            setMovingDirection(direction.y > 0 ? Direction.UP : Direction.DOWN);
        }
        setVelocity(direction.x * getSpeed(), direction.y * getSpeed());

        // جابجایی پیکسلی
        setPixelPosition(currentPos.add(getVelocity().cpy().scl(delta)));
        gameLocation.getTileByPosition(new Position(getPixelPosition().x / TILE_SIZE, getPixelPosition().y / TILE_SIZE)).setFixedObject(this);

        // رسیدن به مرکز تایل
        if (currentPos.dst(targetPos) < getSpeed() * delta) {
            pathIndex++;
        }
    }

    // متد کمک‌کننده برای دریافت/تنظیم PixelPosition

    public Vector2 getPixelPosition() {
        if (pixelPosition == null) pixelPosition = new Vector2(getPosition().getX() * TILE_SIZE,
            getPosition().getY() * TILE_SIZE);
        return pixelPosition;
    }

    public void setPixelPosition(Vector2 pos) {
        this.pixelPosition = pos;
        // هماهنگ‌سازی مختصات تایل در صورت عبور
        getPosition().setX(Math.round(pos.x / TILE_SIZE));
        getPosition().setY(Math.round(pos.y / TILE_SIZE));
    }

    public void pet() {
        friendship += 15;
    }

    public int getFriendship() {
        return friendship;
    }

    public void addFriendShip(int countFriendShip) {
        friendship += countFriendShip;
    }

    public ArrayList<EtcType> getProducts() {
        return new ArrayList<>(Arrays.asList(animalInfo.getProducts()));
    }

    public ArrayList<AnimalProduct> getDailyProducts() {
        return dailyProducts;
    }

    public boolean getIsFed() {
        return isFed;
    }

    public void setFed(boolean fed) {
        isFed = fed;
    }

    public boolean getIsCaressed() {
        return isCaressed;
    }

    public void setCaressed(boolean caressed) {
        isCaressed = caressed;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void produceProductOnDay() {//TODO

    }

    public AnimalType getType() {
        return animalInfo;
    }

    public void setAnimalInfo(AnimalType animalInfo) {
        this.animalInfo = animalInfo;
    }

    public AnimalHouse getHouse() {
        return house;
    }

    public void setHouse(AnimalHouse house) {
        this.house = house;
    }

    public boolean isGoOut() {
        return goOut;
    }

    public void setGoOut(boolean goOut) {
        this.goOut = goOut;
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if (newDay) {
            if (isFed) {
                produce++;
            }
            if (produce == animalInfo.getProductionInterval()) {
                dailyProducts.clear();
                dailyProducts.add(HusbandryController.getProduct(this));
                produce = 0;
            }

            if (!isFed) {
                friendship -= 20;
            }
            if (!goOut) {
                friendship -= 20;
            }
            if (!isCaressed) {
                friendship -= 10;
            }

        }
    }

    public int getProduce() {
        return produce;
    }

    public void setProduce(int produce) {
        this.produce = produce;
    }

    public void deleteProduct(EtcType etcType) {
        if (dailyProducts.isEmpty()) {
            System.out.println("There is no product in this animal");
            return;
        }
        if (dailyProducts.get(0).getEtcType().name().equals(etcType.name())) {
            dailyProducts.remove(0);
        } else {
            System.out.println("There is no" + etcType.name + "in this animal");
        }
    }


    @Override
    public String getName() {
        return nickName;
    }

    @Override
    public String getAssetName() {
        //TODO
        return "";
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer,int button){
        if (button == Input.Buttons.RIGHT){
            AnimalMenu animalMenu = new AnimalMenu(SkinManager.getInstance().getSkin(SkinManager.MAIN_SKIN), this);
            animalMenu.setVisible(true);
            StardewValley.getGameView().getStage().addActor(animalMenu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onPlayerGoesNearby(float distance) {

        return false;
    }

    @Override
    public boolean onPlayerGetsFar(float distance) {
        return false;
    }

    @Override
    public boolean onPlayerFocus() {
        isPaused = true;
        pauseTimer = Integer.MAX_VALUE;
        return false;
    }

    @Override
    public boolean onPlayerDefocus() {
        isPaused = false;
        pauseTimer = 1;
        return false;
    }

    @Override
    public float getSensitivityDistance() {
        return 4;
    }
}
