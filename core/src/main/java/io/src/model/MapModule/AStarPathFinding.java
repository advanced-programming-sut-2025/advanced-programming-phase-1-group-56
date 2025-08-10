package io.src.model.MapModule;

import java.util.*;

public class AStarPathFinding {
    private final Network network;
    private List<Node> path;
    private Node start;
    private Node end;

    private PriorityQueue<Node> openList;
    private HashSet<Node> closedList;

    public AStarPathFinding(Network network, Node start, Node end) {
        this.network = network;
        this.start = start;
        this.end = end;
    }

    public List<Node> solve() {
        if (start == null || end == null) return Collections.emptyList();
        if (start.equals(end)) return Collections.emptyList();

        // RESET all nodes before search
        for (Node node : network.getNodes()) {
            node.setParent(null);
            node.setCost(Double.POSITIVE_INFINITY);
            node.setHeuristic(0);
            node.setFunction(Double.POSITIVE_INFINITY);
        }

        openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getFunction));
        closedList = new HashSet<>();

        // initialize start
        start.setCost(0);
        start.setHeuristic(start.heuristic(end));
        start.setFunction(start.getCost() + start.getHeuristic());
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            if (current.equals(end)) {
                retracePath(current);
                return path != null ? path : Collections.emptyList();
            }

            closedList.add(current);
            current.calculateNeighbours(network);

            for (Node neighbor : current.getNeighbours()) {
                if (!neighbor.isValid() || closedList.contains(neighbor)) continue;

                double tentativeCost = current.getCost() + current.distanceTo(neighbor);

                boolean inOpen = openList.contains(neighbor);
                if (!inOpen || tentativeCost < neighbor.getCost()) {
                    neighbor.setParent(current);
                    neighbor.setCost(tentativeCost);
                    neighbor.setHeuristic(neighbor.heuristic(end));
                    neighbor.setFunction(neighbor.getCost() + neighbor.getHeuristic());

                    if (inOpen) {
                        openList.remove(neighbor);
                    }
                    openList.add(neighbor);
                }
            }
        }

        // If no path found
        return Collections.emptyList();
    }

    private void retracePath(Node current) {
        List<Node> tempPath = new ArrayList<>();
        Node temp = current;
        while (temp != null) {
            tempPath.add(temp);
            temp = temp.getParent();
            if (tempPath.size() > network.getNodes().size()) {
                // loop detected or invalid, abort
                path = Collections.emptyList();
                return;
            }
        }
        Collections.reverse(tempPath);   // reverse to get start -> end
        this.path = tempPath;
    }

    // Optional utility
    public static int countDirectionChanges(List<Node> path) {
        if (path == null || path.size() < 3) return 0;
        int changes = 0;
        for (int i = 2; i < path.size(); i++) {
            Node n1 = path.get(i - 2);
            Node n2 = path.get(i - 1);
            Node n3 = path.get(i);
            int dx1 = (int)((Tile)n2).getPosition().getX() - (int)((Tile)n1).getPosition().getX();
            int dy1 = (int)((Tile)n2).getPosition().getY() - (int)((Tile)n1).getPosition().getY();
            int dx2 = (int)((Tile)n3).getPosition().getX() - (int)((Tile)n2).getPosition().getX();
            int dy2 = (int)((Tile)n3).getPosition().getY() - (int)((Tile)n2).getPosition().getY();
            if (dx1 != dx2 || dy1 != dy2) changes++;
        }
        return changes;
    }

    // getters and setters
    public Network getNetwork() { return network; }
    public List<Node> getPath() { return path; }
    public Node getStart() { return start; }
    public Node getEnd() { return end; }
    public void setStart(Node start) { this.start = start; }
    public void setEnd(Node end) { this.end = end; }
}



//public class AStarPathFinding {
//    private Network network;
//    private ArrayList<Node> path;
//    private Node start;
//    private Node end;
//
//    //    private ArrayList<Node> openList;
//    private PriorityQueue<Node> openList;
//    //    private ArrayList<Node> closedList;
//    private HashSet<Node> closedList;
//
//
//    public AStarPathFinding(Network network , Node start, Node end) {
//        this.network = network;
//        this.start = start;
//        this.end = end;
//    }
//
//    public ArrayList<Node> solve() {
//
//        for (Node node : network.getNodes()) {
//            node.setParent(null);
//            node.setCost(Double.POSITIVE_INFINITY);
//            node.setHeuristic(0);
//            node.setFunction(Double.POSITIVE_INFINITY);
//        }
//
//        if (start == null || end == null) return null;
//        if (start.equals(end)) return new ArrayList<>();
//
//        this.openList = new PriorityQueue<>(Comparator.comparingDouble(Node::getFunction));
//        this.closedList = new HashSet<>();
//
//        start.setCost(0);
//        start.setHeuristic(start.heuristic(end));
//        start.setFunction(start.getCost() + start.getHeuristic());
//        openList.add(start);
//
//        while (!openList.isEmpty()) {
//            Node current = openList.poll();
//
//            if (current.equals(end)) {
//                retracePath(current);
//                return path;
//            }
//
////            openList.remove(current);
//            closedList.add(current);
//            current.calculateNeighbours(network);
//
//            for (Node neighbor : current.getNeighbours()) {
//                if (!neighbor.isValid() || closedList.contains(neighbor)) continue;
//
//                double tempGScore = current.getCost() + current.distanceTo(neighbor);
//
//                if (!openList.contains(neighbor) || tempGScore < neighbor.getCost()) {
//                    neighbor.setParent(current);
//                    neighbor.setCost(tempGScore);
//                    neighbor.setHeuristic(neighbor.heuristic(end));
//                    neighbor.setFunction(neighbor.getCost() + neighbor.getHeuristic());
//
//                    if (!openList.contains(neighbor)) {
//                        openList.add(neighbor);
//                    }
//                }
//
//            }
//        }
//
//        return new ArrayList<>(); // No path found
//    }
//
//    public static int countDirectionChanges(ArrayList<Node> path) {
//        if (path == null || path.size() < 3) return 0;
//
//        int changes = 0;
//
//        for (int i = path.size() - 1; i >= 2; i--) {
//            Tile t1 = (Tile) path.get(i);
//            Tile t2 = (Tile) path.get(i - 1);
//            Tile t3 = (Tile) path.get(i - 2);
//
//            int dx1 = (int)t2.getPosition().getX() - (int)t1.getPosition().getX();
//            int dy1 = (int)t2.getPosition().getY() - (int)t1.getPosition().getY();
//            int dx2 = (int)t3.getPosition().getX() - (int)t2.getPosition().getX();
//            int dy2 = (int)t3.getPosition().getY() - (int)t2.getPosition().getY();
//
//            if (dx1 != dx2 || dy1 != dy2) {
//                changes++;
//            }
//        }
//
//        return changes;
//    }
//
//    private void retracePath(Node current) {
//        ArrayList<Node> tempPath = new ArrayList<>();
//        Node temp = current;
//
//        while (temp != null) {
//            tempPath.add(temp);
//            temp = temp.getParent();
//
//            // جلوگیری از حلقه‌های بی‌پایان
//            if (tempPath.size() > network.getNodes().size()) {
//                System.out.println("ghair mojaz location no path found");
//                return;
//            }
//        }
//
//        Collections.reverse(tempPath);
//        this.path = tempPath;
//    }
//
//
//    public Network getNetwork() {
//        return network;
//    }
//
//    public ArrayList<Node> getPath() {
//        return path;
//    }
//
//    public Node getStart() {
//        return start;
//    }
//
//    public Node getEnd() {
//        return end;
//    }
//
//    public void setStart(Node start) {
//        this.start = start;
//    }
//
//    public void setEnd(Node end) {
//        this.end = end;
//    }
//}
