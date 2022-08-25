package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        final int N = 128000;                   //size of SLList
        final int M = 10000;                    //number of getLast operations to perform

        AList Ns = new AList<Integer>();
        AList times = new AList<Double>();
        AList opcounts = new AList<Integer>();

        int toCheck = 1000;
        SLList testList = new SLList<>();

        for (int i = 0; i <= N; i++) {
            testList.addLast(i);

            if (i == toCheck) {
                Stopwatch timer = new Stopwatch();          //start the timer
                for (int n = 0; n <= M; n++){
                    testList.getLast();
                }
                Ns.addLast(i);
                times.addLast(timer.elapsedTime());
                opcounts.addLast(M);
                toCheck = toCheck * 2;
            }
        }
        printTimingTable(Ns, times, opcounts);
    }

}
