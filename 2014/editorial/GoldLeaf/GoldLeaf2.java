
import java.io.*;
import java.util.*;
import java.awt.geom.*;

/**
 * Solution to Gold Leaf
 * 
 * @author vanb
 */
public class GoldLeaf2
{
    public Scanner sc;
    public PrintStream ps;
    
    /**
     * A class to hold & compare solutions
     * 
     * @author vanb
     */
    public class Solution implements Comparable<Solution>
    {
        /** (li,lj), (ri,rj) */
        public int li, lj, ri, rj;
        
        /**
         * Create a solution
         * 
         * @param li Left i
         * @param lj Left j
         * @param ri Right i
         * @param rj Right j
         */
        public Solution( int li, int lj, int ri, int rj )
        {
            this.li = li;
            this.lj = lj;
            this.ri = ri;
            this.rj = rj;
        }

        @Override
        /**
         * Compare one solution to another
         * 
         * @param s Another solution
         * @return negative if this solution is "better" than s, non-negative otherwise
         */
        public int compareTo( Solution s )
        {
            int diff = li - s.li;
            if( diff==0 ) diff = lj - s.lj;
            if( diff==0 ) diff = ri - s.ri;
            if( diff==0 ) diff = rj - s.rj;
            return diff;
        }
        
        /**
         * Convert this solution to a printable string
         * 
         * @return A printable string
         */
        public String toString()
        {
            return "" + li + " " + lj + " " + ri + " " + rj;
        }
    }
    
    public Solution solution = new Solution( 100, 100, 100, 100 );
    
    public void checkSolution( int li, int lj, int ri, int rj )
    {
        Solution newsol = new Solution( li, lj, ri, rj );
        if( newsol.compareTo( solution ) < 0 ) solution = newsol;
    }
           
    /**
     * Driver.
     * @throws Exception
     */
    public void doit(Scanner sc) throws Exception
    {
        ps = System.out; 
        
        int n = sc.nextInt();
        int m = sc.nextInt();
        
        // Read in the image, count the number of cells that
        // represent exposed paper
        char sheet[][] = new char[n][];
        int paper = 0;
        for( int i=0; i<n; i++ )
        {
            sheet[i] = sc.next().toCharArray();
            for( int j=0; j<m; j++ ) if( sheet[i][j]=='.' ) ++paper;
        }
                
        // First, check horizontal folds
        for( int i=1; i<n; i++ )
        {
            int limit = Math.min( i, n-i );
            boolean found = true;
            int count = 0;
            for( int j=0; j<m && found; j++ ) for( int k=0; k<limit && found; k++ )
            {
                ++count;
                found = (sheet[i+k][j]!=sheet[i-k-1][j]);
            }
            
            // It's only a solution if all of the exposed paper cells are covered
            if( found && count==paper ) checkSolution( i, 1, i, m );
        }
        
        // Next, check vertical
        for( int j=1; j<m; j++ )
        {
            int limit = Math.min( j, m-j );
            boolean found = true;
            int count = 0;
            for( int i=0; i<n && found; i++ ) for( int k=0; k<limit && found; k++ )
            {
                ++count;
                found = (sheet[i][j+k]!=sheet[i][j-k-1]);
            }
            if( found && count==paper ) checkSolution( 1, j, n, j );
        }
       
        // Now, diagonal like this: /
        int max = n+m-3;
        for( int limit=1; limit<=max; ++limit )
        {
            int count = 0;
            boolean found = true;
            for( int i=0; i<limit && found; i++ ) for( int j=0; j<limit-i && found; j++ )
            {
                int newi = limit-j;
                int newj = limit-i;
                if( newi>=0 && newi<n && newj>=0 && newj<m )
                {
                    ++count;
                    found = (sheet[i][j]!=sheet[newi][newj]);
                }
            }
            
            // Left i, Left j, Right i, Right j
            int li=0, lj=0, ri=0, rj=0;
            if( limit<n )
            {
                li = limit+1;
                lj = 1;
            }
            else
            {
                li = n;
                lj = limit - n + 2;
            }
            
            if( limit<m )
            {
                ri = 1;
                rj = limit+1;
            }
            else
            {
                ri = limit - m + 2;
                rj = m;
            }
            if( found && count==paper ) checkSolution( li, lj, ri, rj );
        }
        
        // Finally, diagonal like this: \
        for( int limit=1; limit<=max; ++limit )
        {
            int count = 0;
            boolean found = true;
            for( int i=0; i<limit && found; i++ ) for( int j=m-limit+i; j<m && found; j++ )
            {
                int newi = j-m+limit+1;
                int newj = m-limit+i-1;
                if( newi>=0 && newi<n && newj>=0 && newj<m )
                {
                    ++count;
                    found &= (sheet[i][j]!=sheet[newi][newj]);
                }
            }
            
            int li=0, lj=0, ri=0, rj=0;
            if( limit<m )
            {
                li = 1;
                lj = m - limit;
            }
            else
            {
                li = limit - m + 2;
                lj = 1;
            }
            
            if( limit<n )
            {
                ri = limit+1;
                rj = m;
            }
            else
            {
                ri = n;
                rj = max - limit + 2;
            }

            if( found && count==paper ) checkSolution( li, lj, ri, rj );
        }
         ps.println( solution );
    }
    
    /**
     * @param args
     */
    public static void main( String[] args ) throws Exception
    {
        Scanner sc = new Scanner(System.in) ;
        int T = sc.nextInt() ;
        while (T-- > 0)
           new goldleaf_vanb().doit(sc);
    }   
}
