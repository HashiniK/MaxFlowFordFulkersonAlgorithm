/*
 * Name - Hashini Kodithuwakku
 * IIT Student Id - 2019750
 * UoW Student Id - W1790198
 */

package algocwk;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author w1790198
 */
public class AlgoCWK {

    /**
     * @param args the command line arguments
     */
    
    //creating variable to store the nodes in graphs
    static int V; 
    
    /* Returns true if there's a path from source 's' to sink 'k' in 
    residual graph. Also fills parent[] to store the path
    */
    
    boolean bfs (int rGraph[][], int s, int k, int parent[]){
        /*create a visited array and mark all vertices
        as not visited
        */
        boolean visited[] = new boolean[V];
        for (int i=0; i<V; ++i)
            visited[i]=false;
        
        LinkedList<Integer> queue 
                = new LinkedList<Integer>();
        queue.add(s);
        visited[s]=true;
        parent[s]=-1;
        
        // Standard BFS Loop
        while (queue.size() != 0) {
            int u = queue.poll();
 
            for (int v = 0; v < V; v++) {
                if (visited[v] == false
                    && rGraph[u][v] > 0) {
                    // If we find a connection to the sink
                    // node, then there is no point in BFS
                    // anymore We just have to set its parent
                    // and can return true
                    if (v == k) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
 
        // We didn't reach sink in BFS starting from source,
        // so return false
        return false;
    }

    // Returns tne maximum flow from s to t in the given
    // graph
    int fordFulkerson(int graph[][], int s, int k)
    {
        int u, v;
 
        // Creating a residual graph and fill the residual
        // graph with given capacities in the original graph
        // as residual capacities in residual graph
 
        // Residual graph where rGraph[i][j] indicates
        // residual capacity of edge from i to j (if there
        // is an edge. If rGraph[i][j] is 0, then there is
        // not)
        int rGraph[][] = new int[V][V];
 
        for (u = 0; u < V; u++)
            for (v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];
 
        // This array is filled by BFS and to store path
        int parent[] = new int[V];
 
        int max_flow = 0; // There is no flow initially
 
        // Augment the flow while tere is path from source
        // to sink
        while (bfs(rGraph, s, k, parent)) {
            // Find minimum residual capacity of the edges
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int path_flow = Integer.MAX_VALUE;
            for (v = k; v != s; v = parent[v]) {
                u = parent[v];
                path_flow
                    = Math.min(path_flow, rGraph[u][v]);
            }
 
            // update residual capacities of the edges and
            // reverse edges along the path
            for (v = k; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }
 
            // Add path flow to overall flow
            max_flow += path_flow;
        }
 
        // Return the overall flow
        return max_flow;
    }
 
    // Driver program to test above functions
    public static void main(String[] args) throws java.lang.Exception
    {
        Scanner formatInputFile = null;
        
        try{
             formatInputFile = new Scanner (new File(args[0]));
        } catch (FileNotFoundException e){
            System.out.println("Error! File NOT found");
        }
        
        ArrayList<Integer> fullArray = new ArrayList(); //arraylist to store the complete node values
        ArrayList<Integer> n1Array = new ArrayList(); //arraylist to store the node 1 values
        ArrayList<Integer> n2Array = new ArrayList(); //arraylist to store the node 2 values
        ArrayList<Integer> n3Array = new ArrayList(); //arraylist to store the node 3 values
        
        while (formatInputFile.hasNext()){
            int data = formatInputFile.nextInt();
            fullArray.add(data);
        }
        
        System.out.println(fullArray);
        
        V = fullArray.get(0);
        fullArray.remove(0); //removing the first element as it represents the no. of nodes
        
        System.out.println(fullArray);
        
        //Seperating the arraylist to three arraylists
        for(int j=0; j<fullArray.size(); j+=3){
            n1Array.add(fullArray.get(j));
            n2Array.add(fullArray.get(j+1));
            n3Array.add(fullArray.get(j+2));
        }
        System.out.println("");

        System.out.println(n1Array);
        System.out.println(n2Array);
        System.out.println(n3Array);

        System.out.println("");

        
        int drawGraph[][] = new int[V][V];
        
        for(int i=0; i<n1Array.size(); i++){
            drawGraph[n1Array.get(i)][n2Array.get(i)] = n3Array.get(i);
        }
        for (int[] row : drawGraph){
            System.out.println(Arrays.toString(row));
        }
        AlgoCWK algo = new AlgoCWK();

        System.out.println("");
        System.out.println("Total No. of Nodes - "+V);
        long startTime = System.currentTimeMillis();
        System.out.println("Maximum Possible Flow - " +algo.fordFulkerson(drawGraph,0,V-1));
        long endTime = System.currentTimeMillis();
        double timeElapsed = (endTime - startTime)/1000.0;
        System.out.println("Time Taken - "+ timeElapsed );
    }
}
