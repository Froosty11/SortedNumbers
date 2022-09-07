import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] nList = {1000, 4000, 8000, 16000, 32000, 64000, 128000, 256000};
        System.out.println("#n\tlin-rnd\tlin-sort\tbin-sort");
        for (int n :
                nList) {

            Random rnd = new Random();
            int iterations = 1000; // amount of iterations to take average from etc
            long unsorted_sum = 0;
            long unsorted_minimum = (int) Double.POSITIVE_INFINITY;
            long sorted_sum = 0;
            long sorted_minimum = (int)Double.POSITIVE_INFINITY;
            long bin_sum = 0;
            long bin_min = (int) Double.POSITIVE_INFINITY;
            for (int j = 0; j < iterations; j++) {
                int[] array = new int[n];
                for (int i = 0; i < n; i++) {
                    array[i] = rnd.nextInt(n);
                }
                int findKey = rnd.nextInt(n);
                //System.out.println("Finding " + findKey + " in array of " + n + " size");
                long unsorted_start = System.nanoTime();
                if (!search_unsorted(array, findKey)) {
                    //System.out.println(j +": L-search couldn't find " + findKey + " in " + (System.nanoTime()-unsorted_start) + "ns");
                }
                long unsorted_total = System.nanoTime() - unsorted_start;
                unsorted_sum += unsorted_total;
                if (unsorted_total < unsorted_minimum) unsorted_minimum = unsorted_total;



                Arrays.sort(array); //until we have a sorting algorithm, next week we'll use this
                long sorted_start = System.nanoTime();
                search_unsorted(array, findKey);
                long sorted_total = System.nanoTime() - sorted_start;
                sorted_sum += sorted_total;
                if (sorted_total < sorted_minimum) sorted_minimum = sorted_total;

                long bin_start = System.nanoTime();
                if (binary_search(array, findKey)) {
                    //System.out.println(j +": B-search couldn't find " + findKey + " in " + (System.nanoTime()-unsorted_start) + "ns");
                }

                long bin_total = System.nanoTime() - bin_start;
                if (bin_total < bin_min) bin_min = bin_total;
                bin_sum += bin_total;


            }
            long average = unsorted_sum / iterations;
            long bin_average = bin_sum / iterations;
            long sorted_avg = sorted_sum / iterations;

            System.out.println(n+"\t"+average+"\t"+sorted_avg +"\t"+bin_average);
            /*System.out.println("\n\n");
            System.out.println(n +": random average " + average);
            System.out.println(n +": sorted average " + sorted_average);
            System.out.println(n +": random minimum " + unsorted_minimum);
            System.out.println(n +": sorted minimum " + sort_min);*/

        }


    }

    public static boolean search_unsorted(int[] array, int key) {
        for (int index = 0; index < array.length; index++) {
            if (array[index] == key) {
                return true;
            }
        }
        return false;
    }

    private static int[] makeSortedArray(int n) {
        Random rnd = new Random();
        int[] array = new int[n];
        int nxt = 0;
        for (int i = 0; i < n; i++) {
            nxt += rnd.nextInt(10) + 1;
            array[i] = nxt;
        }
        return array;
    }

    public static boolean binary_search(int[] array, int key) {
        int first = 0;
        int last = array.length - 1;
        while (last-first > 1) {
        // jump to the middle
            int index = first +(last-first)/2;
            if (array[index] == key) {
                return true;
            // hmm what now?
            } if (array[index] < key && index < last) {
            // The index position holds something that is less than
                // what we're looking for, what is the first possible page?
                first = index+1;
                continue;
            } if (array[index] > key && index > first) {
        // The index position holds something that is larger than
        // what we're looking for, what is the last possible page?
                last = index-1;
                continue;
            }
        // Why do we land here? What shoudl we do?
        }
        return false;
    }


}
