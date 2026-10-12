class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        int[] inDegree = new int[numCourses];

        for(int i = 0 ; i<numCourses;i++){
            adj.add(new ArrayList<>());
        }
        for(int[] course : prerequisites){
            adj.get(course[0]).add(course[1]);
            inDegree[course[1]]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for(int i = 0 ; i<numCourses ; i++){
            if(inDegree[i]==0){
                q.add(i);
            }
        }
        int count = 0 ;
        while(!q.isEmpty()){
            int course = q.poll();
            count++;

            for(int nextCourse : adj.get(course)){
                inDegree[nextCourse]--;
                if(inDegree[nextCourse]==0){
                    q.add(nextCourse);
                }
            }
        }
        return count==numCourses;
    }
}