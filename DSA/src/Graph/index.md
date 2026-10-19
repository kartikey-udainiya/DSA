1. bfs and Dfs traversal of graph.java
2. CycleDetectionBfs.java
3. CycleDetectionDfs.java
4. LC130 
5. Number of distinct island.java ( good question , the problem was simple except how to store unqiue shapes)
   1. link - https://www.geeksforgeeks.org/problems/number-of-distinct-islands/1
   2. imp - storing shapes in set
6. BipartiteGraphBFS.java
    1. learning = how to convert edges given as in[][] edge into Adj list
   2. learning = flipping of colors
7. CycleDetection in Directed Graph DFS
   1. learning - how to follow one path and if ans not found on that path then move away.
8. LC802 - eventual safe states
   1. imp - revise - high chances that you might forget.
9. Kahn' algorithm or topological sort.java
10. Cycle Detection Directed graph using Kahn or topo-sort (BFS);
    1. learning = how to reverse the direction of the graph
    2. solving = LC802 using BFS or Topo sort using bfs or kahn algo (three are same)
11. TopologicalSortDFS.java 
    1. link - https://www.geeksforgeeks.org/problems/topological-sort/1
    2. Learning = Topo sort only works in DAG if it is not DAG then Topo sort will not process all nodes.
    3. learning = it can be used to find where there is a cycle or DAG.
10. LC207 - course Schedule I
11. LC210 - course Schedule II
12. LC269 -  brilliant problem with many edges cases must solve.
13. Shortest Path DAG using Topo-sort.java :
    1. problem is about find the shortest path of every node from the Source node
    2. learning : how to create weighted Adj list
    3. learning : how to do relaxation of edges.
14. ShortestPathInUndirectedUnitWeightGraph.java.
15. LC127 : word ladder I
    1. learning : my weak area - string
    2. how to iterate every char of string
16. DijkstraMinHeap.java 
    1. why to we need dijkstra - because we cannot find the shortest distance in a undirected graph with
    non unit weights
    2. For non-unit weighted graphs, you cannot use a normal BFS queue and simply add weight.
    3. (as we did in point 14)
    3. You need an algorithm that can handle the possibility that a node's distance gets improved later.
17. DijkstraUsingSortedSet.java
    1. learning - why use set:because in set we can remove duplicate entries thus a bit faster
18. ShortestPathUsingDijkstra.java
    1. Learning : in this we tell the "node by node" shortest path , ex - node 1->4->3->5
    2. Learning : same algorithm but use of memoization that means storing the "parent node in the current node"  in parent array
       why? :  because current node will tell about the parent node.
19. LC1361.java
    1. Learning : very good questions and that tell how to use dijkstra in such question.
