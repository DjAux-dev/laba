package myfirstpackage;

public class MySecondClass {
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