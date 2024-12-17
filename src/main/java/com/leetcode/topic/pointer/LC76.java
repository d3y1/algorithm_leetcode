package com.leetcode.topic.pointer;

import java.util.HashMap;

/**
 * 76. 最小覆盖子串
 * @author d3y1
 */
public class LC76 {
    public String minWindow(String s, String t) {
        // return solution1(s, t);
        return solution2(s, t);
    }

    // 目标串 字符统计
    private HashMap<Character,Integer> tMap = new HashMap<>();
    // 当前串(滑动窗口) 字符统计
    private HashMap<Character,Integer> cntMap = new HashMap<>();

    /**
     * 双指针 + 哈希
     * @param s
     * @param t
     * @return
     */
    private String solution1(String s, String t){
        int m = s.length();
        int n = t.length();
        if(m < n){
            return "";
        }

        String result = "";

        // 目标串
        for(char ch: t.toCharArray()){
            tMap.put(ch, tMap.getOrDefault(ch,0)+1);
        }

        int len = Integer.MAX_VALUE;
        int start = 0;
        int end = 0;
        // 双指针(毛毛虫) 头动尾随 | 双指针(可变滑动窗口)
        for(int i=0,j=0; j<m; j++){
            if(!tMap.containsKey(s.charAt(j))){
                continue;
            }
            cntMap.put(s.charAt(j), cntMap.getOrDefault(s.charAt(j),0)+1);
            while(i<=j && !tMap.containsKey(s.charAt(i))){
                i++;
            }
            while(i<=j && check()){
                if(j-i+1 < len){
                    len = j-i+1;
                    start = i;
                    end = i+len;
                }
                if(tMap.containsKey(s.charAt(i))){
                    cntMap.put(s.charAt(i), cntMap.getOrDefault(s.charAt(i),0)-1);
                }
                i++;
            }
        }

        if(len != Integer.MAX_VALUE){
            result = s.substring(start, end);
        }

        return result;
    }

    /**
     * 校验是否满足条件(s涵盖t)
     * @return
     */
    private boolean check(){
        for(char ch: tMap.keySet()){
            if(cntMap.getOrDefault(ch,0) < tMap.get(ch)){
                return false;
            }
        }

        return true;
    }

    /**
     * 双指针 + 哈希
     * @param s
     * @param t
     * @return
     */
    private String solution2(String s, String t){
        int m = s.length();
        int n = t.length();
        if(m < n){
            return "";
        }

        String result = "";

        // 目标串 字符统计
        int[] need = new int[128];
        // 当前串(滑动窗口) 字符统计
        int[] have = new int[128];

        for(char ch: t.toCharArray()){
            need[ch]++;
        }

        int len = Integer.MAX_VALUE;
        int start = 0;
        int end = 0;
        char chL,chR;
        int cnt = 0;
        // 双指针(毛毛虫) 头动尾随 | 双指针(可变滑动窗口)
        for(int i=0,j=0; j<m; j++){
            chR = s.charAt(j);
            if(need[chR] == 0){
                continue;
            }
            if(have[chR] < need[chR]){
                cnt++;
            }
            have[chR]++;
            while(cnt == t.length()){
                if(j-i+1 < len){
                    len = j-i+1;
                    start = i;
                    end = i+len;
                }
                chL = s.charAt(i);
                if(need[chL] == 0){
                    i++;
                    continue;
                }
                if(have[chL] == need[chL]){
                    cnt--;
                }
                have[chL]--;
                i++;
            }
        }

        if(len != Integer.MAX_VALUE){
            result = s.substring(start, end);
        }

        return result;
    }
}