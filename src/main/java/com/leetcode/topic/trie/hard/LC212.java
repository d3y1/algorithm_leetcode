package com.leetcode.topic.trie.hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * LC212 单词搜索 II
 * @author d3y1
 */
public class LC212 {
    private int m;
    private int n;

    public List<String> findWords(char[][] board, String[] words) {
        // return solution1(board, words);
        return solution2(board, words);
    }

    private int[] dx = {0, 1, 0, -1};
    private int[] dy = {1, 0, -1, 0};

    /**
     * Trie+dfs
     * @param board
     * @param words
     * @return
     */
    private List<String> solution1(char[][] board, String[] words){
        m = board.length;
        n = board[0].length;

        Trie trie = new Trie();
        for(String word: words){
            trie.insert(word);
        }

        HashSet<String> ans = new HashSet<>();
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                dfs(board, i, j, trie, ans);
            }
        }

        return new ArrayList<String>(ans);
    }

    /**
     * 递归
     * @param board
     * @param i
     * @param j
     * @param trie
     * @param ans
     */
    private void dfs(char[][] board, int i, int j, Trie trie, HashSet<String> ans){
        char ch = board[i][j];
        Trie curr = trie.children.get(ch);

        if(curr == null){
            return;
        }
        if(curr.isEnd){
            ans.add(curr.word);
        }

        // 标记
        board[i][j] = '#';
        int x,y;
        for(int k=0; k<4; k++){
            x = i+dx[k];
            y = j+dy[k];
            if(0<=x&&x<m && 0<=y&&y<n){
                dfs(board, x, y, curr, ans);
            }
        }
        // 恢复
        board[i][j] = ch;
    }

    private class Trie {
        HashMap<Character, Trie> children;
        boolean isEnd;
        String word;

        public Trie(){
            this.children = new HashMap<>();
            this.isEnd = false;
            this.word = "";
        }

        public void insert(String word){
            Trie curr = this;
            for(char ch: word.toCharArray()){
                if(curr.children.get(ch) == null){
                    curr.children.put(ch, new Trie());
                }
                curr = curr.children.get(ch);
            }
            curr.isEnd = true;
            curr.word = word;
        }
    }



    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /**
     * Trie+dfs: 优化
     * @param board
     * @param words
     * @return
     */
    private List<String> solution2(char[][] board, String[] words){
        m = board.length;
        n = board[0].length;

        Trie trie = new Trie();
        for(String word: words){
            trie.insert(word);
        }

        HashSet<String> ans = new HashSet<>();
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                DFS(board, i, j, trie, ans);
            }
        }

        return new ArrayList<String>(ans);
    }

    /**
     * 递归
     * @param board
     * @param i
     * @param j
     * @param trie
     * @param ans
     */
    private void DFS(char[][] board, int i, int j, Trie trie, HashSet<String> ans){
        char ch = board[i][j];
        Trie curr = trie.children.get(ch);

        if(curr == null){
            return;
        }
        if(curr.isEnd){
            ans.add(curr.word);
            curr.isEnd = false;
        }

        if(curr.children.isEmpty()){
            trie.children.remove(ch);
        }else{
            // 标记
            board[i][j] = '#';
            int x,y;
            for(int[] dir: dirs){
                x = i+dir[0];
                y = j+dir[1];
                if(0<=x&&x<m && 0<=y&&y<n){
                    DFS(board, x, y, curr, ans);
                }
            }
            // 恢复
            board[i][j] = ch;
        }
    }
}