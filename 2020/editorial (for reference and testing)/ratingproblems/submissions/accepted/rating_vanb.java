import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Solution to Rating Problems
 * 
 * @author vanb
 */
public class rating_vanb
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
        int k = sc.nextInt();
        double sum = 0.0;
        for( int i=0; i<k; i++ ) sum += sc.nextDouble();
        
        
        ps.print( (sum + (n-k)*(-3.0))/n );
        ps.print( " " );
        ps.println( (sum + (n-k)*(3.0))/n );
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
        
        new rating_vanb().doit();
    }

}
