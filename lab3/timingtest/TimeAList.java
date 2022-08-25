package timingtest;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        AList testList = new AList<Integer>();
        int toCheck = 1000;

        AList Ns = new AList<Integer>();
        AList times = new AList<Double>();
        AList opcounts = new AList<Integer>();

        Stopwatch timer = new Stopwatch();
        for(int i = 0; i <= 140000000; i++) {
            testList.addLast(i);

            if(i == toCheck){
                Ns.addLast(i);
                times.addLast(timer.elapsedTime());
                opcounts.addLast(i);
                toCheck = toCheck * 2;
            }
        }
        printTimingTable(Ns, times, opcounts);
    }
}
