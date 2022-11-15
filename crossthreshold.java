import javax.swing.*;

public class crossthreshold {
    public crossthreshold(){

    };
    public int solution(int[] energy,int th){
        int left=0;
        int max=0;
        for(int a:energy){
            max=Math.max(max,a);
        }
        int right=max;
        while(left<=right){
            int mid=(left+right)/2;
            if(greaterthanth(energy,th,mid))
                left=mid+1;
            else
                right=mid-1;
        }
        return right;
    }


    boolean greaterthanth(int[] energy,int th,int bar){
        int sum=0;
        for(int a:energy){
            sum+=Math.max(a-bar,0);
        }
        return sum>=th;
    }
}
