package io.src.model.GameObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.src.StardewValley;
import io.src.controller.GameMenuController.HusbandryController;
import io.src.model.App;
import io.src.model.Clickable;
import io.src.model.Enums.Animals.AnimalType;
import io.src.model.Enums.Direction;
import io.src.model.Enums.GameLocationType;
import io.src.model.Enums.Items.EtcType;
import io.src.model.Enums.TileType;
import io.src.model.MapModule.AStarPathFinding;
import io.src.model.MapModule.Buildings.AnimalHouse;
import io.src.model.MapModule.Buildings.Barn;
import io.src.model.MapModule.Buildings.Building;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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

    public GameLocation getGameLocation() {
        return gameLocation;
    }

    private GameLocation gameLocation;
    private List<Position> movePoints;
    private LocalDateTime lastPettingTime = LocalDateTime.now();
    private boolean petHint = false;
    private LocalDateTime lastFeedingTime = LocalDateTime.now();
    private boolean feedHint = false;

    public Animal(Position position, String name, AnimalType animalInfo, AnimalHouse house) {
        super(position, true);
        this.nickName = name;
        this.animalInfo = animalInfo;
        this.isFed = false;
        this.isCaressed = false;
        this.goOut = false;
        this.produce = 0;
        this.movePoints = house.getListOfPathNodes();
        this.house = house;
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }

    // ---------- initializePaths ----------
    public void initializePaths(GameLocation gameLocation) {
        if (gameLocation == null) return;

        // پاک‌سازی وضعیت قبلی
        this.gameLocation = gameLocation;
        precomputedPaths.clear();
        pathListIndex = 0;
        pathIndex = 0;
        currentPath = null;

        if (movePoints == null || movePoints.isEmpty()) {
            Gdx.app.log("Animal", "initializePaths: no movePoints for " + getName());
            return;
        }

        // پیدا کردن startNode به صورت ایمن؛ اگر pos خارج از bounds بود آن را clamp کن
        Position startPos = getPosition();
        // چک bounds برای بازیابی یک تایل معتبر
        if (!gameLocation.isWithinBounds((int) startPos.getX(), (int) startPos.getY(), 1, 1)) {
            // clamp به بازهٔ معتبر
            int maxX = gameLocation.getWidth() - 1;
            int maxY = gameLocation.getHeight() - 1;
            int clampedX = MathUtils.clamp((int) startPos.getX(), 0, Math.max(0, maxX));
            int clampedY = MathUtils.clamp((int) startPos.getY(), 0, Math.max(0, maxY));
            Gdx.app.log("Animal", "startPos out of bounds, clamping " + startPos + " -> " + clampedX + "," + clampedY);
            startPos.setX(clampedX);
            startPos.setY(clampedY);
            setPosition(startPos);
            setPixelPosition(new Vector2(clampedX * TILE_SIZE, clampedY * TILE_SIZE));
        }

        Node startNode = gameLocation.getTileByPosition(this.getPosition());
        if (startNode == null) {
            Gdx.app.log("Animal", "initializePaths: startNode is null for " + this.getName());
            return;
        }

        // برای هر waypoint مسیر A* بساز و فقط مسیرهای معتبر را اضافه کن
        Node nodeStart = startNode;
        for (Position wp : movePoints) {
            if (!gameLocation.isWithinBounds((int) wp.getX(), (int) wp.getY(), 1, 1)) {
                Gdx.app.log("Animal", "Skipping movePoint out of bounds: " + wp + " for " + getName());
                continue;
            }
            Node endNode = gameLocation.getTileByPosition(wp);
            if (endNode == null) {
                Gdx.app.log("Animal", "Skipping movePoint with null tile: " + wp);
                continue;
            }
            List<Node> path = new AStarPathFinding(gameLocation, nodeStart, endNode).solve();
            if (path == null || path.isEmpty()) {
                Gdx.app.log("Animal", "No path found from " + nodeStart + " to " + wp + " for " + getName());
                // ادامه بده اما مسیر را اضافه نکن
            } else {
                precomputedPaths.add(path);
                nodeStart = endNode;
            }
        }

        if (!precomputedPaths.isEmpty()) {
            // شروع مسیر اول
            pathListIndex = 0;
            pathIndex = 0;
            currentPath = precomputedPaths.get(0);
            isPaused = true;
            pauseTimer = 0f;
        } else {
            Gdx.app.log("Animal", "initializePaths: no valid precomputedPaths for " + getName());
        }
    }

//    public void initializePaths(GameLocation gameLocation) {
//        this.gameLocation = gameLocation;
////        List<Position> movePoints = animalInfo.getPathPoints();
//        // مسیر از نقطه شروع NPC به هر waypoint
//        Node startNode = gameLocation.getTileByPosition(this.getPosition());
//        Node endNode = null;
//        for (Position wp : movePoints) {
//            endNode = gameLocation.getTileByPosition(wp);
//            List<Node> path = new AStarPathFinding(gameLocation, startNode, endNode).solve();
//            precomputedPaths.add(path);
//            startNode = gameLocation.getTileByPosition(wp);
//        }
//        // آماده‌سازی مسیر اول
//        advanceToNextPath();
//    }

//    private void advanceToNextPath() {
//        // مکث بعد از هر مسیر
//        isPaused = true;
//        pauseTimer = 0;
//        // انتخاب مسیر بعدی
//        currentPath = precomputedPaths.get(pathListIndex);
//
//        pathListIndex = (pathListIndex + 1) % precomputedPaths.size();
//        pathIndex = 0;
//    }

    private void advanceToNextPath() {
        if (precomputedPaths.isEmpty()) {
            currentPath = null;
            return;
        }
        currentPath = precomputedPaths.get(pathListIndex);
        pathListIndex = (pathListIndex + 1) % precomputedPaths.size();
        pathIndex = 0;
        isPaused = true;
        pauseTimer = 0f;
    }

    // ---------- update (چند اصلاح کوچک) ----------
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

        // اگر مسیری وجود ندارد سعی کن به advance بروی (ممکن است هنوز هیچ precomputed نداشته باشیم)
        if (currentPath == null || pathIndex >= currentPath.size()) {
            advanceToNextPath();
            // اگر بعد از advance هم هیچ مسیری نیست، nothing to do
            if (currentPath == null || currentPath.isEmpty()) return;
        }

        Node nextTile = currentPath.get(pathIndex);
        if (!(nextTile instanceof Tile)) {
            // safety: اگر node یک Tile نیست، skip کن
            pathIndex++;
            return;
        }
        Tile nextTileCast = (Tile) nextTile;
        Vector2 targetPos = new Vector2(nextTileCast.getPosition().getX() * TILE_SIZE,
            nextTileCast.getPosition().getY() * TILE_SIZE);

        Vector2 currentPos = getPixelPosition();

        // قبل از پاک‌کردن fixedObject قبلی، چک bounds و اینکه واقعاً این تایل ما را نگه داشته
        Position curTilePos = new Position((int) Math.floor(currentPos.x / TILE_SIZE),
            (int) Math.floor(currentPos.y / TILE_SIZE));
        if (gameLocation.isWithinBounds((int) curTilePos.getX(), (int) curTilePos.getY(), 1, 1)) {
            Tile curTile = gameLocation.getTileByPosition(curTilePos);
            if (curTile != null && curTile.getFixedObject() == this) {
                curTile.setFixedObject(null);
            }
        }

        // بردار حرکت و جهت
        Vector2 direction = targetPos.cpy().sub(currentPos);
        if (direction.len() == 0) {
            pathIndex++;
            return;
        }
        direction.nor();

        if (Math.abs(direction.x) > Math.abs(direction.y)) {
            setMovingDirection(direction.x > 0 ? Direction.RIGHT : Direction.LEFT);
        } else {
            setMovingDirection(direction.y > 0 ? Direction.UP : Direction.DOWN);
        }
        setVelocity(direction.x * getSpeed(), direction.y * getSpeed());

        // جابجایی پیکسلی
        Vector2 newPos = currentPos.cpy().add(getVelocity().cpy().scl(delta));
        setPixelPosition(newPos);

        // setFixedObject روی تایل جدید، اما قبل چک bounds
        Position newTilePos = new Position((int) Math.floor(newPos.x / TILE_SIZE),
            (int) Math.floor(newPos.y / TILE_SIZE));
        if (gameLocation.isWithinBounds((int) newTilePos.getX(), (int) newTilePos.getY(), 1, 1)) {
            Tile newTile = gameLocation.getTileByPosition(newTilePos);
            if (newTile != null) {
                newTile.setFixedObject(this);

                if (newTile.getTileType() == TileType.Wrapper) {
                    newTile.setFixedObject(null);

                    // اگر الان داخلِ indoor هستیم -> انتقال به farm
                    if (gameLocation.getType() == GameLocationType.Barn_Indoor || gameLocation.getType() == GameLocationType.Coop_Indoor) {

                        // 1) remove from indoor location
                        gameLocation.removeGameObject(this);

                        // 2) set new gameLocation to farm
                        GameLocation farm = App.getMe().getPlayerFarm();
                        this.gameLocation = farm;

                        // 3) determine farm door position (explicit API on Building recommended)
                        Position farmDoor = ((Building) house).getDoorPosition(); // farm coords of door
                        if (farmDoor == null) {
                            // fallback: place near building position
                            farmDoor = new Position(((Building) house).getPosition().getX(), ((Building) house).getPosition().getY() - 2);
                        }

                        // 4) teleport the logical position and pixel position to farmDoor
                        setPosition(new Position(farmDoor.getX(), farmDoor.getY()-2));
//                        setPixelPosition(new Vector2(farmDoor.getX() * TILE_SIZE, farmDoor.getY() * TILE_SIZE));

                        // 5) add to farm gameLocation
                        farm.getTileByPosition(getPosition()).setFixedObject(this);
                        farm.addGameObject(this);

                        setGoOut(true);

                        // 6) now set the next movePoints appropriate for farm (for example pasture or roaming points)
                        //    You should have a method on Building that returns outside move-points (outside waypoints).
//                        List<Position> outsideTargets = ((Building) house).getOutsideMovePoints();
//                        if (outsideTargets == null || outsideTargets.isEmpty()) {
                            // fallback: a sensible default relative to door
//                            outsideTargets = List.of(new Position(getPosition().getX() - 3, getPosition().getY()));
//                        }
                        setMovePoints(List.of(new Position(getPosition().getX() - 3, getPosition().getY())));

                        // 7) ensure old precomputedPaths are cleared and recalc on new location
                        precomputedPaths.clear();
                        initializePaths(farm);
                        return;
                    } else {
                        // wrapper on farm -> means animal wants to go inside
                        gameLocation.removeGameObject(this);
                        GameLocation indoorLoc = ((Building) house).getIndoor();
                        this.gameLocation = indoorLoc;

                        // set indoor logical position (usually a fixed spawn inside)
//                        Position indoorSpawn = ((Building) house).getIndoorSpawnPosition(); // you should provide this
//                        if (indoorSpawn == null) indoorSpawn = new Position(7,3);
                        setPosition( new Position(7,3));
//                        setPixelPosition(new Vector2(indoorSpawn.getX() * TILE_SIZE, indoorSpawn.getY() * TILE_SIZE));

                        indoorLoc.getTileByPosition(getPosition()).setFixedObject(this);
                        indoorLoc.addGameObject(this);

                        setGoOut(false);
                        setMovePoints(house.getListOfPathNodes());

                        precomputedPaths.clear();
                        initializePaths(indoorLoc);
                        return;
                    }
                }
                // بررسی wrapper
//                if (newTile.getTileType() == TileType.Wrapper) {
//                    newTile.setFixedObject(null);
//                    // اگر داخل فضای داخلی (barn/coop indoor) هستیم، بیرون ببریم و بالعکس
//                    if (gameLocation.getType() == GameLocationType.Barn_Indoor || gameLocation.getType() == GameLocationType.Coop_Indoor) {
//                        // انتقال به farm
//                        gameLocation.removeGameObject(this);
//                        gameLocation = App.getMe().getPlayerFarm();
//                        setGoOut(true);
//
//                        // set logical position to door of building (یا نقطه‌ای مناسب)
//
//                        setPosition(new Position(((Building) house).getPosition().getX(),((Building) house).getPosition().getY() - 2 ));
//                        setMovePoints(List.of(new Position(getPosition().getX()-3,getPosition().getY() - 2 )));
//
////                        precomputedPaths.clear();
////                        getPosition().setX(((Building) house).getPosition().getX());
////                        getPosition().setY(((Building) house).getPosition().getY() - 2);
//                        gameLocation.getTileByPosition(getPosition()).setFixedObject(this);
//                        gameLocation.addGameObject(this);
//
//                        // نقاط بعدی باید برای farm باشد
//                        precomputedPaths.clear();
//                        initializePaths(gameLocation);
//                        return;
//                    } else {
//                        // wrapper در farm => می‌خواد وارد indoor شود
//                        gameLocation.removeGameObject(this);
//                        gameLocation = ((Building) house).getIndoor();
//                        getPosition().setX(7);
//                        getPosition().setY(3);
//                        gameLocation.getTileByPosition(new Position(getPosition().getX(), getPosition().getY())).setFixedObject(this);
//                        setGoOut(false);
//                        gameLocation.addGameObject(this);
//                        setMovePoints(house.getListOfPathNodes());
//                        precomputedPaths.clear();
//                        initializePaths(gameLocation);
//                        return;
//                    }
//                }
            }
        }

        // چک رسیدن به مرکز تایل
        if (currentPos.dst(targetPos) < getSpeed() * delta) {
            pathIndex++;
        }
    }




//    @Override
//    public void update(float delta) {
//        if (isPaused) {
//            pauseTimer += delta;
//            if (pauseTimer >= pauseDuration) {
//                isPaused = false;
//            } else {
//                return;
//            }
//        }
//        if (currentPath == null || pathIndex >= currentPath.size()) {
//            advanceToNextPath();
//            return;
//        }
//
//        // حرکت روان بر اساس currentPath
//        Node nextTile = currentPath.get(pathIndex);
//        Vector2 targetPos = new Vector2(((Tile) nextTile).getPosition().getX() * TILE_SIZE,
//            ((Tile) nextTile).getPosition().getY() * TILE_SIZE);
//        Vector2 currentPos = getPixelPosition();  // مختصات پیکسل NPC
//        gameLocation.getTileByPosition(new Position(currentPos.x / TILE_SIZE, currentPos.y / TILE_SIZE)).setFixedObject(null);
//        // بردار حرکت و تنظیم جهت
//        Vector2 direction = targetPos.cpy().sub(currentPos).nor();
//        if (Math.abs(direction.x) > Math.abs(direction.y)) {
//            setMovingDirection(direction.x > 0 ? Direction.RIGHT : Direction.LEFT);
//        } else {
//            setMovingDirection(direction.y > 0 ? Direction.UP : Direction.DOWN);
//        }
//        setVelocity(direction.x * getSpeed(), direction.y * getSpeed());
//
//        // جابجایی پیکسلی
//        setPixelPosition(currentPos.add(getVelocity().cpy().scl(delta)));
//        gameLocation.getTileByPosition(new Position(getPixelPosition().x / TILE_SIZE, getPixelPosition().y / TILE_SIZE)).setFixedObject(this);
//        if (gameLocation.getTileByPosition(new Position(getPixelPosition().x / TILE_SIZE, getPixelPosition().y / TILE_SIZE)).getTileType() == TileType.Wrapper) {
//            if (gameLocation.getType() == GameLocationType.Barn_Indoor || gameLocation.getType() == GameLocationType.Coop_Indoor) {
//                // انتقال به farm
//                gameLocation.removeGameObject(this);
//                gameLocation = App.getMe().getPlayerFarm();
//                setGoOut(true);
//
//                // set logical position to door of building (یا نقطه‌ای مناسب)
//
//                setPosition(new Position(((Building) house).getPosition().getX(),((Building) house).getPosition().getY() - 2 ));
//                setMovePoints(List.of(new Position(getPosition().getX()-3,getPosition().getY() - 2 )));
//
////                        precomputedPaths.clear();
////                        getPosition().setX(((Building) house).getPosition().getX());
////                        getPosition().setY(((Building) house).getPosition().getY() - 2);
//                gameLocation.getTileByPosition(getPosition()).setFixedObject(this);
//                gameLocation.addGameObject(this);
//
//                // نقاط بعدی باید برای farm باشد
//                precomputedPaths.clear();
//                initializePaths(gameLocation);
//                return;
//            } else {
//                // wrapper در farm => می‌خواد وارد indoor شود
//                gameLocation.removeGameObject(this);
//                gameLocation = ((Building) house).getIndoor();
//                getPosition().setX(7);
//                getPosition().setY(3);
//                gameLocation.addGameObject(this);
//                gameLocation.getTileByPosition(new Position(getPosition().getX(), getPosition().getY())).setFixedObject(this);
//                setGoOut(false);
//                setMovePoints(house.getListOfPathNodes());
//                precomputedPaths.clear();
//                initializePaths(gameLocation);
//                return;
//            }
////            if (gameLocation.getType() == GameLocationType.Barn_Indoor || gameLocation.getType() == GameLocationType.Coop_Indoor){
////                gameLocation.removeGameObject(this);
////                gameLocation = App.getMe().getPlayerFarm();
////                getPosition().setX(((Building)house).getPosition().getX());
////                getPosition().setY(((Building)house).getPosition().getY() -2);
////                gameLocation.getTileByPosition(new Position(getPosition().getX() , getPosition().getY())).setFixedObject(this);
////                setGoOut(true);
////                setMovePoints(List.of(new Position(getPosition().getX()-3 , getPosition().getY()-2)));
////                initializePaths(gameLocation);
////                return;
////            } else {
////                gameLocation.removeGameObject(this);
////                gameLocation = ((Building)house).getIndoor();
////                getPosition().setX(7);
////                getPosition().setY(3);
////                gameLocation.getTileByPosition(new Position(getPosition().getX() , getPosition().getY())).setFixedObject(this);
////                setGoOut(false);
////                setMovePoints(house.getListOfPathNodes());
////                initializePaths(gameLocation);
////                return;
////            }
//        }
//        // رسیدن به مرکز تایل
//        if (currentPos.dst(targetPos) < getSpeed() * delta) {
//            pathIndex++;
//        }
//    }

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


    public List<Position> getMovePoints() {
        return movePoints;
    }

    public void setMovePoints(List<Position> movePoints) {
        this.movePoints = movePoints;
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
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

    public LocalDateTime getLastPettingTime() {
        return lastPettingTime;
    }

    public void setLastPettingTime(LocalDateTime lastPettingTime) {
        this.lastPettingTime = lastPettingTime;
    }

    public boolean isPetHint() {
        return petHint;
    }

    public void setPetHint(boolean petHint) {
        this.petHint = petHint;
    }

    public LocalDateTime getLastFeedingTime() {
        return lastFeedingTime;
    }

    public void setLastFeedingTime(LocalDateTime lastFeedingTime) {
        this.lastFeedingTime = lastFeedingTime;
    }

    public boolean isFeedHint() {
        return feedHint;
    }

    public void setFeedHint(boolean feedHint) {
        this.feedHint = feedHint;
    }
}
