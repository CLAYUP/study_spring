import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Y.
 * @version $version$
 * @date Created in 22:33 2019/3/13
 */
public class Ss {

    public static void main(String[] args) {
        String s = "+-------------------------------------------------+\n" +
                "|                                                 |\n" +
                "|    H######                      ####            |\n" +
                "|          #                      #  #            |\n" +
                "|          #                      #  #            |\n" +
                "|          #     ####             #  #            |\n" +
                "|          #     #  #             #  #            |\n" +
                "|          ######@###             #  #            |\n" +
                "|                #       ####     #  #            |\n" +
                "|                #       #  #     #  #            |\n" +
                "|            ####@#######@###     #  #            |\n" +
                "|            #   #       #        #  #            |\n" +
                "| T          #####       #        #  #   ##       |\n" +
                "| #                      #      ###  ### ##       |\n" +
                "| ################       #      #      ####       |\n" +
                "|                #       #      #         #       |\n" +
                "|   ##############       #######@##########       |\n" +
                "|   #                         ###                 |\n" +
                "|   ###########################                   |\n" +
                "+-------------------------------------------------+";
        System.out.println(s);

        int l = 0;
        int b = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '#') {
                l++;
            }
            if (s.charAt(i) == '@') {
                b += 2;
            }
        }
        System.out.println(l);
        System.out.println(b);

    }
}



