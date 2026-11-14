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
20. LC787.java : cheapest flight within K stops
    1. i still not able to understand the answer i think or lack full grasp of it.
21. LC1976 : Number of Ways to Arrive at Destination 
    1. Learning : very good question of how to use DP 
22. Bellman-Ford-Algorithm.java
23. Floyd-Warshall-Algorithm.java 
    1. used to calculate the the shortest distance from the each node to all other nodes
    2. LC1334.java - direct implementation of this
24. Minimum-spanning-Tree.java also called Prim's Algorithm
25. DisjointSet.java
    1. Learning : i learned what are these disjoint set and why they are used 
    2. answer of the above question - disjoint set as a data structure helps us to tell about the 
        whether a node is part of other node in constant time i.e 4x(alpha)
26. KruskalAlgo.java
    1. learning : how to apply disjoint set to find the MST : is what kruskal algorithm is about
27. DisjointSet.java - a question was solved below the derivation os DisjointSet
    1. name of the Question : Mini edge movement
    2. LC721 accounts merge : very good question next time you revise do learn other ways
    3. LC305 Number of Island II : very very good question. (https://www.geeksforgeeks.org/problems/number-of-islands/1)
28. Kosaraju-Algorithm.java : https://www.geeksforgeeks.org/problems/strongly-connected-components-kosarajus-algo/1
    1. Learning : what is Strongly connected components is and how to find them.
29. Tarjan's-Algorithm-LC1192.java :
    1. Learning : what is tarjan's algorithm and how it works.
30. Articulation-point-graph.java https://www.geeksforgeeks.org/problems/articulation-point2616/1
    1. Learning : how to find where the node breaks the whole graph into multiple component or not.
    2. Learning : it uses Tarjan's Algorithm.