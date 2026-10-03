import java.awt.image.AreaAveragingScaleFilter;
import java.io.InputStream;
import java.util.*;

import static java.util.Collections.max;
import static java.util.Collections.min;

public class Main {
    public static void main(String[] args) {
        int v = 6;

        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(1);
        adj.get(0).add(2);
        adj.get(1).add(0);
        adj.get(1).add(3);
        adj.get(2).add(0);
        adj.get(2).add(4);
        adj.get(3).add(1);
        adj.get(3).add(5);
        adj.get(4).add(2);
        adj.get(4).add(5);
        adj.get(5).add(3);
        adj.get(5).add(4);

        System.out.println(bfsGraph(v,adj));
        System.out.println(dfsGraph(v,adj));



    }
    public static ArrayList<Integer> bfsGraph(int v,ArrayList<ArrayList<Integer>> adj){
        boolean [] visited = new boolean[v];
        ArrayList<Integer> bfs = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        visited[0]=true;

        while(!q.isEmpty()){
            Integer node = q.poll();
            bfs.add(node);

            for(Integer i : adj.get(node)){
                if(!visited[i]){
                    q.add(i);
                    visited[i]=true;
                }
            }
        }
        return bfs;
    }

    // DFs Traversal of graph

    public static ArrayList<Integer> dfsGraph(int v, ArrayList<ArrayList<Integer>> adj){
        boolean[] visited = new boolean[v];
        ArrayList<Integer> dfs = new ArrayList<>();
        dfs(adj,0,dfs,visited);
        return dfs;

    }
    private static void dfs(ArrayList<ArrayList<Integer>> adj, Integer node, ArrayList<Integer> dfs,boolean[] visited) {
        visited[node] = true;
        dfs.add(node);
        for(Integer i : adj.get(node)){
            if(!visited[i]) {
                dfs(adj, i, dfs, visited);
            }
        }
    }
}
