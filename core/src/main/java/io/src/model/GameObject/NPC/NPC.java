package io.src.model.GameObject.NPC;

import com.badlogic.gdx.math.Vector2;
import io.src.model.Enums.Direction;
import io.src.model.Enums.NpcType;
import io.src.model.MapModule.AStarPathFinding;
import io.src.model.MapModule.GameLocations.Town;
import io.src.model.MapModule.Node;
import io.src.model.MapModule.Position;
import io.src.model.GameObject.LivingEntity;
import io.src.model.MapModule.Tile;
import io.src.model.Player;
import io.src.model.TimeSystem.DateTime;
import io.src.model.TimeSystem.TimeObserver;
import io.src.model.items.Item;

import java.util.ArrayList;
import java.util.List;

import static io.src.view.GameMenus.GameView.TILE_SIZE;

public class NPC extends LivingEntity implements TimeObserver {

    private final NpcType type;
    private final ArrayList<NpcFriendship> friendships = new ArrayList<>();
    private final List<List<Node>> precomputedPaths = new ArrayList<>();  // لیست مسیرهای پیش‌محاسبه
    private int pathListIndex = 0;               // ایندکس مسیر فعلی
    private List<Node> currentPath;              // مسیر کنونی نودها
    private int pathIndex = 0;                   // ایندکس در مسیر کنونی
    private final float pauseDuration = 1f;      // مدت مکث روی هر نقطه
    private float pauseTimer = 0f;               // تایمر مکث
    private boolean isPaused = true;             // شروع در حالت مکث تا آماده‌سازی مسیر
    private Town town;

    public NPC(Position position, NpcType type) {
        super(position, false);
        this.type = type;
    }

    /**
     * فراخوانی بعد از اینکه town ست شد؛ مسیرها را یکبار محاسبه می‌کند
     */
    public void initializePaths(Town town) {
        this.town = town;
        List<Position> movePoints = type.getPathPoints();
        // مسیر از نقطه شروع NPC به هر waypoint
        Node startNode = town.getTileByPosition(this.getPosition());
        Node endNode = null;
        for (Position wp : movePoints) {
            endNode = town.getTileByPosition(wp);
            List<Node> path = new AStarPathFinding(town, startNode, endNode).solve();
            precomputedPaths.add(path);
            startNode = town.getTileByPosition(wp);
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
        Vector2 targetPos = new Vector2(((Tile)nextTile).getPosition().getX() * TILE_SIZE,
            ((Tile)nextTile).getPosition().getY() * TILE_SIZE);
        Vector2 currentPos = getPixelPosition();  // مختصات پیکسل NPC

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

        // رسیدن به مرکز تایل
        if (currentPos.dst(targetPos) < getSpeed() * delta) {
            pathIndex++;
        }
    }

    // متد کمک‌کننده برای دریافت/تنظیم PixelPosition
    private Vector2 pixelPosition;
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
//    private final NpcType type;
//    private final ArrayList<NpcFriendship> friendships = new ArrayList<>();
//    private final Queue<Node> path;          // صف نقاطی که باید برود
//    private final float pauseDuration;           // مدت مکث روی هر نقطه
//    private float pauseTimer = 0f;               // تایمر مکث
//    private boolean isPaused = false;            // آیا الان در حالت مکث است؟
//    private List<Node> currentPath;          // لیست tileهایی که A* محاسبه کرده
//    private int pathIndex = 0;                   // ایندکس مرحله در currentPath
//    private Town town;
//
//    public void setTown(Town town) {
//        this.town = town;
//        setMovePoint(type.getPathPoints());
//    }
//
//
//    public NPC(Position position, NpcType type) {
//        super(position, false);
//        this.type = type;
//        path = new LinkedList<>();
//        pauseDuration = 3;
////        town = App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown();
//    }
//
//    public void setMovePoint(List<Position> movePoints){
//        for (Position movePoint : movePoints) {
//            path.add(town.getTileByPosition(movePoint));
//        }
//    }
//
    public NpcType getType() {
        return type;
    }
//
//
    public ArrayList<NpcFriendship> getFriendships() {
        return friendships;
    }

    public void addFriendship(NpcFriendship friendship) {
        friendships.add(friendship);
    }

    public NpcFriendship findFriendshipByPlayer(Player player) {
        for (NpcFriendship friendship : friendships) {
            if (friendship.getPlayer().equals(player)) {
                return friendship;
            }
        }
        NpcFriendship friendship = new NpcFriendship(player, this);
        friendships.add(friendship);
        player.getNpcFriendShips().add(friendship);
        return friendship;
    }
//
//    private Vector2 tileToPixel(Position tilePos) {
//        float pixelX = tilePos.getX() * TILE_SIZE;
//        float pixelY = tilePos.getY() * TILE_SIZE;
//        return new Vector2(pixelX, pixelY);
//    }
//
//    private void advanceToNextTarget() {
//        // وقتی به یک WayPoint رسید، pop کن و مسیر A* تا موقعیت بعدی را محاسبه کن
//        Node wp = path.poll();
//        path.offer(wp); // اگر دایره‌ای باشه دوباره به ته صف اضافه کن
//        currentPath = new AStarPathFinding(town, town.getTileByPosition(Math.round(this.getPosition().getX()) , Math.round(this.getPosition().getY())),  wp ).solve();
//        pathIndex = 0;
//        isPaused = false;
//    }
//
//    public void update(float delta) {
//        if (isPaused) {
//            // در حالت مکث، تا پایان pauseDuration صبر کن
//            pauseTimer += delta;
//            if (pauseTimer >= pauseDuration) {
//                pauseTimer = 0;
//                isPaused = false;
//                advanceToNextTarget();
//            }
//            return;
//        }
//
//        if (currentPath == null || pathIndex >= currentPath.size()) {
//            // مسیر به اتمام رسیده → وارد حالت مکث شو
//            isPaused = true;
//            return;
//        }
//
//        // قدم بعدی در مسیر
//        Node nextTile = currentPath.get(pathIndex);
//        Vector2 targetPos = tileToPixel(((Tile)nextTile).getPosition());
//        Vector2 currentPos = tileToPixel(this.getPosition());
//
//        // بردار حرکت تا سرعت و فاصله
//        Vector2 direction = targetPos.cpy().sub(currentPos).nor();
//        double directionAngle = Math.atan2(direction.y, direction.x);
//
//        if (Math.abs(direction.x) > Math.abs(direction.y)) {
//            setMovingDirection(direction.x > 0 ? Direction.RIGHT : Direction.LEFT);
//        } else {
//            setMovingDirection(direction.y > 0 ? Direction.UP : Direction.DOWN);
//        }
////        if (directionAngle>= -(Math.PI)/2 && directionAngle<(Math.PI)/2){
////            this.setMovingDirection(Direction.RIGHT);
////        } else if (directionAngle>= (Math.PI)/2 && directionAngle< 3*(Math.PI)/2) {
////            this.setMovingDirection(Direction.UP);
////        } else if (directionAngle>= 3*(Math.PI)/2 || directionAngle< - 3*(Math.PI)/2) {
////            this.setMovingDirection(Direction.LEFT);
////        } else {
////            this.setMovingDirection(Direction.DOWN);
////        }
//
//        this.setVelocity(direction.x * this.getSpeed(), direction.y * this.getSpeed());
//
//        // حرکت با delta
//        this.getPosition().ChangePosition(this.getVelocity().cpy().scl(delta).x , this.getVelocity().cpy().scl(delta).y);
//        // اگر به تقریب به هدف رسید:
//        if (currentPos.dst(targetPos) < this.getSpeed() * delta) {
//            // ایندکس مسیر را افزایش بده
//            pathIndex++;
//            // اگر به انتها رسید، وارد حالت مکث شو
//            if (pathIndex >= currentPath.size()) {
//                isPaused = true;
//            }
//        }
//    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if (newDay) {
            for (NpcFriendship friendship : friendships) {
                if (friendship.getLevel() < 3)
                    continue;
                Player playerToGift = friendship.getPlayer();
                int randomPercent = (int) (Math.random() * 100);
                if (randomPercent < 50) {
                    ArrayList<Item> favItems = this.getType().getFavoriteItems();
                    Item item = favItems.get(randomPercent % favItems.size());
                    if (playerToGift.getInventory().canAddItem(item, 1)) {
                        playerToGift.getInventory().add(item, 1);
                        System.out.println(this.type.getName() + " gifted 1*" + item.getName() + " to " +
                                playerToGift.getUser().getName());
                    } else {
                        System.out.println(this.type.getName() + " failed to send you gift beacuse" +
                                " your inventory was full ");
                    }
                }

            }
        }
    }
    @Override
    public String getAssetName(){
        //TODO
        return "Fiber_Grass1";
    }
}
