/*
 * VIVA - vanb's Input Verification Assistant
 * (C) 2012-2020
 * 
 * @author vanb
 */
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Solution to Ant Typing.
 *
 * @author vanb
 */
public class ant_vanb
{
    /** Input. */
    private static Scanner sc;
    
    /** Output. */
    private static PrintStream ps;
    
    /** The first digit. */
    private int first_digit;
    
    /** A count of how many times each pair appears. */
    private int pairs[][] = new int[10][10];
        
    /** The position of a given digin on the keyboard (0=far left, 8=far right). */
    private int pos[] = new int[9];

    /** Which keys have been 'used' in generating permutations. */
    private boolean used[] = new boolean[9];
    
    /** The best answer. */
    private int best = Integer.MAX_VALUE;
    
    /**
     * Permutations.
     *
     * @param level the level
     */
    private void permutations( int level )
    {
        // Level==0 means we've placed all 9 digits.
        if( level == 0 )
        {
            // We've got to move to the first digit, wherever it is
            int time = pos[first_digit];
            
            for( int i=0; i<9; i++ ) for( int j=i+1; j<9; j++ )
            {
                // The amount of time it takes to go from key i to key j (or vice versa)
                // multiplied by the number of times we have to do it
                time += pairs[i][j] * Math.abs( pos[i] - pos[j] );
            }
            
            if( time<best ) best = time;
        }
        else
        {
            // Place the next digit
            --level;
            for( int i=0; i<9; i++ ) if( !used[i] )
            {
                used[i] = true;
                pos[level] = i;
                permutations( level );
                used[i] = false;
            }
        }
    }
    
    /**
     * Do it!.
     */
    private void doit()
    {
        char sequence[] = sc.next().toCharArray();
        
        // Figure out the number of times each pair of digits occurs consecutively
        int last_digit = sequence[0]-'1';
        first_digit = last_digit;
        for( int i=0; i<9; i++ ) Arrays.fill( pairs[i], 0 );        
        for( int i=1; i<sequence.length; i++ ) 
        {
            int next_digit = sequence[i]-'1';
            ++pairs[last_digit][next_digit];
            ++pairs[next_digit][last_digit];
            last_digit = next_digit;
        }
        
        // Go through all possible permutations for that row of keys
        Arrays.fill( used, false );
        Arrays.fill( pos, 0 );
        permutations( 9 );
        
        // permutations() only figures the cost of moving around.
        // We've got to add the cost of stopping for a second on each key
        ps.println( best + sequence.length );
    }
        
    /**
     * Main.
     *
     * @param args unused
     * @throws Exception the exception
     */
    public static void main( String[] args ) throws Exception
    {
        sc = new Scanner( System.in );
        ps = System.out;
        
        new ant_vanb().doit();
    }

}
