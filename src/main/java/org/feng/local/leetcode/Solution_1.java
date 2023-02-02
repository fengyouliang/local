package org.feng.local.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Youliang Feng
 * @ModuleOwner: Sheldon Luo
 * @Date: 04/18/2023 19:24
 * @Description:
 */
public class Solution_1 {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;

        int[] result = twoSum(nums, target);

        System.out.println("result = " + Arrays.toString(result));
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int remain = target - num;
            if (map.containsKey(remain)) {
                return new int[]{i, map.get(remain)};
            }
            map.put(num, i);
        }
        return new int[]{};
    }
}
