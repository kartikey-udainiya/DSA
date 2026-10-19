class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if(!wordList.contains(endWord)){
            return 0 ;
        }

        Set<String> st = new HashSet<>();
        for(String word : wordList){
            st.add(word);
        }

        Queue<String> q = new LinkedList<>();
        q.add(beginWord);
        wordList.remove(beginWord);
        int level =1;

        while(!q.isEmpty()){
            int size = q.size();

            for (int i = 0; i < size; i++) {
                String word = q.poll();

                if(word.equals(endWord)){
                    return level;
                }
                char[] chars = word.toCharArray();
                for (int j = 0; j < word.length(); j++) {
                    char original = chars[j];

                    for (char c = 'a'; c <='z' ; c++) {
                        chars[j]=c;

                        String newWord = new String(chars);

                        if(st.contains(newWord)){
                            q.add(newWord);
                            st.remove(newWord);
                        }
                    }
                    chars[j]=original;
                }
            }
            level++;
        }
        return 0;
    }
}