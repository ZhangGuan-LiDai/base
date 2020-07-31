package com.ourlife.base.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangchao
 * @createdOn 2020/7/27
 */
public class Test {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }

    public static int[] twoSum(int[] nums, int target) {
        if (nums.length < 1) {
            throw new IllegalArgumentException();
        }
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int value = target - nums[i];
            if (map.containsKey(value)) {
                return new int[]{i, map.get(value)};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException();
    }
}
