package com.leetcode.topic.trie.hard;

import java.util.Arrays;
import java.util.HashMap;

/**
 * LC2935 找出强数对的最大异或值 II
 * @author d3y1
 */
public class LC2935 {
    /**
     * 类似 -> NC378 两数最大异或值 [nowcoder]
     * 类似 -> LC2932 找出强数对的最大异或值 I [leetcode]
     * @param nums
     * @return
     */
    public int maximumStrongPairXor(int[] nums) {
        // return solution1(nums);
        // return solution2(nums);
        // return solution22(nums);
        return solution3(nums);
    }

    /**
     * 模拟法 - 超时!
     * @param nums
     * @return
     */
    private int solution1(int[] nums){
        int n = nums.length;
        int ans = 0;
        int xor;
        for(int i=0; i<n; i++){
            for(int j=i+1; j<n; j++){
                if(isStrongPairs(nums[i], nums[j])){
                    xor = nums[i]^nums[j];
                    ans = Math.max(ans, xor);
                }
            }
        }

        return ans;
    }

    /**
     * 是否为强数对
     * @param num1
     * @param num2
     * @return
     */
    private boolean isStrongPairs(int num1, int num2){
        int min,max;
        if(num1 <= num2){
            min = num1;
            max = num2;
        }else{
            min = num2;
            max = num1;
        }

        return max<=min*2;
    }

    /**
     * 位运算 + 哈希
     *
     * pre_k() 表示从最高位开始的前k位
     * a_i a_j 表示某两个选择的数
     * xor     表示最终异或结果(从高位到低位,依次确定,优先置1)
     *
     * pre_k(xor) = pre_k(a_i) ^ pre_k(a_j)
     * => pre_k(a_j) = pre_k(xor) ^ pre_k(a_i)
     *
     * xor当前数位优先置1, pre_k(xor)为已知
     * 因此我们先将所有的pre_k(a_j)加入哈希表existed中, 再枚举校验是否存在某个数a_i, 使得pre_k(xor)^pre_k(a_i)恰好出现在existed中.
     *
     * 如果可以找到上面的a_i, 则当前数位可以为1, 否则当前数位只能为0.
     *
     * @param nums
     * @return
     */
    private int solution2(int[] nums){
        int n = nums.length;
        Arrays.sort(nums);

        int HIGH_BIT = 31-Integer.numberOfLeadingZeros(nums[n-1]);
        int xor = 0;
        HashMap<Integer, Integer> existMap = new HashMap<>();
        // 从高位到低位 依次确定
        for(int i=HIGH_BIT; i>=0; i--){
            existMap.clear();

            // 当前数位优先置1, 此处xor即pre_k(xor) k=HIGH_BIT-i+1
            // xor = xor*2+1;
            xor = (xor<<1)+1;
            boolean found = false;
            for(int val: nums){
                // 表示当前数位可以为1 <- 校验pre_k(xor)^pre_k(a_i)
                if(existMap.containsKey(xor^(val>>i)) && existMap.get(xor^(val>>i))*2>=val){
                    found = true;
                    break;
                }
                // pre_k(a_j) val前k位 k=HIGH_BIT-i+1
                // key(val>>i) -> 当前最大val
                existMap.put(val>>i, val);
            }

            // 未找到 -> 表示当前数位不可为1, 减1置0
            if(!found){
                xor = xor-1;
            }
        }

        return xor;
    }

    /**
     * 位运算+哈希: 优化
     * @param nums
     * @return
     */
    private int solution22(int[] nums){
        int n = nums.length;
        Arrays.sort(nums);

        int HIGH_BIT = 31-Integer.numberOfLeadingZeros(nums[n-1]);

        int xor = 0;
        int mask = 0;
        HashMap<Integer, Integer> existMap = new HashMap<>();
        for(int i=HIGH_BIT; i>=0; i--){
            existMap.clear();
            mask |= 1<<i;
            // 当前比特位优先置1 -> 尝试置1
            int tryXor = xor|(1<<i);
            for(int val : nums){
                // 低位置0
                int maskVal = val&mask;
                if (existMap.containsKey(tryXor^maskVal) && existMap.get(tryXor^maskVal)*2>=val) {
                    // 当前比特位可以置1
                    xor = tryXor;
                    break;
                }
                existMap.put(maskVal, val);
            }
        }

        return xor;
    }

    /**
     * Trie(字典树/前缀树) + 滑动窗口
     * @param nums
     * @return
     */
    private int solution3(int[] nums){
        int ans = 0;
        // 排序
        Arrays.sort(nums);
        // Trie
        Trie trie = new Trie();

        // 滑动窗口 左边界
        int left = 0;
        for(int num: nums){
            trie.insert(num);
            while(nums[left]*2 < num){
                trie.remove(nums[left++]);
            }
            ans = Math.max(ans, trie.queryMaxXor(num));
        }

        return ans;
    }

    private class Node {
        // 0 1
        Node[] children = new Node[2];
        int cnt = 0;
    }

    private class Trie {
        // max(nums[i]) = 2^20-1(最多20位) -> 最多右移20-1=19位
        private static final int HIGH_BIT = 19;
        private Node root = new Node();

        /**
         * 新增
         * @param val
         */
        private void insert(int val){
            Node curr = root;
            int bit;
            for(int i=HIGH_BIT; i>=0; i--){
                bit = (val>>i)&1;
                if(curr.children[bit] == null){
                    curr.children[bit] = new Node();
                }
                curr = curr.children[bit];
                curr.cnt++;
            }
        }

        /**
         * 删除
         * @param val
         */
        private void remove(int val){
            Node curr = root;
            int bit;
            for(int i=HIGH_BIT; i>=0; i--){
                bit = (val>>i)&1;
                curr = curr.children[bit];
                curr.cnt--;
            }
        }

        /**
         * 查询: 获取当前数val的最大异或值
         * @param val
         * @return
         */
        private int queryMaxXor(int val){
            Node curr = root;
            int xor = 0;
            int bit;
            for(int i=HIGH_BIT; i>=0; i--){
                bit = (val>>i)&1;
                // cur.children[bit^1].cnt=0 => 已删除(空节点)
                if(curr.children[bit^1]!=null && curr.children[bit^1].cnt>0){
                    xor |= (1<<i);
                    curr = curr.children[bit^1];
                }else{
                    curr = curr.children[bit];
                }
            }

            return xor;
        }
    }
}