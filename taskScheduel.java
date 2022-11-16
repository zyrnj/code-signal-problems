public class taskScheduel {

    int task(int[]cost,int[] time){
        Integer[][] memo=new Integer[cost.length][cost.length];
        return dfs(cost,time,memo,0,0);
    }
 //贪心并不好用，要同时贪心cost/time性价比以及让time尽可能和n-i相等
  //  还是二维dp,只通过了13/15个测试，超时
int dfs(int[]cost,int[] time,Integer[][] memo,int n, int lefttime){
        if(lefttime>=0&&n==cost.length||lefttime>=cost.length)
            return 0;
        else if(n==cost.length&&lefttime<0)
            return Integer.MAX_VALUE/2;
        if(lefttime>=0&&memo[n][lefttime]!=null)
            return memo[n][lefttime];
        int ans=0;
        ans=Math.min(dfs(cost,time,memo,n+1,lefttime+time[n])+cost[n],
                dfs(cost,time,memo,n+1,lefttime-1));
        if(lefttime>=0)
            memo[n][lefttime]=ans;
        return ans;
}


//理解错了题意，这种是针对task必须一个一个放置上去的记忆化搜索
    int dfs2(int[]cost,int[] time,Integer[] memo,int n, int lefttime){
        if(n==cost.length)
            return 0;
        //if(memo[n]!=null)
          //  return memo[n];
        int ans=0;
        if(lefttime==0){
            ans=cost[n]+dfs2(cost,time,memo,n+1,lefttime+time[n]);
        }
        else{
            ans=Math.min(dfs2(cost,time,memo,n+1,lefttime+time[n])+cost[n],
                    dfs2(cost,time,memo,n+1,lefttime-1));
        }
        memo[n]=ans;
        return ans;
    }
}
