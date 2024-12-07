package com.leetcode.topic.pointer;

/**
 * 674 最长连续递增序列
 * @author d3y1
 */
public class LC674 {
    private int solution(int[] nums){
        int n = nums.length;
        int len = 1;
        // 双指针
        for(int i=0,j=1; j<n; j++){
            if(nums[j] > nums[j-1]){
                len = Math.max(len, j-i+1);
            }else{
                i = j;
            }
        }

        return len;
    }
}
