import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Solution to Triangular Collection
 * 
 * @author vanb
 */
public class triangular_vanb
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
        
        // Powers of 2 will be useful
        long power2[] = new long[n+1];
        power2[0] = 1L;
        for( int i=1; i<=n; i++) power2[i] = power2[i-1]*2L;
        
        // Read the numbers, sort them (smallest first).
        int a[] = new int[n];
        for( int i=0; i<n; i++ ) a[i] = sc.nextInt();
        Arrays.sort( a );
        
        // Go through all pairs, find all sets with each pair as the smallest two values.
        long total = 0L;
        for( int i=0; i<n; i++ )
        { 
            for( int j=i+1; j<n; j++ )
            {   
                // Three values x, y and z can form a triangle iff x+y>z, x+z>y and y+z>x.
                // Starting with the smallest values for x and y, find all values of z where x+y>z
                // We know that x<y<z. So if x+y>z, then z+y>x+y>z>x, so z+y>x.
                // You can also show by the same logic that z+x>y. So, all we have to do is find the 
                // values in the array which are greater than a[j] (later in the list since it's sorted), 
                // but less than a[i]+a[j].
                int k = Arrays.binarySearch( a, a[i]+a[j] );  
                
                // Java BinarySearch foo. You'll need to look it up.
                k = k<0 ? -k-2 : k-1;            
                
                // OK, so there are k-j values in the list which are greater than a[j] but smaller than a[i]+a[j].
                // Any combination of them can form a triangular set with a[i] and a[j], except the empty set.
                // Thus, there are 2^(k-j) - 1 sets found here.
                total += power2[k-j]-1L;
            }
        }
        
        ps.println( total );
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
        
        new triangular_vanb().doit();
    }

}
