package com.leetcode.topic.trie.hard;

import java.util.HashSet;
import java.util.List;

/**
 * LC139 单词拆分
 * @author d3y1
 */
public class LC139 {
    private int len;
    private boolean isFound = false;

    HashSet<String> set = new HashSet<>();

    public boolean wordBreak(String s, List<String> wordDict) {
        // return solution1(s, wordDict);
        return solution11(s, wordDict);
        // return solution2(s, wordDict);
        // return solution3(s, wordDict);
    }

    /**
     * 动态规划
     *
     * dp[i]表示字符串s前i个字符组成的字符串s[0..i−1]是否能被拆分成若干个字典中出现的单词
     *
     * dp[i] = dp[j] && check(s[j..i−1])  , j<i
     * 其中 check(s[j..i−1]) 表示子串s[j..i−1]是否出现在字典中
     *
     * @param s
     * @param wordDict
     * @return
     */
    private boolean solution1(String s, List<String> wordDict){
        int len = s.length();

        int max = 0;
        HashSet<String> wordSet = new HashSet<>();
        for(String word: wordDict){
            max = Math.max(max, word.length());
            wordSet.add(word);
        }

        boolean[] dp = new boolean[len+1];
        dp[0] = true;
        for(int i=1; i<=len; i++){
            for(int j=i-1; j>=0; j--){
                // 剪枝
                if(i-j > max){
                    break;
                }
                // dp[j] && check(s[j..i−1])
                if(dp[j] && wordSet.contains(s.substring(j, i))){
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[len];
    }

    /**
     * 动态规划
     *
     * dp[i]表示字符串s前i个字符组成的字符串s[0..i−1]是否能被拆分成若干个字典中出现的单词
     *
     * dp[i] = dp[j] && check(s[j..i−1])  , j<i
     * 其中 check(s[j..i−1]) 表示子串s[j..i−1]是否出现在字典中
     *
     * @param s
     * @param wordDict
     * @return
     */
    private boolean solution11(String s, List<String> wordDict){
        int len = s.length();

        int max = 0;
        Trie trie = new Trie();
        for(String word: wordDict){
            max = Math.max(max, word.length());
            trie.insert(word);
        }

        boolean[] dp = new boolean[len+1];
        dp[0] = true;
        for(int i=1; i<=len; i++){
            for(int j=i-1; j>=0; j--){
                // 剪枝
                if(i-j > max){
                    break;
                }
                // dp[j] && check(s[j..i−1])
                if(dp[j] && trie.search(s.substring(j, i))){
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[len];
    }

    /**
     * dfs: 字典树Trie
     * @param s
     * @param wordDict
     * @return
     */
    private boolean solution2(String s, List<String> wordDict){
        Trie root = new Trie();
        for(String word: wordDict){
            root.insert(word);
        }

        len = s.length();
        for(int i=1; i<=len; i++){
            dfs(s, root, 0, i, "");
        }

        return isFound;
    }

    /**
     * 递归
     * @param s
     * @param root
     * @param i
     * @param j
     * @param result
     */
    private void dfs(String s, Trie root, int i, int j, String result){
        if(isFound){
            return;
        }
        String sub = s.substring(i, j);
        if(root.search(sub)){
            if(j == len){
                isFound = true;
            }else{
                String pre = s.substring(j, j+1);
                if(root.prefixNumber(pre) > 0){
                    for(int k=j+1; k<=len; k++){
                        dfs(s, root, j, k, result+sub+" ");
                    }
                }
            }
        }
    }

    /**
     * 字典树Trie
     */
    private class Trie {
        private Trie[] children;
        boolean isEnd;
        private int cnt;

        public Trie(){
            children = new Trie[75];
            isEnd = false;
            cnt = 0;
        }

        public void insert(String word){
            Trie curr = this;
            int index;
            for(char ch: word.toCharArray()){
                index = ch - '0';
                if(curr.children[index] == null){
                    curr.children[index] = new Trie();
                }
                curr = curr.children[index];
                curr.cnt++;
            }
            curr.isEnd = true;
        }

        public boolean search(String word){
            Trie curr = this;
            int index;
            for(char ch: word.toCharArray()){
                index = ch - '0';
                if(curr.children[index] == null){
                    return false;
                }
                curr = curr.children[index];
            }
            if(!curr.isEnd){
                return false;
            }

            return true;
        }

        public int prefixNumber(String pre){
            Trie curr = this;
            int index;
            for(char ch: pre.toCharArray()){
                index = ch - '0';
                if(curr.children[index] == null){
                    return 0;
                }
                curr = curr.children[index];
            }

            return curr.cnt;
        }
    }

    /**
     * dfs: HashSet
     * @param s
     * @param wordDict
     * @return
     */
    private boolean solution3(String s, List<String> wordDict){
        len = s.length();
        for(String word: wordDict){
            set.add(word);
        }

        dfs(s, 0, "");

        return isFound;
    }

    /**
     * 递归
     * @param s
     * @param i
     * @param result
     */
    private void dfs(String s, int i, String result){
        if(isFound){
            return;
        }
        if(i == len){
            isFound = true;
        }

        String sub;
        for(int j=i+1; j<=len; j++){
            sub = s.substring(i, j);
            if(set.contains(sub)){
                dfs(s, j, result+sub+" ");
            }
        }
    }
}