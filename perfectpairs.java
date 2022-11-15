import java.util.Arrays;
/*
If x and y are both have the same sign, then |X+y| = |x|+|y| >= max(|x|,|y|).

Otherwise, x and y have different signs, so let's assume x<=0<=y. Then |x-y| is the distance between the two numbers and it's always at least as big as both of the absolute values.

**min(|x-y|,|x+y|)<=min(|x|,|y|)**

1. |x| < |y|
|y| ≤ 2 |x|
2. |y| < |x|
|x| ≤ 2|y|
 */
public class perfectpairs {
    public static int numberOfPair(int[] arr){
        for (int i = 0; i < arr.length; i++){
            if (arr[i] < 0) arr[i] = -arr[i];
        }
        Arrays.sort(arr);
        int res = 0;
        for (int i = 0; i < arr.length; i++){
            int idx = bs(arr, arr[i] * 2);
            res += Math.max(0, idx - i);
        }
        return res;
    }

    // find the int x that arr[x] <= n < arr[x + 1]
    public static int bs(int[] arr, int n){
        int sz = arr.length;
        if (n <= arr[0]) return 0;
        if (arr[sz - 1] <= n) return sz - 1;

        int lo = 0;
        int hi = sz - 1;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (n < arr[mid]) {
                hi = mid - 1;
            } else if (arr[mid] <= n && n < arr[mid + 1]) {
                return mid;
            } else {
                lo = mid - 1;
            }
        }
        return hi;
    }
}
