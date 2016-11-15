/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.Scanner;

/**
 * description usage of GraphLib.java to create graphs,
 *             usage of DepthFirstSearch.java to do dfs or 
 *             find paths between vertices on an already created graph
 * @version 1.1.0
 * @author A-C-PC
 */
public class Graph {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int v = in.nextInt();
        int e = in.nextInt();
        GraphLib graph;
        graph = new GraphLib(v);
        for (int i = 0; i < e; i++) {
            graph.addEdge(in.nextInt(), in.nextInt());
        }
        graph.printAdjacencyList();
        //System.out.println(graph);
        //System.out.println(graph.getDegree(2));
        
        /* // not useful
        Object[] o;
        o = graph.getAdjListOf(2);
        for(Object value : o) {
            System.out.println(2 + " - " + value);
        }*/
        
        DepthFirstSearch search = new DepthFirstSearch(graph);
        /*for (int x = 0; x < v; x++) {
            search.dfsUtil(graph, x);
            for (int i = 0; i < graph.getVertexCount(); i++) {
                if (search.getMarked(i) && i != x) {
                    System.out.print(i + " ");
                }
            }
            System.out.println();
            System.out.println("Maximum Connected Nodes to " + x + ": " + search.getConnected());
            System.out.println("Path from " + x + " : " + search);
        }
        if(search.isConnected()) {
            System.out.println("Connected Graph");
        }
        else {
            System.out.println("Disconnected Graph");
        }
        
        
        for (int x = 0; x < v; x++) {
            search.dfsUtilNonRec(graph, x);
            for (int i = 0; i < graph.getVertexCount(); i++) {
                if (search.getMarked(i) && i != x) {
                    System.out.print(i + " ");
                }
            }
            System.out.println();
            System.out.println("Maximum Connected Nodes to " + x + ": " + search.getConnected());
        }
        if(search.isConnected()) {
            System.out.println("Connected Graph" + search);
        }
        else {
            System.out.println("Disconnected Graph" + search);
        }*/
        
        search.dfsFull(graph);
        if(search.isConnected()) {
            System.out.println("Connected Graph ");
        }
        else {
            System.out.println("Disconnected Graph ");
        }
        System.out.println("EdgeTo: " + search);
        /*
        search.dfsUtil(graph, 0);
        search.printEdgeTo();
        boolean tmp = search.hasPathTo(2);
        if(tmp) {
            System.out.println(search.pathTo(2));
        }
        else {
            System.out.println("No Path detected");
        }
        */
        
        BreadthFirstSearch bfs = new BreadthFirstSearch(graph, 0);
        for(int i = 0; i < v; i++) {
            if(bfs.hasPathTo(i)) {
                System.out.println(i + " from 0 : " + bfs.getDistTo(i));
            }
            else {
                System.out.println(i + " : disconnected from 0");
            }
        }
        System.out.println(bfs);
    }
}
