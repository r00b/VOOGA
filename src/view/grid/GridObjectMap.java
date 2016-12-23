package view.grid;

import java.awt.Point;
import java.util.*;

/**
 * @author Teddy Franceschi
 */
public class GridObjectMap {

    private Map<Point, ArrayList<Point>> data;
    private int height;
    private int width;

    /**
     * Constructor for this class
     *
     * @param width
     * @param height
     */
    public GridObjectMap(int width, int height) {
        data = new HashMap<Point, ArrayList<Point>>();
        this.height = height;
        this.width = width;
        initMap();
    }

    private void initMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                data.put(new Point(i, j), new ArrayList<Point>());
            }
        }
    }

    private ArrayList<Point> sharesObjectWith(int x, int y) {
        try {
            ArrayList<Point> neighbors = data.get(new Point(x, y));
            return neighbors;
        } catch (Exception e) {
            return new ArrayList<Point>();
        }
    }

    /**
     * Returns a list of integers whose coordinates point to a location that share an object with (x,y)
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return Returns a list like {x1,y1,x2,y2...}
     */
    public ArrayList<Integer> sharesObjWith(int x, int y) {
        ArrayList<Point> neighbors = sharesObjectWith(x, y);
        ArrayList<Integer> points = new ArrayList<Integer>();
        for (int i = 0; i < neighbors.size(); i++) {
            points.add((int) neighbors.get(i).getX());
            points.add((int) neighbors.get(i).getY());
        }
        return points;
    }

    /**
     * Stores an object in the grid (marks coordinates as occupied)
     *
     * @param list List of points which will be occupied by object being placed there
     * @return
     */
    public boolean storeObject(List<GridPaneNode> list) {
        ArrayList<Point> temp = new ArrayList<Point>();
        for (int i = 0; i < list.size(); i++) {
            temp.add(nodeToPoint(list.get(i)));
        }
        for (int i = 0; i < temp.size(); i++) {
            for (int j = 0; j < temp.size(); j++) {
                //if(i!=j){
                if (data.containsKey(temp.get(i))) {
                    data.get(temp.get(i)).add(temp.get(j));
                }
                // }
            }
        }
        return true;
    }

    /**
     * Removes collision that would be cause if object is still there
     *
     * @param row
     * @param col
     */
    public void collisionRemoval(int row, int col) {
        Point a = new Point(row, col);
        ArrayList<Point> temp = data.get(a);
        for (int i = 0; i < temp.size(); i++) {
            data.put(temp.get(i), new ArrayList<Point>());
        }
    }

    /**
     * Checks if a position (x,y) is filled with an object or not
     *
     * @param x
     * @param y
     * @return
     */
    public boolean available(int x, int y) {
        Point temp = new Point(x, y);
        if (data.containsKey(temp)) {
            if (data.get(temp).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    void resizeAdd(int row, int col) {
        Point a = new Point(row, col);
        if (!data.containsKey(a)) {
            data.put(a, new ArrayList<Point>());
        }
    }

    void resizeRemove(int row, int col) {
        Point a = new Point(row, col);
        if (data.containsKey(a)) {
            if (data.get(a).isEmpty()) {
                data.remove(a);
            } else {
                collisionRemoval(row, col);
            }
        }
    }

    private Point nodeToPoint(GridPaneNode node) {
        return new Point(node.getRow(), node.getCol());
    }

    public String toString() {
        return data.toString();
    }

    Map<Point, ArrayList<Point>> getMap() {
        return data;
    }


}