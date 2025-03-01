package com.leetcode.topic.trie.easy;

/**
 * LC14 最长公共前缀
 * @author d3y1
 */
public class LC14 {
    public String longestCommonPrefix(String[] strs){
        return solution1(strs);
        // return solution2(strs);
        // return solution3(strs);
        // return solution4(strs);
        // return solution5(strs);
    }

    /**
     * Trie(字典树/前缀树)
     * @param strs
     * @return
     */
    private String solution1(String[] strs){
        int n = strs.length;
        Trie trie = new Trie();
        for(String str: strs){
            trie.insert(str);
        }

        String prefix = trie.getMaxPre(strs[0], n);

        return prefix;
    }

    /**
     * Trie类
     */
    private class Trie {
        private Trie[] children;
        private int cnt;

        public Trie(){
            this.children = new Trie[26];
            // 到达当前位置次数
            this.cnt = 0;
        }

        /**
         * 插入
         * @param str
         */
        public void insert(String str){
            Trie curr = this;
            int idx;
            for(char letter: str.toCharArray()){
                idx = letter-'a';
                if(curr.children[idx] == null){
                    curr.children[idx] = new Trie();
                }
                curr.children[idx].cnt++;
                curr = curr.children[idx];
            }
        }

        /**
         * 获取最长公共前缀
         * @param str
         * @param n
         * @return
         */
        public String getMaxPre(String str, int n){
            Trie curr = this;
            int idx;
            StringBuilder sb = new StringBuilder();
            for(char letter: str.toCharArray()){
                idx = letter-'a';
                // 关键 (等于n => 当前字符必包含于最长公共前缀)
                if(curr.children[idx].cnt != n){
                    break;
                }
                sb.append(letter);
                curr = curr.children[idx];
            }

            return sb.toString();
        }
    }

    /**
     * 横向扫描
     *
     * LCP(S1,...,Sn) = LCP(LCP(LCP(S1,S2),S3),...,Sn)
     *
     * @param strs
     * @return
     */
    private String solution2(String[] strs){
        int n = strs.length;
        String prefix = strs[0];
        for(int i=1; i<n; i++){
            prefix = LCP(prefix, strs[i]);
            if("".equals(prefix)){
                break;
            }
        }

        return prefix;
    }

    /**
     * 最长公共前缀
     * @param str1
     * @param str2
     * @return
     */
    private String LCP(String str1, String str2){
        int len = Math.min(str1.length(), str2.length());
        int idx = 0;
        while(idx<len && str1.charAt(idx)==str2.charAt(idx)){
            idx++;
        }

        return str1.substring(0, idx);
    }

    /**
     * 横向扫描(分治法)
     *
     * LCP(S1,...,Sn) = LCP(LCP(S1,...,Sk),LCP(S(k+1),...,Sn))
     *
     * @param strs
     * @return
     */
    private String solution3(String[] strs){
        int n = strs.length;
        return LCP(strs, 0, n-1);
    }

    /**
     * 最长公共前缀
     * @param strs
     * @param left
     * @param right
     * @return
     */
    private String LCP(String[] strs, int left, int right){
        if(left == right){
            return strs[left];
        }else{
            int mid = left+(right-left)/2;
            String lcpLeft = LCP(strs, left, mid);
            String lcpRight = LCP(strs, mid+1, right);
            return CP(lcpLeft, lcpRight);
        }
    }

    /**
     * 公共前缀
     * @param str1
     * @param str2
     * @return
     */
    private String CP(String str1, String str2){
        int len = Math.min(str1.length(), str2.length());
        int idx = 0;
        while(idx<len && str1.charAt(idx)==str2.charAt(idx)){
            idx++;
        }

        return str1.substring(0, idx);
    }

    /**
     * 纵向扫描
     * @param strs
     * @return
     */
    private String solution4(String[] strs){
        int n = strs.length;
        int len = strs[0].length();
        char letter;
        for(int i=0; i<len; i++){
            letter = strs[0].charAt(i);
            for(int j=1; j<n; j++){
                if(i==strs[j].length() || strs[j].charAt(i)!=letter){
                    return strs[0].substring(0, i);
                }
            }
        }

        return strs[0];
    }

    /**
     * 二分法
     * @param strs
     * @return
     */
    private String solution5(String[] strs){
        int minLen = Integer.MAX_VALUE;
        for(String str: strs){
            minLen = Math.min(minLen, str.length());
        }

        int left = 0;
        int right = minLen;
        int mid;
        while(left <= right){
            mid = left+(right-left)/2;
            if(isCP(strs, mid)){
                left = mid+1;
            }else{
                right = mid-1;
            }
        }

        return strs[0].substring(0, left-1);
    }

    /**
     * 是否为公共前缀(前len个字符)
     * @param strs
     * @param len
     * @return
     */
    private boolean isCP(String[] strs, int len){
        int n = strs.length;
        String pre0 = strs[0].substring(0, len);
        for(int i=1; i<n; i++){
            if(!strs[i].substring(0,len).equals(pre0)){
                return false;
            }
        }

        return true;
    }
}