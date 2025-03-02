package com.leetcode.topic.trie.easy;

import java.util.HashMap;

/**
 * LC3042 统计前后缀下标对 I
 * @author d3y1
 */
public class LC3042 {
    public int countPrefixSuffixPairs(String[] words) {
        // return solution1(words);
        // return solution11(words);
        // return solution2(words);
        return solution3(words);
    }

    /**
     * 循环遍历
     * @param words
     * @return
     */
    private int solution1(String[] words){
        int n = words.length;
        int cnt = 0;
        for(int i=0; i<n; i++){
            for(int j=i+1; j<n; j++){
                if(isPrefixAndSuffix(words[i], words[j])){
                    cnt++;
                }
            }
        }

        return cnt;
    }

    /**
     * 是否前缀且后缀
     * @param str1
     * @param str2
     * @return
     */
    private boolean isPrefixAndSuffix(String str1, String str2){
        int n1 = str1.length();
        int n2 = str2.length();
        if(n1 > n2){
            return false;
        }

        String prefix = str2.substring(0, n1);
        String suffix = str2.substring(n2-n1);
        if(!str1.equals(prefix) || !str1.equals(suffix)){
            return false;
        }

        return true;
    }

    /**
     * 循环遍历: 优化
     * @param words
     * @return
     */
    private int solution11(String[] words){
        int n = words.length;
        int cnt = 0;
        for(int i=0; i<n; i++){
            for(int j=i+1; j<n; j++){
                if(isPrefixSuffix(words[i], words[j])){
                    cnt++;
                }
            }
        }

        return cnt;
    }

    /**
     * 是否前缀且后缀
     * @param str1
     * @param str2
     * @return
     */
    private boolean isPrefixSuffix(String str1, String str2){
        return str2.startsWith(str1)&&str2.endsWith(str1);
    }

    /**
     * Trie(字典树/前缀树)
     * @param words
     * @return
     */
    private int solution2(String[] words){
        TRIE trie = new TRIE();

        int ans = 0;
        for(String word: words){
            ans += trie.insert(word);
        }

        return ans;
    }

    private class TRIE {
        int cnt;
        TRIE[] children;

        public TRIE(){
            this.cnt = 0;
            this.children = new TRIE[25*26+25+1];
        }

        public int insert(String word){
            int pairs = 0;
            int n = word.length();
            TRIE curr = this;
            char preCh,sufCh;
            int idx;
            for(int i=0; i<n; i++){
                preCh = word.charAt(i);
                sufCh = word.charAt(n-i-1);
                // 26进制 abc -> (ac bb ca)
                idx = (preCh-'a')*26+(sufCh-'a');
                if(curr.children[idx] == null){
                    curr.children[idx] = new TRIE();
                }
                curr = curr.children[idx];
                pairs += curr.cnt;
            }
            curr.cnt++;

            return pairs;
        }
    }

    /**
     * Trie(字典树/前缀树)
     * @param words
     * @return
     */
    private int solution3(String[] words){
        Trie trie = new Trie();

        int ans = 0;
        for(String word: words){
            ans += trie.insert(word);
        }

        return ans;
    }

    private class Trie {
        int cnt;
        HashMap<Integer,Trie> children;

        public Trie(){
            this.cnt = 0;
            this.children = new HashMap<>();
        }

        public int insert(String word){
            int pairs = 0;
            int n = word.length();
            Trie curr = this;
            char preCh,sufCh;
            int idx;
            for(int i=0; i<n; i++){
                preCh = word.charAt(i);
                sufCh = word.charAt(n-i-1);
                // 26进制 abc -> (ac bb ca)
                idx = (preCh-'a')*26+(sufCh-'a');
                curr.children.putIfAbsent(idx, new Trie());
                curr = curr.children.get(idx);
                pairs += curr.cnt;
            }
            curr.cnt++;

            return pairs;
        }
    }
}