package com.leetcode.topic.trie.hard;

import java.util.*;

/**
 * LC1948 删除系统中的重复文件夹
 * @author d3y1
 */
public class LC1948 {
    List<List<String>> ans = new ArrayList<>();
    // serial -> cnt
    HashMap<String, Integer> cntMap = new HashMap<>();
    List<String> path = new ArrayList<>();

    /**
     * Trie + dfs
     * @param paths
     * @return
     */
    public List<List<String>> deleteDuplicateFolder(List<List<String>> paths) {
        Trie trie = new Trie();
        for(List<String> path: paths){
            trie.insert(path);
        }

        serialize(trie);

        delete(trie);

        return ans;
    }

    private class Trie {
        String serial;
        HashMap<String, Trie> children;

        public Trie(){
            this.serial = "";
            this.children = new HashMap<>();
        }

        /**
         * 插入
         * @param path
         */
        public void insert(List<String> path){
            Trie curr = this;
            for(String dir: path){
                if(curr.children.get(dir) == null){
                    curr.children.put(dir, new Trie());
                }
                curr = curr.children.get(dir);
            }
        }


    }

    /**
     * 当前节点 子树序列化
     * 树的后序遍历
     * @param root
     */
    public void serialize(Trie root){
        if(root.children.isEmpty()){
            return;
        }

        // 子节点
        List<String> childSerials = new ArrayList<>();
        String dir;
        Trie child;
        for(Map.Entry<String,Trie> entry: root.children.entrySet()){
            dir = entry.getKey();
            child = entry.getValue();
            serialize(child);
            childSerials.add(dir+"("+child.serial+")");
        }

        // 排序
        Collections.sort(childSerials);

        StringBuilder sb = new StringBuilder();
        for(String serial: childSerials){
            sb.append(serial);
        }
        // 根
        root.serial = sb.toString();

        cntMap.put(root.serial, cntMap.getOrDefault(root.serial,0)+1);
    }

    /**
     * 删除重复
     * 树的前序遍历
     * @param root
     */
    public void delete(Trie root){
        // 当前节点 子树重复
        if(cntMap.getOrDefault(root.serial,0) > 1){
            return;
        }

        if(!path.isEmpty()){
            // 注意 要new一个ArrayList
            ans.add(new ArrayList<>(path));
        }

        String dir;
        Trie child;
        for(Map.Entry<String,Trie> entry: root.children.entrySet()){
            dir = entry.getKey();
            child = entry.getValue();
            path.add(dir);
            delete(child);
            path.remove(path.size()-1);
        }
    }
}