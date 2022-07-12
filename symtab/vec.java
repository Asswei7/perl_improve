package symtab;

import java.util.Arrays;
import java.util.Vector;
public class vec{
    public int[] num;
    public int n;
    public vec(int[] num,int n){
        this.num = Arrays.copyOfRange(num,0,0+n);
        this.n = n;
    }
    public void setValue(int index,int value){
        this.num[index] = value;
    }
    public int getValue(int index){
        return this.num[index];
    }
    public int mutiBYvec(vec v2){
        int res = 0;
        for(int i=0;i<this.n;i++){
            res += this.getValue(i)*v2.getValue(i);
        }
        return  res;
    }
    public vec mutiBYnum(int num){
        for(int i=0;i<this.n;i++){
            setValue(i,this.getValue(i)*num);
        }
        return this;
    }
    public vec addVec(vec v1){
        for(int i=0;i<this.n;i++){
            setValue(i,getValue(i)+ v1.getValue(i));
        }
        return this;
    }
    public vec addNum(int num){
        for(int i=0;i<this.n;i++){
            setValue(i,getValue(i)+ num);
        }
        return this;
    }
    public int getSum(){
        int sum = 0;
        for(int i=0;i<this.n;i++){
            sum += getValue(i);
        }
        return sum;
    }
    @Override
    public String toString() {
        return "vec{" +
                "num=" + Arrays.toString(num) +
                ", n=" + n +
                '}';
    }

}

