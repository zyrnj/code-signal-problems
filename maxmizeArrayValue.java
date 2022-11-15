public class maxmizeArrayValue {
    /*
    Given an array arr of n positive integers, the following operation can be performed any number of times.
Use a 1-based index for the array.

Choose any i such that 2<=i<=n
Choose any x such that 1<=x<=arr[i]
Set arr[i-1] to arr[i-1]+x
Set arr[i] to arr[i]-x
Minimize the maximum value of arr using the above operation and return value.

Example: arr=[1,5,7,6]
arr becomes [1,9,3,6] after choosing i=3 and x=4 as x<=arr[3], i.e. 4<7
arr becomes [5,5,3,6] after choosing i=2 and x=4 as x<=arr[2]
arr becomes [5,5,4,5] after choosing i=4 and x=1 as x<=arr[4]
output = 5

Example : arr = [5,15,19]
output = 13

Example : arr = [10,3,5,7]
output = 10
     */
    public static int Ans(int[] nums){
        int n = nums.length;
        // we can not reduce the 1st number, so it is our lower bound;
        int lo = nums[0];
        int hi = getMax(nums);
        int minmax = lo;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (canAchieve(nums, mid)) {
                minmax = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private static boolean canAchieve(int[] nums, int max){
        int diff = 0;
        for (int i = nums.length - 1; i >= 0; i--){
            diff = Math.max(nums[i] + diff - max, 0);
        }
        return diff == 0;
    }

    private static int getMax(int[] nums) {
        int max = nums[0];
        for(int n: nums){
            max = Math.max(max, n);
        }
        return max;
    }
}
