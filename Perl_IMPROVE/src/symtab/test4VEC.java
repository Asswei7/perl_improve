package symtab;

import java.util.Vector;

public class test4VEC {
    public static void main(String[] args) {
        Vector v1 = new Vector();
        //
        vec x = new vec(new int[]{1,2,3},3);
        vec y = new vec(new int[]{4,5,6},3);
        int r = x.mutiBYvec(y);
        System.out.println(r);
        vec z = x.mutiBYnum(2);
        System.out.println(z);
    }
}
