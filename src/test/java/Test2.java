import java.util.Scanner;

/**
 * @author Y.
 * @version $version$
 * @date Created in 22:02 2019/3/23
 */
public class Test2 {
    static long[] yushu = new long[9];//用来存余数
    static String A = "0ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        long n = sc.nextInt();
        yushu[0] = 0;
        int i = 1;
        while (n != 0) {
            yushu[i] = ((n % 26 == 0) ? 26 : n % 26);

            n /= 26;
            i++;
        }
        for (int a = i - 1; a > 0; a--) {
            System.out.print(A.charAt((int) yushu[a]));
        }
    }

}
