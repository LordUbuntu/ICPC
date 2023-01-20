import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Solution to
 * 
 * @author vanb
 */
public class magic_vanb
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
        char letters[] = sc.next().trim().toLowerCase().toCharArray();
        boolean found[] = new boolean[26];
        boolean ok = true;
        for( char letter : letters )
        {
            int index = letter-'a';
            if( found[index] ) ok = false;
            else found[index] = true;
        }
        
        ps.println( ok ? 1 : 0 );
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
        
        new magic_vanb().doit();
    }

}
