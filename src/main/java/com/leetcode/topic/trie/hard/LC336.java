package com.leetcode.topic.trie.hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LC336 回文对
 * @author d3y1
 */
public class LC336 {
    public List<List<Integer>> palindromePairs(String[] words) {
        // return sulution1(words);
        return sulution2(words);
    }

    /**
     * 模拟法: 超时!
     * @param words
     * @return
     */
    public List<List<Integer>> sulution1(String[] words) {
        int n = words.length;
        List<List<Integer>> ans = new ArrayList<>();
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i == j){
                    continue;
                }
                if(isPalindrome(words[i]+words[j])){
                    ans.add(Arrays.asList(i,j));
                }
            }
        }

        return ans;
    }

    /**
     * 是否回文字符串
     * @param str
     * @return
     */
    private boolean isPalindrome(String str){
        int len = str.length();
        int i = 0;
        int j = len-1;
        while(i <= j){
            if(str.charAt(i++) != str.charAt(j--)){
                return false;
            }
        }
        return true;
    }



    List<List<Integer>> ans = new ArrayList<>();
    List<Integer> idxEmpty = new ArrayList<>();

    /**
     * Trie
     * @param words
     * @return
     */
    public List<List<Integer>> sulution2(String[] words) {
        int n = words.length;
        Trie trie = new Trie();
        String word;
        for(int i=0; i<n; i++){
            word = words[i];
            if("".equals(word)){
                idxEmpty.add(i);
            }else{
                trie.insert(word, i);
            }
        }

        trie.query(words);

        return ans;
    }

    private class Trie {
        Trie[] children;
        boolean isEnd;
        int index;
        List<Integer> idxArray;

        public Trie(){
            this.children = new Trie[26];
            isEnd = false;
            index = -1;
            idxArray = new ArrayList<>();
        }

        /**
         * 插入
         * @param word
         * @param idx
         */
        public void insert(String word, int idx){
            Trie curr = this;
            for(char ch: word.toCharArray()){
                curr.idxArray.add(idx);
                if(curr.children[ch-'a'] == null){
                    curr.children[ch-'a'] = new Trie();
                }
                curr = curr.children[ch-'a'];
            }
            curr.isEnd = true;
            curr.index = idx;
        }

        /**
         * 查询结果
         * @param words
         */
        public void query(String[] words){
            Trie root = this;
            Trie curr;
            int n = words.length;
            String word;
            int len;
            char ch;
            // word: x + y
            // words[i] -> y(遍历字符串y)
            for(int i=0; i<n; i++){
                curr = root;
                word = words[i];
                len = word.length();
                int j;
                for(j=len-1; j>=0; j--){
                    ch = word.charAt(j);
                    if(curr.children[ch-'a'] == null){
                        break;
                    }
                    curr = curr.children[ch-'a'];
                    if(curr.isEnd){
                        // len(x) <= len(y)
                        if(isPalindrome(word.substring(0,j))){
                            if(curr.index != i){
                                ans.add(Arrays.asList(curr.index,i));
                            }
                        }
                    }
                }
                // y(为空 | 非空且所有字符遍历完)
                if(j == -1){
                    // x(非空)
                    if(curr.idxArray.size() > 0){
                        for(int idx: curr.idxArray){
                            // len(x) > len(y)
                            if(isPalindrome(words[idx].substring(len))){
                                if(idx != i){
                                    ans.add(Arrays.asList(idx,i));
                                }
                            }
                        }
                    }
                    // x(为空) -> ""
                    if(idxEmpty.size() > 0){
                        for(int idx: idxEmpty){
                            if(isPalindrome(words[i])){
                                if(idx != i){
                                    ans.add(Arrays.asList(idx,i));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}