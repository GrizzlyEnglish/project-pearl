package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers;

import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.Tile;
import com.purgadell.grizzly.Worlds.DungeonWorld.Board.Tiles.VoidTile;

import java.util.LinkedList;

/**
 * Created by Ryan English on 6/3/2017.
 */

public class PathFinder {

    private static class Node {
        private double heuristic = 0;
        private double movement_cost = 0;

        private Node parent_Node;

        private Coordinates cords;
        private Tile tile;

        private Node(Node parent, Coordinates c){
            this.parent_Node = parent;
            this.cords = c;
        }

        private Node(Node parent, Tile t){
            this.parent_Node = parent;
            this.tile = t;
            this.movement_cost = t.movementCost();
        }

        private boolean equals(Node n){
            if(tile != null) return this.tile.equals(n.tile);
            return this.cords.coords.Equals(n.cords);
        }

        private String ToString(){
            return tile == null ? cords.ToString() : tile.getTileCoords().ToString();
        }

        private double getFCost() { return heuristic + movement_cost; }
    }
    private static final int movement_cost = 10;

    public static LinkedList<Tile> getPath(LinkedList<Tile> boardTiles, Tile onTile, Tile endTile, TileGetter tg){
        Node n = getPath(tg, new Node(null, onTile), new Node(null, endTile), 1, boardTiles);
        return calcTilePath(n);
    }

    public static LinkedList<Coordinates> getPath(Coordinates start, Coordinates end, int width, int height){
        TileGetter tg = new TileGetter(width, height);
        Node n = getPath(tg, new Node(null, start), new Node(null, end), 0, null);
        return calcCoordPath(n);
    }

    private static Node getPath(TileGetter tg, Node start, Node end, int type, LinkedList<Tile> boardTiles){
        LinkedList<Node> Open_List = new LinkedList<Node>();
        LinkedList<Node> Closed_List = new LinkedList<Node>();

        Open_List.add(start);

        long startT = System.currentTimeMillis();

        while(Open_List.size() > 0){
            Node on_node = getLowestF(Open_List);
            Open_List.remove(on_node);
            Closed_List.add(on_node);

            if(on_node.equals(end)) return on_node;

            if(type == 0) coordsPath(on_node, end, tg, Open_List, Closed_List);
            else tilePath(boardTiles, tg, on_node, end, Open_List, Closed_List);

            if(Open_List.size() < 1) break;
            long currentT = System.currentTimeMillis() - startT;
            if(currentT > 6000){
                System.out.println("Killed Path Finder -> Start: " + start.ToString() + "  End: " + end.ToString());
                //Killed Path Finder -> Start: BOARD(44,47) -- POS(11264.0,7755.0)  End: BOARD(50,21) -- POS(12800.0,3465.0)
                //Killed Path Finder -> Start: BOARD(28,49) -- POS(7168.0,8085.0)  End: BOARD(23,52) -- POS(6016.0,8580.0)
                //Killed Path Finder -> Start: BOARD(0,42) -- POS(128.0,6930.0)  End: BOARD(46,50) -- POS(11904.0,8250.0)
                break;
            }
        }

//        LinkedList<Coordinates> list = new LinkedList<Coordinates>();
//        for(Node cl : Closed_List){
//            list.push(cl.cords);
//        }
//
//        for(Node ol : Open_List){
//            list.push(ol.cords);
//        }

        return getLowestF(Open_List);
    }

    private static void coordsPath(Node on_node, Node end, TileGetter tg, LinkedList<Node> Open_List, LinkedList<Node> Closed_List){
        LinkedList<Coordinates> tiles_around = tg.borderAroundCoordinate(on_node.cords, 1);

        for(Coordinates c : tiles_around){
            int additional_cost = 0;

            Node test_node = new Node(on_node, c);

            test_node.movement_cost = movement_cost + on_node.movement_cost + additional_cost;
            test_node.heuristic = calcHeuristic(c, end.cords);

            if(listContains(Closed_List, test_node)) continue;

            if(!listContains(Open_List, test_node)){
                Open_List.add(test_node);
            } else {
                if(on_node.movement_cost + movement_cost < test_node.movement_cost){
                    test_node.parent_Node = on_node;
                }
            }
        }
    }

    private static void tilePath(LinkedList<Tile> boardTiles, TileGetter tg, Node on_node, Node end, LinkedList<Node> Open_List, LinkedList<Node> Closed_List){
        LinkedList<Tile> tiles_around = tg.borderAroundTile(boardTiles, on_node.tile, 1);

        for(Tile t : tiles_around){
            int additional_cost = 0;

            Node test_node = new Node(on_node, t);

            test_node.movement_cost = movement_cost + on_node.movement_cost + additional_cost;
            test_node.heuristic = calcHeuristic(t.getTileCoords(), end.tile.getTileCoords());

            if(listContains(Closed_List, test_node)) continue;

            if(!listContains(Open_List, test_node)){
                Open_List.add(test_node);
            } else {
                if(on_node.movement_cost + movement_cost < test_node.movement_cost){
                    test_node.parent_Node = on_node;
                }
            }
        }
    }

    private static boolean listContains(LinkedList<Node> list, Node n){
        for(Node test : list){
            if(test.equals(n)) return true;
        }
        return false;
    }

    private static Node getLowestF(LinkedList<Node> Open_List){
        Node n = Open_List.get(0);
        double lastF = n.getFCost();

        for(Node testn : Open_List){
            if(testn.getFCost() < lastF){
                n = testn;
                lastF = n.getFCost();
            }
        }

        return n;
    }

    private static LinkedList<Coordinates> calcCoordPath(Node end){
        LinkedList<Coordinates> path = new LinkedList<Coordinates>();

        do{
            path.push(end.cords);
            end = end.parent_Node;
        }while(end != null && end.parent_Node != null);

        return path;
    }

    private static LinkedList<Tile> calcTilePath(Node end){
        LinkedList<Tile> path = new LinkedList<Tile>();

        do{
            path.push(end.tile);
            end = end.parent_Node;
        }while(end != null && end.parent_Node != null);

        return path;
    }

    private static double calcHeuristic(Coordinates n, Coordinates end){
        return Math.sqrt(Math.pow(n.position.x - end.position.x,2) + Math.pow(n.position.y - end.position.y, 2));
    }


}
