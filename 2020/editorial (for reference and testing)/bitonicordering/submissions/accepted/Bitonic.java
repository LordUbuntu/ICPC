import java.util.ArrayList;
import java.util.Scanner;

/**
 * ACM (?) program to determine the fewest neighbor-swaps needed
 * to get a sequence of distinct integers into a bitonic
 * ordering such that the first portion of the sequence
 * is increasing and the second portion of the sequence
 * is decreasing.
 *
 * Conceptually, the maximum value of the sequence acts
 * as the "tent-pole".  Values on either side will
 * decrease as they move away from the maximum value.
 *
 * To keep things a little simpler, note that all values
 * are distinct.
 *
 * At first glance, you might just separately count
 * inversions on the left side of the maximum and the
 * right side of the maximum to get those independently
 * in the appropriate order.
 *
 * But the optimal result (fewest swaps) may involve
 * one or more elements crossing over from one side
 * of the maximum element to its other side.  And it
 * may involve elements from both sides crossing to the
 * other.
 *
 * Solution: O(n log n)
 *
 * There are probably several different ways to visualize this.
 * I struggled to convince myself of the correctness of my
 * general first idea, and eventually abandoned it for what
 * I believe is a better argument.
 *
 * Consider the smallest element.  In the optimal solution,
 * it must be either the leftmost or the rightmost element.
 * That choice is independent of the optimal solution for the
 * rest of the problem.  Once it is placed, everything else
 * will never interact with it again.  So the optimal solution
 * will involve moving the smallest element to whichever edge
 * of the data is closer.  Then the problem repeats for the
 * remaining data.
 *
 * So for each element, you need to know how many LARGER
 * elements are on each side of it (the smaller elements
 * will have already been accounted for).  Each element
 * will choose its side simply based on which of these
 * two choices involves fewer swaps.
 *
 * This can clearly be done in O(n^2) time.
 *
 * O(n log n) is possible by using the merge-sort based
 * counting inversions algorithm idea, instead keeping track
 * of the number of larger elements on each side of
 * each element as it builds up.
 *
 * Then a single pass through the data will compute
 * the optimal answer.
 */
public class Bitonic {

    public static ArrayList<Element> orig;
    public static ArrayList<Integer> lhs;
    public static ArrayList<Integer> rhs;
    public static ArrayList<Element> orig_sorted;

    public static long compute_bitonic() {
        /**
         * do merge sort counting inversions approach,
         * except instead of counting inversions, count
         * the number of larger elements to each side
         * of each element
         */

        orig_sorted = count_larger(orig); // now sorted

        // one loop through, choosing better path for each element
        // for confirmation, we'll build up what the bitonic
        // solution would look like too
        lhs = new ArrayList<>();
        rhs = new ArrayList<>();

        long total_swaps = 0;
        for(int i = 0; i < orig_sorted.size(); i++) {
            Element curr = orig_sorted.get(i);
            if(curr.larger_to_left < curr.larger_to_right) {
                lhs.add(curr.val);
                total_swaps += curr.larger_to_left;
            }
            else {
                rhs.add(curr.val);
                total_swaps += curr.larger_to_right;
            }
        }

        // to complete the confirmation array, tack
        // on the rhs elements in reverse order
        for(int i = rhs.size()-1; i >= 0; i--) {
            lhs.add(rhs.get(i));
        }

        /*
        for(Integer el: lhs) {
            System.out.print(el + " ");
        }
        System.out.println();
        */

        return total_swaps;

    }

    public static ArrayList<Element> count_larger(ArrayList<Element> ary) {
        if(ary.size() <= 1) {
            return ary;
        }

        int mid = (ary.size()-1) / 2;

        ArrayList<Element> lhalf = new ArrayList<>();
        ArrayList<Element> rhalf = new ArrayList<>();

        int i = 0;
        while(i <= mid) {
            lhalf.add(ary.get(i));
            i++;
        }
        while(i < ary.size()) {
            rhalf.add(ary.get(i));
            i++;
        }

        ArrayList<Element> lhalfsorted = count_larger(lhalf);
        ArrayList<Element> rhalfsorted = count_larger(rhalf);

        return merge_and_count(lhalfsorted, rhalfsorted);
    }

    public static ArrayList<Element> merge_and_count(ArrayList<Element> lhalfsorted,
                                                     ArrayList<Element> rhalfsorted) {

        ArrayList<Element> fullsorted = new ArrayList<>();

        int lidx = 0;
        int ridx = 0;
        while((lidx < lhalfsorted.size()) && ridx < rhalfsorted.size()) {
            if (lhalfsorted.get(lidx).val < rhalfsorted.get(ridx).val) {
                fullsorted.add(lhalfsorted.get(lidx));
                lhalfsorted.get(lidx).larger_to_right += rhalfsorted.size() - ridx;
                lidx++;
            }
            else {
                fullsorted.add(rhalfsorted.get(ridx));
                rhalfsorted.get(ridx).larger_to_left += lhalfsorted.size() - lidx;
                ridx++;
            }
        }

        while (lidx < lhalfsorted.size()) {
            fullsorted.add(lhalfsorted.get(lidx));
            lidx++;
        }
        while (ridx < rhalfsorted.size()) {
            fullsorted.add(rhalfsorted.get(ridx));
            ridx++;
        }

        return fullsorted;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        orig = new ArrayList<>();
        for(int i = 0; i < n;i++) {
            orig.add(new Element(in.nextInt()));
        }
        in.close();

        System.out.println(compute_bitonic());
    }
}

class Element {

    int val;
    int larger_to_left;
    int larger_to_right;

    Element(int value) {
        val = value;
        larger_to_left = 0;
        larger_to_right = 0;
    }
}