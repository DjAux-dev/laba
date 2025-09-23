import myfirstpackage.*;

class MyFirstClass {
    public static void main(String[] s) {
        MySecondClass o = new MySecondClass(8, 8);
        
        System.out.println(o.func());
        
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                o.setaNum(i);
                o.setbNum(j);
                System.out.print(o.func());
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}