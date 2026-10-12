class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        int[] inDegree = new int[numCourses];

        for(int i = 0 ; i<numCourses;i++){
            adj.add(new ArrayList<>());
        }
        for(int[] course : prerequisites){
            adj.get(course[1]).add(course[0]);
            inDegree[course[0]]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for(int i = 0 ; i<numCourses ; i++){
            if(inDegree[i]==0){
                q.add(i);
            }
        }
        List<Integer> list = new ArrayList<>();

        while(!q.isEmpty()){
            int course = q.poll();
            list.add(course);

            for(int nextCourse : adj.get(course)){
                inDegree[nextCourse]--;
                if(inDegree[nextCourse]==0){
                    q.add(nextCourse);
                }
            }
        }
        if(list.size()!=numCourses){return new int[0];}
        int[] res = new int[list.size()];
        for(int i = 0 ; i<list.size();i++){
            res[i]=list.get(i);
        }
        return res;
    }
}