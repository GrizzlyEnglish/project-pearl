package com.purgadell.grizzly.Worlds.DungeonWorld.Board.Helpers;

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

        private Node(Node parent, Coordinates c){
            this.parent_Node = parent;
            this.cords = c;
        }

        private double getFCost() { return heuristic + movement_cost; }
    }
    private static final int movement_cost = 10;

    public static LinkedList<Coordinates> getPath(Coordinates start, Coordinates end, int width, int height){
        LinkedList<Node> Open_List = new LinkedList<Node>();
        LinkedList<Node> Closed_List = new LinkedList<Node>();
        TileGetter tg = new TileGetter(width, height);

        Node n = new Node(null, start);

        Open_List.add(n);

        long startT = System.currentTimeMillis();

        while(Open_List.size() > 0){
            Node on_node = getLowestF(Open_List);
            Open_List.remove(on_node);
            Closed_List.add(on_node);

            if(on_node.cords.coords.Equals(end)) return calcPath(on_node);

            LinkedList<Coordinates> tiles_around = tg.borderAroundTile(on_node.cords, 1);

            for(Coordinates c : tiles_around){
                int additional_cost = 0;

                Node test_node = new Node(on_node, c);

                test_node.movement_cost = movement_cost + on_node.movement_cost + additional_cost;
                test_node.heuristic = calcHeuristic(c, end);

                if(listContains(Closed_List, test_node)) continue;

                if(!listContains(Open_List, test_node)){
                    Open_List.add(test_node);
                } else {
                    if(on_node.movement_cost + movement_cost < test_node.movement_cost){
                        test_node.parent_Node = on_node;
                    }
                }
            }

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

        LinkedList<Coordinates> list = new LinkedList<Coordinates>();
        for(Node cl : Closed_List){
            list.push(cl.cords);
        }

        for(Node ol : Open_List){
            list.push(ol.cords);
        }

        return list;
    }

    private static boolean listContains(LinkedList<Node> list, Node n){
        for(Node test : list){
            if(n.cords.equals(test.cords)) return true;
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

    private static LinkedList<Coordinates> calcPath(Node end){
        LinkedList<Coordinates> path = new LinkedList<Coordinates>();

        do{
            path.push(end.cords);
            end = end.parent_Node;
        }while(end.parent_Node != null);

        return path;
    }

    private static double calcHeuristic(Coordinates n, Coordinates end){
        return Math.sqrt(Math.pow(n.position.x - end.position.x,2) + Math.pow(n.position.y - end.position.y, 2));
    }


}
