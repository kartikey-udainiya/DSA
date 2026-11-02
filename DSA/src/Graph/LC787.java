/*
 * LeetCode 787: Cheapest Flights Within K Stops
 *
 * PROBLEM: Find the cheapest price from src to dst with at most k stops.
 *          (k stops = at most k+1 flights/edges). Return -1 if impossible.
 *
 * APPROACH: BFS (level by level) + cost pruning.
 *   - Each BFS "level" = one more flight taken, so level number = number of
 *     edges used so far. This lets us enforce the stops limit naturally.
 *   - NOT Dijkstra: the queue is a plain FIFO LinkedList, not a PriorityQueue
 *     (the variable name "pq" is misleading). We care about the number of
 *     stops, not just the cheapest path, so BFS order is what matters.
 *   - totalCost[city] stores the cheapest cost found so far to reach that city.
 *     We only push a neighbor into the queue if we found a CHEAPER way to it.
 *     This prunes useless paths and keeps the search small.
 *
 * Time:  roughly O(k * E) in the worst case (each level can relax every edge)
 * Space: O(n + E) for the adjacency list, cost array and queue
 */
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

        // ---------- STEP I: Build the weighted adjacency list ----------
        // adj.get(a) = list of {destination, price} for every flight leaving city a
        ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] flight : flights) {
            int from = flight[0];
            int to = flight[1];
            int price = flight[2];

            adj.get(from).add(new int[]{to, price});
        }

        // ---------- STEP II: Initialize cost tracking and the queue ----------
        // totalCost[i] = cheapest known cost to reach city i from src
        // (MAX_VALUE means "not reached yet")
        int[] totalCost = new int[n];
        Arrays.fill(totalCost, Integer.MAX_VALUE);

        // Queue entries are {stopsUsedSoFar, currentCity, costSoFar}
        // Plain FIFO queue (BFS), despite the name "pq".
        Queue<int[]> pq = new LinkedList<>();
        pq.add(new int[]{0, src, 0});
        totalCost[src] = 0; // reaching the source costs nothing

        // ---------- STEP III: BFS over flights ----------
        while (!pq.isEmpty()) {
            int[] flight = pq.poll();
            int stops = flight[0];  // number of flights taken so far
            int city = flight[1];   // the city we're currently in
            int cost = flight[2];   // total price paid to get here

            // We're allowed k stops = k+1 flights max.
            // If we've already used k+1 flights, we can't fly any further.
            if (stops >= k + 1) {
                continue;
            }

            // Try every flight leaving the current city
            for (int[] neighborFlight : adj.get(city)) {
                int neigbhorCity = neighborFlight[0];
                int costToReach = neighborFlight[1];

                // Only keep this path if it is CHEAPER than the best known
                // path to that neighbor. (This is the pruning step.)
                // (stops <= k is redundant given the check above, but harmless.)
                if (cost + costToReach < totalCost[neigbhorCity] && stops <= k) {
                    totalCost[neigbhorCity] = cost + costToReach;
                    // One more flight used (stops + 1), new total cost
                    pq.add(new int[]{stops + 1, neigbhorCity, cost + costToReach});
                }
            }
        }

        // ---------- STEP IV: Answer ----------
        // If dst was never reached within the stop limit, return -1
        return totalCost[dst] != Integer.MAX_VALUE ? totalCost[dst] : -1;
    }
}