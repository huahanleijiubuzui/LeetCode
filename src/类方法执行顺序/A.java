package 类方法执行顺序;

/**
 * @author huahan
 * date: 2021/6/30.
 * version:1.0
 */
public class A {
    public static String str = "父类静态变量";
    static {
        System.out.println(str);
        System.out.println("父类的静态代码块");
    }
    A() {
        System.out.println("父类的构造方法");
    }

    public static void methodA() {
        System.out.println("父类的静态方法");
    }

    public void methodBB() {
        System.out.println("父类的普通方法");
    }
}
