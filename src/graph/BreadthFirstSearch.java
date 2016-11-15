/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * description BFS related functions on Graph
 * Used a different approach than DepthFirstSearch.java
 * @author A-C-PC
 */
public class BreadthFirstSearch {
    private final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    
    /**
     * Calculates the shortest path between source vertex and every other vertex 
     * in the graph
     * @param graph the graph
     * @param v the source vertex
     * @throws IllegalArgumentException if v is invalid
     */
    public BreadthFirstSearch(GraphLib graph, int v) {
        if(v < 0 || v >= graph.getVertexCount()) {
            throw new IllegalArgumentException("Argument Vertex value is Invalid : " + v);
        }
        this.marked = new boolean[graph.getVertexCount()];
        this.edgeTo = new int[graph.getVertexCount()];
        this.distTo = new int[graph.getVertexCount()];
        for(int i = 0; i < graph.getVertexCount(); i++) {
            this.distTo[i] = INFINITY;
            this.marked[i] = false;
            this.edgeTo[i] = -1;
        }
        this.bfs(graph, v);
    }
    
    /**
     * bfs algortihm Sets the edgeTo, marked, and distTo, use functions to get values
     * @param graph The graph
     * @param v The source vertex
     */
    private void bfs(GraphLib graph, int v) {
        Queue<Integer> q = new PriorityQueue<>();
        distTo[v] = 0;
        edgeTo[v] = -1;
        marked[v] = true;
        q.add(v);
        while(!q.isEmpty()) {
            int tmp = q.poll();
            for(int w : graph.getAdjListOf(tmp)) {
                if(!marked[w]) {
                    distTo[w] = distTo[tmp] + 1;
                    edgeTo[w] = tmp;
                    marked[w] = true;
                    q.add(w);
                }
            }
        }
    }
    
    /**
     * Is there a Path?
     * @param v Whether there is a path between v and the source vertex
     * @return true if there is a path else false
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }
    
    /**
     * What's the distance between em, INFINITY is the default value i.e no path
     * @param v
     * @return the distance between the source vertex and v
     */
    public int getDistTo(int v) {
        return distTo[v];
    }
    
    /**
     * Full Path between source vertex and v vertex 
     * Requires Stack, Size may vary up to the longest path
     * @param v valid destination vertex to which path has to be determined
     * @return Iterator &lt;Integer&gt; StackPath Returns the path between source and vertex
     *         else it returns null 
     */
    public Iterable<Integer> pathTo(int v) {
        if(!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        int x;
        for(x = v; distTo[x] != 0; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(x);
        return path;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(marked) + " " + 
               Arrays.toString(edgeTo) + " " +
               Arrays.toString(distTo);
    }
}
