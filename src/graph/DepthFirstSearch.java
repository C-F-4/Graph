/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

/**
 * description DFS related functions on Graph
 * Utility functions reset the previous state of private variables
 * @author A-C-PC
 */
public class DepthFirstSearch {
    private boolean[] marked;
    private int[] edgeTo;
    private int count;
    private boolean isConn;
    private int source;
    
    /**
     * Instantiates and create an object
     * @param graph A graph Object on which dfs operations are to be used
     */
    public DepthFirstSearch(GraphLib graph) {
        /*this.marked = new boolean[graph.getVertexCount()];
        this.edgeTo = new int[graph.getVertexCount()];
        this.source = -1;
        this.count = 0;
        this.isConn = false;*/
    }
    
    /**
     * dfs utility function (recursve)
     * Follows Recursive Approach as observed in defRec function
     * @see dfsRec
     * @param graph A graph object on which dfs is to be performed same as passed in constructor
     * @param v vertex on which dfs is to be done
     */
    public void dfsUtil(GraphLib graph, int v) {
        this.count = 0;
        this.source = v;
        this.marked = new boolean[graph.getVertexCount()];
        this.edgeTo = new int[graph.getVertexCount()];
        for(int i = 0; i < graph.getVertexCount(); i++) {
            this.marked[i] = false;
            this.edgeTo[i] = -1;
        }
        this.edgeTo[v] = v;
        this.dfsRec(graph, v);
        this.isConn = graph.getVertexCount() == this.count;
    }
    
    /**
     * dfs utility function (non recursive)
     * Follows Non Recursive/ Linear Approach as observed in defNonRec function
     * @see dfsNonRec
     * @param graph A graph object on which dfs is to be performed same as passed in constructor
     * @param v vertex on which dfs is to be done
     */
    public void dfsUtilNonRec(GraphLib graph, int v) {
        this.count = 0;
        this.source = v;
        this.marked = new boolean[graph.getVertexCount()];
        this.edgeTo = new int[graph.getVertexCount()];
        for(int i = 0; i < graph.getVertexCount(); i++) {
            this.marked[i] = false;
            this.edgeTo[i] = -1;
        }
        this.edgeTo[v] = v;
        this.dfsNonRec(graph, v);
        this.isConn = graph.getVertexCount() == this.count;
    }
    
    /**
     * dfs utility function (Full DFS on graph)
     * Follows Recursive Approach as observed in defRec function
     * @see dfsRec
     * @param graph A graph object on which dfs is to be performed same as passed in constructor
     */
    public void dfsFull(GraphLib graph) {
        this.count = 0;
        this.source = -1;
        this.marked = new boolean[graph.getVertexCount()];
        this.edgeTo = new int[graph.getVertexCount()];
        for(int i = 0; i < graph.getVertexCount(); i++) {
            this.marked[i] = false;
            this.edgeTo[i] = -1;
        }
        int tmp = 0;
        for(int i = 0; i < graph.getVertexCount(); i++) {
            if(!marked[i]) {
                this.count = 0;
                this.edgeTo[i] = i;
                this.dfsRec(graph, i);
                tmp ++;
            }
        }
        this.isConn = tmp == 1;
    }
    
    /**
     * Do Recursive DFS
     * Maximum Function calls would increase upto the longest path in the graph
     * Only Utility functions use dfsRec
     * @param graph as passed to DFS Utility Functions
     * @param v vertex
     */
    private void dfsRec(GraphLib graph, int v) {
        this.marked[v] = true;
        this.count ++;
        for(int w : graph.getAdjListOf(v)) {
            if(!this.marked[w]) {
                this.edgeTo[w] = v;
                this.dfsRec(graph, w);
            }
        }
    }
    
    /**
     * Do Linear DFS using stack
     * Stack size may increase upto the longest path in the graph
     * Only Utility functions use dfsRec
     * @param graph as passed to DFS Utility Functions
     * @param v source vertex
     */
    private void dfsNonRec(GraphLib graph, int v) {
        // Create a Stack
        Stack<Integer> stack = new Stack<>();
        // get all the iterators into an array so for each list at any time
        // we are pointing to the next element to visit
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[graph.getVertexCount()];
        // Initialize the Iterato[] with the adjacency list head nodes
        for(int i = 0; i < graph.getVertexCount(); i++) {
            adj[i] = graph.getAdjListOf(i).iterator();
        }
        // mark current as true and push the starting node;
        this.marked[v] = true;
        this.count ++;
        stack.push(v);
        while(!stack.isEmpty()) {
            // get the last element
            int top = stack.peek();
            //check if top has any left elements in adjacency list
            if(adj[top].hasNext()) {
                // get next element mark it true and check its adj list
                int w = adj[top].next();
                if(!this.marked[w]) {
                    this.marked[w] = true;
                    this.edgeTo[w] = top;
                    this.count ++;
                    stack.push(w);
                }
            }
            else {
                // remove the element
                stack.pop();
            }
        }
    }
    
    /**
     * Full Path between source and vertex v 
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
        for(int i = v; i != source; i = edgeTo[i]) {
            path.push(i);
        }
        path.push(source);
        return path;
    }
    
    /**
     * Once you have used dfs utility functions, then you can use this
     * to check whether v vertex and source vertex are connected
     * *** Don't use it after dfsFull
     * @param v vertex to which path needs to be found
     * @return a boolean value true if path exists false otherwise
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }
    
    /**
     * Prints The EdgeTo array
     * Use it for debugging purposes
     * If for a vertex i, edgeTo[i] = i, that means that's where the search started
     * example for a graph as
     * 0 : 1, 2       EdgeTo[] = [0, 0, 1]  if source = 0 for dfs;
     * 1 : 0, 2
     * 2 : 0, 1
     */
    public void printEdgeTo() {
        for(int i = 0; i < edgeTo.length; i++) {
            System.out.println(i + " : " + edgeTo[i]);
        }
    }
    
    /**
     * Same as hasPathTo, can be safely removed
     * used during development phase
     * @param v vertex
     * @return
     */
    public boolean getMarked(int v) {
        return this.marked[v];
    }
    
    /**
     * For last dfs done, returns the longest path with the source
     * @return number of vertices connected to source vertex including source vertex
     */
    public int getConnected() {
        return this.count;
    }
    
    /**
     * Checks if the graph is Connected or Disconnected
     * @return boolean value true if Connected false if Disconnected
     */
    public boolean isConnected() {
        return this.isConn;
    }
    
    @Override
    public String toString() {
        return Arrays.toString(edgeTo);
    }
}
