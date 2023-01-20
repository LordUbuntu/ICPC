import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Solution to longest Common Subsequence.
 *
 * @author vanb
 */
public class subsequence_vanb
{
    /** Input. */
    private static Scanner sc;
    
    /** Output. */
    private static PrintStream ps;
    
    /** 
     * The relations. 
     * Indices are cap letters minus 'A'. 
     * relations[i][j] is < if letter i is always before letter j, 
     *                    > if letter i is always after letter j,
     *                    ? if both can occur, and 
     *                    . if i==j. */
    private char relations[][];
    
    /** The longest subsequence starting from here. */
    private int fromhere[];
    
    /**
     * Depth First Search to find longest subsequences.
     *
     * @param node the node
     * @return the int
     */
    private int dfs( int node )
    {
        // Have we already done this letter?
        if( fromhere[node]<0 )
        {
            int best = 0;
            for( int i=0; i<fromhere.length; i++ )
            {
                // Look at all the next possible letters, find the longest
                if( relations[node][i]=='<' )
                {
                    int sofar = dfs(i);
                    if( sofar>best ) best = sofar;
                }
            }
            
            // Got to add 1 for this letter
            fromhere[node] = best+1;
        }
        
        return fromhere[node];
    }
    
    /**
     * Do it!.
     */
    private void doit()
    {
        int n = sc.nextInt();
        int k = sc.nextInt();
        
        // This will remember relations between letters
        // (which letters come before/after other letters)
        // Use a '.' as a flag that we haven't seen this relation yet.
        relations = new char[k][k];
        for( int i=0; i<k; i++ ) Arrays.fill( relations[i], '.' );
        
        // Find relations between pairs of letters
        while( n-->0 )
        {
            char letters[] = sc.next().toUpperCase().trim().toCharArray();
            for( int i=0; i<letters.length; i++ )
            {
                int ii = letters[i]-'A';

                for( int j=i+1; j<letters.length; j++ )
                {
                    int jj = letters[j]-'A';      
                    
                    // ii comes before jj.
                    if( relations[ii][jj]=='.' ) relations[ii][jj] = '<';
                    else if( relations[ii][jj]=='>' ) relations[ii][jj] = '?';
                    
                    // jj comes after ii.
                    if( relations[jj][ii]=='.' ) relations[jj][ii] = '>';
                    else if( relations[jj][ii]=='<' ) relations[jj][ii] = '?';
                }
            }
        }
        
        // Find the length of the longest subsequence from each starting point.
        // Use -1 as a flag that we haven't found it yet.
        fromhere = new int[k];
        Arrays.fill( fromhere, -1 );
        for( int i=0; i<k; i++ ) dfs(i);
        
        // Find & print the longest overall
        int longest = 0;
        for( int i : fromhere ) if( i>longest ) longest = i;
        ps.println( longest );
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
        
        new subsequence_vanb().doit();
    }

}
