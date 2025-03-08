package com.leetcode.topic.trie.middle;

/**
 * LC676 实现一个魔法字典
 * @author d3y1
 */
public class LC676 {
//    public MagicDictionary() {
//
//    }

    // private String[] dicts;

    // public void buildDict(String[] dictionary) {
    //     dicts = dictionary;
    // }

    // public boolean search(String searchWord) {
    //     int len = searchWord.length();

    //     int diff;
    //     for(String dict: dicts){
    //         if(dict.length() != len){
    //             continue;
    //         }
    //         diff = 0;
    //         for(int i=0; i<len; i++){
    //             if(dict.charAt(i) != searchWord.charAt(i)){
    //                 diff++;
    //             }
    //             if(diff > 1){
    //                 break;
    //             }
    //         }
    //         if(diff == 1){
    //             return true;
    //         }
    //     }

    //     return false;
    // }


//////////////////////////////////////////////////////////////////////////////


    Trie trie;

    public void buildDict(String[] dictionary) {
        trie = new Trie();
        for(String dict: dictionary){
            trie.insert(dict);
        }
    }

    public boolean search(String searchWord) {
        return dfs(trie, searchWord, 0, false);
    }

    private boolean dfs(Trie curr, String searchWord, int pos, boolean isModified){
        if(pos == searchWord.length()){
            return isModified && curr.isEnd;
        }

        int idx = searchWord.charAt(pos)-'a';
        if(curr.children[idx] != null){
            if(dfs(curr.children[idx], searchWord, pos+1, isModified)){
                return true;
            }
        }

        // 只换一次
        if(!isModified){
            for(int i=0; i<26; i++){
                // 一个字母换成另一个字母
                if(i!=idx && curr.children[i]!=null){
                    if(dfs(curr.children[i], searchWord, pos+1, true)){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private class Trie {
        Trie[] children;
        boolean isEnd;

        public Trie(){
            children = new Trie[26];
            isEnd = false;
        }

        public void insert(String dict){
            Trie curr = this;
            int idx;
            for(char ch: dict.toCharArray()){
                idx = ch-'a';
                if(curr.children[idx] == null){
                    curr.children[idx] = new Trie();
                }
                curr = curr.children[idx];
            }
            curr.isEnd = true;
        }
    }
}