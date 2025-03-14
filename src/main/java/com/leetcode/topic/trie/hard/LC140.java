package com.leetcode.topic.trie.hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * LC140 单词拆分 II
 * @author d3y1
 */
public class LC140 {
    private int len;
    private ArrayList<String> list = new ArrayList<>();

    private HashSet<String> set = new HashSet<>();

    /**
     * 类似 -> LC139 单词拆分 [leetcode]
     * @param s
     * @param wordDict
     * @return
     */
    public List<String> wordBreak(String s, List<String> wordDict) {
        // return solution1(s, wordDict);
        // return solution2(s, wordDict);
        // return solution3(s, wordDict);
        return solution4(s, wordDict);
    }

    /**
     * 动态规划
     *
     * dp[i]表示字符串s前i个字符组成的字符串能否拆分
     *
     * dp[i] = dp[j] && check(s[j..i−1])  , 0<=j<i
     * check(s[j..i−1]) => map.getOrDefault(suffix, false)
     * 其中 check(s[j..i−1]) 表示子串s[j..i−1]是否出现在字典中
     *
     * @param s
     * @param wordDict
     * @return
     */
    private List<String> solution1(String s, List<String> wordDict){
        int n = s.length();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int len;
        HashMap<String, Boolean> map = new HashMap<>();
        for(String word: wordDict){
            len = word.length();
            min = Math.min(min, len);
            max = Math.max(max, len);
            map.put(word, true);
        }

        boolean[] dp = new boolean[n+1];
        dp[0] = true;
        ArrayList<String>[] list = new ArrayList[n+1];
        for(int i=1; i<=n; i++){
            int gap;
            String suffix;
            for(int j=i-1; j>=0; j--){
                gap = i-j;
                if(gap < min){
                    continue;
                }
                if(max < gap){
                    break;
                }
                suffix = s.substring(j, i);
                if(dp[j] && map.getOrDefault(suffix, false)){
                    dp[i] = true;
                    if(list[i] == null){
                        list[i] = new ArrayList<String>();
                    }
                    if(list[j]!=null && list[j].size() > 0){
                        for(String part: list[j]){
                            list[i].add(part + " " + suffix);
                        }
                    }else{
                        list[i].add(suffix);
                    }
                }
            }
        }

        List<String> ans = list[n]==null ? new ArrayList<>() : list[n];

        return ans;
    }



    /**
     * 动态规划: 简化
     *
     * dp[i]表示字符串s前i个字符组成的字符串的所有拆分方案
     *
     * 判断是否可拆分
     * dp[j]!=null && map.getOrDefault(suffix, false)  , 0<=j<i
     * check(s[j..i−1]) => map.getOrDefault(suffix, false)
     * 其中 check(s[j..i−1]) 表示子串s[j..i−1]是否出现在字典中
     *
     * @param s
     * @param wordDict
     * @return
     */
    private List<String> solution2(String s, List<String> wordDict){
        int n = s.length();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int len;
        HashSet<String> dictSet = new HashSet<>();
        for(String word: wordDict){
            len = word.length();
            min = Math.min(min, len);
            max = Math.max(max, len);
            dictSet.add(word);
        }

        ArrayList<String>[] dp = new ArrayList[n+1];
        dp[0] = new ArrayList<>();
        for(int i=1; i<=n; i++){
            int gap;
            String suffix;
            for(int j=i-1; j>=0; j--){
                gap = i-j;
                if(gap < min){
                    continue;
                }
                if(max < gap){
                    break;
                }
                suffix = s.substring(j, i);
                if(dp[j]!=null && dictSet.contains(suffix)){
                    if(dp[i] == null){
                        dp[i] = new ArrayList<>();
                    }
                    if(dp[j]!=null && dp[j].size() > 0){
                        for(String part: dp[j]){
                            dp[i].add(part + " " + suffix);
                        }
                    }else{
                        dp[i].add(suffix);
                    }
                }
            }
        }

        List<String> ans = dp[n]!=null ? dp[n] : new ArrayList<>();

        return ans;
    }



    /**
     * dfs: 字典树Trie
     * @param s
     * @param wordDict
     * @return
     */
    private List<String> solution3(String s, List<String> wordDict){
        Trie root = new Trie();
        for(String word: wordDict){
            root.insert(word);
        }

        len = s.length();
        for(int i=1; i<=len; i++){
            dfs(s, root, 0, i, "");
        }

        return list;
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
        String sub = s.substring(i, j);
        if(root.search(sub)){
            if(j == len){
                list.add(result+sub);
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
        private int count;

        public Trie(){
            children = new Trie[26];
            isEnd = false;
            count = 0;
        }

        public void insert(String word){
            Trie curr = this;
            int index;
            for(char ch: word.toCharArray()){
                index = ch - 'a';
                if(curr.children[index] == null){
                    curr.children[index] = new Trie();
                }
                curr = curr.children[index];
                curr.count++;
            }
            curr.isEnd = true;
        }

        public boolean search(String word){
            Trie curr = this;
            int index;
            for(char ch: word.toCharArray()){
                index = ch - 'a';
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
                index = ch - 'a';
                if(curr.children[index] == null){
                    return 0;
                }
                curr = curr.children[index];
            }

            return curr.count;
        }
    }



    /**
     * dfs: HashSet
     * @param s
     * @param wordDict
     * @return
     */
    private List<String> solution4(String s, List<String> wordDict){
        len = s.length();
        for(String word: wordDict){
            set.add(word);
        }

        dfs(s, 0, "");

        return list;
    }

    /**
     * 递归
     * @param s
     * @param i
     * @param result
     */
    private void dfs(String s, int i, String result){
        if(i == len){
            list.add(result.trim());
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