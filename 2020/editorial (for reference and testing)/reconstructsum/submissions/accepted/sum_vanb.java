import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Solution to Even or Odd
 * 
 * @author vanb
 */
public class sum_vanb
{
    /** Input. */
    private static Scanner sc;
    
    /** Output. */
    private static PrintStream ps;
    
    /**
     * Do it!
     */
    private void doit()
    {
        int n = sc.nextInt();
        
        // We'll need all the numbers, and the sum of all the numbers.
        long numbers[] = new long[n];
        long sum = 0L; 
        for( int i=0; i<n; i++ ) sum += (numbers[i] = sc.nextLong());
        
        // Find the smallest answer!
        // A number works if: sum-number == number
        long answer = Long.MAX_VALUE;        
        for( int i=0; i<n; i++ ) if( sum-numbers[i]==numbers[i] && numbers[i]<answer ) answer = numbers[i];
        
        if( answer==Long.MAX_VALUE ) ps.println( "BAD" );
        else ps.println( answer );
    }
        
    /**
     * Main.
     * 
     * @param args unused
     * @throws Exception
     */
    public static void main( String[] args ) throws Exception
    {
        sc = new Scanner( System.in );
        ps = System.out;
        
        new sum_vanb().doit();
    }

}
