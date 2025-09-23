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
class MySecondClass {
    private int aNum;
    private int bNum;
    
    public MySecondClass(int a, int b) {
        this.aNum = a;
        this.bNum = b;
    }
    
    public int getaNum() {
        return aNum;
    }
    
    public int getbNum() {
        return bNum;
    }
    
    public void setaNum(int val) {
        this.aNum = val;
    }
    
    public void setbNum(int val) {
        this.bNum = val;
    }
    
    public int func() {
        return aNum & bNum;
    }
}