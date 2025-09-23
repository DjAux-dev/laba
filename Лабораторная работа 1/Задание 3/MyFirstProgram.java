public class MyFirstProgram {
    public static void main(String[] args) {
        MySecondClass o = new MySecondClass(1, 1);
        System.out.println(o.divide());
        
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                o.setA(i);
                o.setB(j);
                System.out.println(i + " / " + j + " = " + o.divide());
            }
        }
    }
}

class MySecondClass {
    private int a, b;
    
    public MySecondClass(int a, int b) {
        this.a = a;
        this.b = b;
    }
    
    public int getA() { return a; }
    public int getB() { return b; }
    public void setA(int a) { this.a = a; }
    public void setB(int b) { this.b = b; }
    
    public int divide() {
        return b == 0 ? 0 : a / b;
    }
}