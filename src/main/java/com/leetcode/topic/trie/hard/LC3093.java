package com.leetcode.topic.trie.hard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * LC3093 最长公共后缀查询
 * @author d3y1
 */
public class LC3093 {
    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        // return solution1(wordsContainer, wordsQuery);
        return solution2(wordsContainer, wordsQuery);
    }

    /**
     * Trie + bfs
     * @param wordsContainer
     * @param wordsQuery
     * @return
     */
    public int[] solution1(String[] wordsContainer, String[] wordsQuery) {
        Trie trie = new Trie();
        for(int i=0; i<wordsContainer.length; i++){
            trie.insert(wordsContainer[i], i);
        }

        HashMap<String, Integer> wordIndexMap = new HashMap<>();
        int[] ans = new int[wordsQuery.length];
        String word;
        for(int i=0; i<wordsQuery.length; i++){
            word = wordsQuery[i];
            if(wordIndexMap.containsKey(word)){
                ans[i] = wordIndexMap.get(word);
            }else{
                ans[i] = trie.query(word);
                wordIndexMap.put(word, ans[i]);
            }
        }

        return ans;
    }

    private class Trie {
        Trie[] children;
        boolean isEnd;
        int index;

        public Trie(){
            this.children = new Trie[26];
            this.isEnd = false;
            this.index = -1;
        }

        /**
         * 插入
         * @param word
         * @param idx
         */
        public void insert(String word, int idx){
            Trie curr = this;
            int n = word.length();
            char ch;
            for(int i=n-1; i>=0; i--){
                ch = word.charAt(i);
                if(curr.children[ch-'a'] == null){
                    curr.children[ch-'a'] = new Trie();
                }
                curr = curr.children[ch-'a'];
            }
            if(!curr.isEnd){
                curr.isEnd = true;
                curr.index = idx;
            }
        }

        /**
         * 查询
         * @param word
         * @return
         */
        public int query(String word){
            int ans = -1;
            boolean isFound = false;
            Trie curr = this;
            int n = word.length();
            char ch;
            for(int i=n-1; i>=0; i--){
                ch = word.charAt(i);
                if(curr.children[ch-'a'] == null){
                    ans = getIndex(curr);
                    isFound = true;
                    break;
                }else{
                    curr = curr.children[ch-'a'];
                }
            }
            if(!isFound){
                ans = getIndex(curr);
            }

            return ans;
        }

        /**
         * 获取索引: bfs
         * @param curr
         * @return
         */
        private int getIndex(Trie curr){
            int ans = Integer.MAX_VALUE;
            Queue<Trie> queue = new LinkedList<>();
            queue.offer(curr);
            Trie trie;
            int size;
            boolean isFound = false;
            while(!queue.isEmpty()){
                size = queue.size();
                while(size-- > 0){
                    trie = queue.poll();
                    if(trie.isEnd){
                        ans = Math.min(ans, trie.index);
                        isFound = true;
                    }
                    for(int i=0; i<26; i++){
                        if(trie.children[i] != null){
                            queue.offer(trie.children[i]);
                        }
                    }
                }
                if(isFound){
                    return ans;
                }
            }

            return ans;
        }
    }

    /**
     * Trie
     * @param wordsContainer
     * @param wordsQuery
     * @return
     */
    public int[] solution2(String[] wordsContainer, String[] wordsQuery) {
        TRIE trie = new TRIE();
        for(int i=0; i<wordsContainer.length; i++){
            trie.insert(wordsContainer[i], i);
        }

        int[] ans = new int[wordsQuery.length];
        for(int i=0; i<wordsQuery.length; i++){
            ans[i] = trie.query(wordsQuery[i]);
        }

        return ans;
    }

    private class TRIE {
        TRIE[] children;
        int index;
        int minLen;

        public TRIE(){
            this.children = new TRIE[26];
            this.index = -1;
            this.minLen = Integer.MAX_VALUE;
        }

        /**
         * 插入
         * @param word
         * @param idx
         */
        public void insert(String word, int idx){
            TRIE curr = this;
            int len = word.length();

            if(len < curr.minLen){
                curr.minLen = len;
                curr.index = idx;
            }

            char ch;
            for(int i=len-1; i>=0; i--){
                ch = word.charAt(i);
                if(curr.children[ch-'a'] == null){
                    curr.children[ch-'a'] = new TRIE();
                }
                curr = curr.children[ch-'a'];
                if(len < curr.minLen){
                    curr.minLen = len;
                    curr.index = idx;
                }
            }
        }

        /**
         * 查询
         * @param word
         * @return
         */
        public int query(String word){
            int ans;
            TRIE curr = this;
            int len = word.length();
            char ch;
            for(int i=len-1; i>=0; i--){
                ch = word.charAt(i);
                if(curr.children[ch-'a'] == null){
                    break;
                }
                curr = curr.children[ch-'a'];
            }
            ans = curr.index;

            return ans;
        }
    }
}