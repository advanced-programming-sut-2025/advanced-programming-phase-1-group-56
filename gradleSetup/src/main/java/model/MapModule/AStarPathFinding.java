package model.MapModule;

import java.util.ArrayList;

public class AStarPathFinding {
    private Network network;
    private ArrayList<Node> path;
    private Node start;
    private Node end;

    private ArrayList<Node> openList;
    private ArrayList<Node> closedList;

    public AStarPathFinding(Network network , Node start, Node end) {
        this.network = network;
        this.path = null;
        this.openList = null;
        this.closedList = null;
        this.start = start;
        this.end = end;
    }

    public ArrayList<Node> solve() {

        if (start == null || end == null) {
            return null;
        }

        if (start.equals(end)) {
            this.path = new ArrayList<>();
            return path;
        }

        this.path = new ArrayList<>();

        this.openList = new ArrayList<>();
        this.closedList = new ArrayList<>();

        this.openList.add(start);

        while (!openList.isEmpty()) {
            Node current = getLowestF();

            if (current.equals(end)) {
                retracePath(current);
                break;
            }

            openList.remove(current);
            closedList.add(current);

            for (Node n : current.getNeighbours()) {

                if (closedList.contains(n) || !n.isValid()) {
                    continue;
                }

                double tempScore = current.getCost() + current.distanceTo(n);

                if (openList.contains(n)) {
                    if (tempScore < n.getCost()) {
                        n.setCost(tempScore);
                        n.setParent(current);
                    }
                } else {
                    n.setCost(tempScore);
                    openList.add(n);
                    n.setParent(current);
                }

                n.setHeuristic(n.heuristic(end));
                n.setFunction(n.getCost() + n.getHeuristic());

            }

        }
        return this.path;
    }

    public static int countDirectionChanges(ArrayList<Node> path) {
        if (path == null || path.size() < 3) return 0;

        int changes = 0;

        for (int i = path.size() - 1; i >= 2; i--) {
            Tile t1 = (Tile) path.get(i);
            Tile t2 = (Tile) path.get(i - 1);
            Tile t3 = (Tile) path.get(i - 2);

            int dx1 = t2.getPosition().getX() - t1.getPosition().getX();
            int dy1 = t2.getPosition().getY() - t1.getPosition().getY();
            int dx2 = t3.getPosition().getX() - t2.getPosition().getX();
            int dy2 = t3.getPosition().getY() - t2.getPosition().getY();

            if (dx1 != dx2 || dy1 != dy2) {
                changes++;
            }
        }

        return changes;
    }


//    public void reset() {
//        this.start = null;
//        this.end = null;
//        this.path = null;
//        this.openList = null;
//        this.closedList = null;
//        for (Node n : network.getNodes()) {
//            n.setValid(true);
//        }
//    }

    private void retracePath(Node current) {
        Node temp = current;
        this.path.add(current);

        while (temp.getParent() != null) {
            this.path.add(temp.getParent());
            temp = temp.getParent();
        }

        this.path.add(start);
    }

    private Node getLowestF() {
        Node lowest = openList.get(0);
        for (Node n : openList) {
            if (n.getFunction()< lowest.getFunction()) {
                lowest = n;
            }
        }
        return lowest;
    }
//
//    public void updateUI() {
//        setChanged();
//        notifyObservers();
//        clearChanged();
//    }

    public Network getNetwork() {
        return network;
    }

    public ArrayList<Node> getPath() {
        return path;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public void setEnd(Node end) {
        this.end = end;
    }
}
