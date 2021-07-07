package 类方法执行顺序;

/**
 * @author huahan
 * date: 2021/6/30.
 * version:1.0
 */
public class B {
    public static String str = "子类静态变量";

    static {
        System.out.println(str);
        System.out.println("子类的静态代码块");
    }
    B() {
        System.out.println("子类的构造方法");
    }

    public static void methodB() {
        System.out.println("子类的静态方法");
    }

    public void methodBB() {
        System.out.println("子类的普通方法");
    }

   public static void main(String[] args) {
//        A a = new A();
//        a.methodBB();

    }
}
