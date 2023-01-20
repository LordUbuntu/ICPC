import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Solution to Missing Number
 * 
 * @author vanb
 */
public class missing_vanb
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
        sc.nextInt();
        String digits = sc.next();
        
        int i=1;
        String stri = ""+i;
        while( digits.startsWith( stri ) )
        {
            digits = digits.substring( stri.length() );
            stri = "" + (++i);
        }
        ps.println( i );
        
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
        
        new missing_vanb().doit();
    }

}
