package com.leetcode.topic.trie.hard;

import java.util.HashMap;

/**
 * LC745 前缀和后缀搜索
 * @author d3y1
 */
public class LC745 {
    // 哈希
    // HashMap<String, Integer> dicts;
    // public WordFilter(String[] words) {
    //     dicts = new HashMap<>();
    //     String word;
    //     int len;
    //     for(int i=0; i<words.length; i++){
    //         word = words[i];
    //         len = word.length();
    //         for(int lenP=1; lenP<=len; lenP++){
    //             for(int lenS=1; lenS<=len; lenS++){
    //                 dicts.put(word.substring(0,lenP)+"#"+word.substring(len-lenS), i);
    //             }
    //         }
    //     }
    // }

    // public int f(String pref, String suff) {
    //     return dicts.getOrDefault(pref+"#"+suff, -1);
    // }

    //-----------------------------------------------------------------------------------

    // Trie
    // 27进制: ''-0 'a'-1 'b'-2 ... 'z'-26
    private final int RADIX = 27;
    Trie trie;
    public LC745(String[] words) {
        trie = new Trie();
        for(int i=0; i<words.length; i++){
            trie.insert(words[i], i);
        }
    }

    public int f(String pref, String suff) {
        return trie.query(pref, suff);
    }

    private class Trie {
        HashMap<Integer, Trie> children;
        int index;

        public Trie(){
            this.children = new HashMap<>();
            this.index = -1;
        }

        public void insert(String word, int idx){
            Trie root = this;
            Trie curr;
            int len = word.length();
            String pref,suff;
            int lenP,lenS;
            int key;
            for(int i=1; i<=len; i++){
                for(int j=0; j<len; j++){
                    curr = root;
                    pref = word.substring(0,i);
                    suff = word.substring(j);
                    lenP = pref.length();
                    lenS = suff.length();
                    for(int k=0; k<Math.max(lenP,lenS); k++){
                        key = 0;
                        if(k < lenP){
                            key += (pref.charAt(k)-'a'+1)*RADIX;
                        }
                        if(k < lenS){
                            key += (suff.charAt(lenS-k-1)-'a'+1);
                        }
                        if(!curr.children.containsKey(key)){
                            curr.children.put(key, new Trie());
                        }
                        curr = curr.children.get(key);
                        curr.index = idx;
                    }
                }
            }
        }

        public int query(String pref, String suff){
            int ans = -1;
            Trie curr = this;
            int lenP = pref.length();
            int lenS = suff.length();
            int key;
            for(int k=0; k<Math.max(lenP,lenS); k++){
                key = 0;
                if(k < lenP){
                    key += (pref.charAt(k)-'a'+1)*RADIX;
                }
                if(k < lenS){
                    key += (suff.charAt(lenS-k-1)-'a'+1);
                }
                if(!curr.children.containsKey(key)){
                    return -1;
                }
                curr = curr.children.get(key);
            }
            ans = curr.index;

            return ans;
        }
    }

    //-----------------------------------------------------------------------------------

    // Trie trie;
    // public WordFilter(String[] words) {
    //     trie = new Trie();
    //     for(int i=0; i<words.length; i++){
    //         trie.insert(words[i], i);
    //     }
    // }

    // public int f(String pref, String suff) {
    //     return trie.query(pref, suff);
    // }

    // private class Trie {
    //     HashMap<String, Trie> children;
    //     int index;

    //     public Trie(){
    //         this.children = new HashMap<>();
    //         this.index = -1;
    //     }

    //     public void insert(String word, int idx){
    //         Trie root = this;
    //         Trie curr;
    //         int len = word.length();
    //         String pref,suff;
    //         int lenP,lenS;
    //         String key;
    //         char c1,c2;
    //         for(int i=1; i<=len; i++){
    //             for(int j=0; j<len; j++){
    //                 curr = root;
    //                 pref = word.substring(0,i);
    //                 suff = word.substring(j);
    //                 lenP = pref.length();
    //                 lenS = suff.length();
    //                 for(int k=0; k<Math.max(lenP,lenS); k++){
    //                     c1 = k<lenP ? pref.charAt(k) : '#';
    //                     c2 = k<lenS ? suff.charAt(lenS-k-1) : '#';
    //                     key = new StringBuilder().append(c1).append(c2).toString();
    //                     if(!curr.children.containsKey(key)){
    //                         curr.children.put(key, new Trie());
    //                     }
    //                     curr = curr.children.get(key);
    //                     curr.index = idx;
    //                 }
    //             }
    //         }
    //     }

    //     public int query(String pref, String suff){
    //         int ans = -1;
    //         Trie curr = this;
    //         int lenP = pref.length();
    //         int lenS = suff.length();
    //         String key;
    //         char c1,c2;
    //         for(int k=0; k<Math.max(lenP,lenS); k++){
    //             c1 = k<lenP ? pref.charAt(k) : '#';
    //             c2 = k<lenS ? suff.charAt(lenS-k-1) : '#';
    //             key = new StringBuilder().append(c1).append(c2).toString();
    //             if(!curr.children.containsKey(key)){
    //                 return -1;
    //             }
    //             curr = curr.children.get(key);
    //         }
    //         ans = curr.index;

    //         return ans;
    //     }
    // }
}