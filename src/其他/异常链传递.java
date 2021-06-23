package 其他;

/**
 * @author huahan
 * date: 2021/6/19.
 * version:1.0
 */
public class 异常链传递 {
    public void test1() {
        try {
            int num1 = 1/0;
        } catch (Exception e) {
            //e.setStackTrace(e.getStackTrace());
            e.printStackTrace();
        }
    };

    public void test2() {
        try {
            Integer num2 = null;
            num2.longValue();
        } catch (Exception e) {
            //e.setStackTrace(e.getStackTrace());
            e.printStackTrace();
        }
    };

    public void test3() {
        String num3 = "3.45";
        int num4 =  Integer.parseInt(num3);
    };

    public static void main(String[] args) {
        try {
            异常链传递 tr = new 异常链传递();
            tr.test1();
            tr.test2();
            tr.test3();
        } catch (Exception e) {
            System.out.println("--main方法异常了---");
            e.printStackTrace();
        }
    }
}
