import java.util.TreeMap;

public class Solution {
    /*给一个数组， 例如 [1,2,3,6,7,9] 表示这些位置上有房子。位置相邻的房子组成社群。现在有三个：[1,2,3], [6,7], [9]。 又给了一系列请求queries，比如[6,3,7]。每个元素表示我要毁掉什么位置上的房子。问每毁掉一个房子后，还剩下多少个社群。
    毁掉6之后，还有3个，
    毁掉3之后，依旧有3个，
    当毁掉7之后，就剩下2个了。
    将每一步的结果放在一个数组里返回，于是应该返回 [3,3,2]
    这题要注意 输入的房子不一定是有序的， [1,2,3,6,7,9] 还可以换成[9,1,2,3,6,7], 结果应该是一样的。
    如果觉得有那么一点点帮助，可以帮我加一点点米吗，谢谢啦！*/
    public Solution(){

    }
    public int[] jquery(int[] houses,int[] broken){
        TreeMap<Integer, Integer> map=new TreeMap();
        int[] ans=new int[broken.length];
        int start=houses[0];
        int end=0;
        for(int i=0;i<houses.length-1;i++){
            if(houses[i]+1==houses[i+1])
                continue;
            else{
                end=houses[i];
                map.put(start,end);
                start=houses[i+1];
            }
        }
        map.put(start,houses[houses.length-1]);
        for(int i=0;i<broken.length;i++){
            int floor=map.floorKey(broken[i]);
            int ceil=map.get(floor);
            if(floor==ceil)
                map.remove(floor);
            else if(floor==broken[i]){
                map.remove(floor);
                map.put(floor+1,ceil);
            }
            else if(ceil==broken[i]){
                map.remove(floor);
                map.put(floor,ceil-1);
            }
            else{
                map.remove(floor);
                map.put(floor,broken[i]-1);
                map.put(broken[i]+1,ceil);
            }
            ans[i]=map.size();
        }
        return ans;
    }
}
