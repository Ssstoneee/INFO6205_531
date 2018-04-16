import java.util.Calendar;

public class Tsp_GA {
    //    /**
//     * @param a 开始时间
//     * @param b   结束时间
//     */

    public static void main(String[] args) {
        GA ga = new GA();
        Calendar a = Calendar.getInstance(); //开始时间
//        Tsp tsp = new Tsp();
//        tsp.run();
        ga.run();

        Calendar b = Calendar.getInstance(); //结束时间
        ga.t.CalTime(a,b);
//        tsp.CalTime(a, b);



    }
}


