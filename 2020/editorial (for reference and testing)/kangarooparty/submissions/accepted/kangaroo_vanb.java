import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Solution to Kangaroo Party
 * 
 * @author vanb
 */
public class kangaroo_vanb
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
        int houses[] = new int[n];
        for( int i=0; i<n; i++ ) houses[i] = sc.nextInt();
        
        int best = Integer.MAX_VALUE;
        for( int i=0; i<n; i++ ) for( int j=i+1; j<n; j++ )
        {
            int energy = 0;
            for( int k=0; k<n; k++ )
            {
                int distance = Math.min( Math.abs( houses[k]-houses[i] ), Math.abs( houses[k]-houses[j] ) ); 
                energy += distance*distance;
            }
            
            if( energy<best ) best = energy;
        }
        
        ps.println( best );
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
        
        new kangaroo_vanb().doit();
    }

}
