public static String foreignDictionary(String[] words) {
    ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
    int [] exist = new int[27];
    int [] inDegree = new int [27];

    for (int i = 0; i < 27; i++) {
        adj.add(new ArrayList<>());
    }
    for (String word : words) {
        for (char c : word.toCharArray()) {
            exist[c - 'a'] = 1;
        }
    }

    for (int i = 0; i < words.length-1; i++) {
        boolean foundDiff = false;
        int len = Math.min(words[i].length(),words[i+1].length());
        for (int j = 0; j < len; j++) {
            if(words[i].charAt(j)!=words[i+1].charAt(j)){
                int from = words[i].charAt(j)-'a';
                int to = words[i+1].charAt(j)-'a';
                adj.get(from).add(to);
                inDegree[to]++;
                foundDiff =true;
                break;
            }

        }
        if (!foundDiff && words[i].length() > words[i+1].length()) {
            return "";
        }
    }


    Queue<Integer> q = new LinkedList<>();

    for (int i = 0; i < 27; i++) {
        if(exist[i]==1&&inDegree[i]==0){
            q.add(i);
        }
    }
    List<Integer> list = new ArrayList<>();
    while(!q.isEmpty()){
        int node = q.poll();
        list.add(node);
        for(int neighbor:adj.get(node)){
            inDegree[neighbor]--;
            if(inDegree[neighbor]==0){
                q.add(neighbor);
            }
        }
    }
    int count = 0;
    for (int i = 0; i < 27; i++) {
        if(exist[i]==1){
            count++;
        }
    }

    // cycle detected
    if(list.size()<count){
        return "";
    }
    StringBuilder s = new StringBuilder();

    for (int i = 0; i < list.size(); i++) {
        s.append((char)(list.get(i)+'a'));
    }
    return s.toString();
}