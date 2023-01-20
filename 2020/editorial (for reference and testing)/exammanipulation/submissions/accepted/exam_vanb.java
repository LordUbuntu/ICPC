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
 * Solution to Exam Manipulation
 * 
 * @author vanb
 */
public class exam_vanb
{
    /** Input. */
    private static Scanner sc;
    
    /** Output. */
    private static PrintStream ps;
    
    /** All the student's tests */
    private char tests[][];
    
    /** A place to put all possible answer keys */
    private char key[];
    
    /** The best of the worst! */
    private int best = 0;
    
    /**
     * Generate all possible Keys.
     *
     * @param level the level
     */
    private void keys( int level )
    {
        if( level==0 )
        {
            // We've chosen an answer for every question.
            // Let's see who fared worst.
            int worst = key.length;
            for( char test[] : tests )
            {
                int score = 0;
                for( int i=0; i<test.length; i++ ) if( test[i]==key[i] ) ++score;
                if( score<worst ) worst = score;
            }
            
            // If this is better than the best worst so far, remember it.
            if( worst>best ) best = worst;
        }
        else
        {
            // Pick an answer for the next question.
            --level;
            key[level] = 'T';
            keys(level);
            key[level] = 'F';
            keys(level);
        }
    }
    
    /**
     * Do it!
     */
    private void doit()
    {
        int n = sc.nextInt();
        int k = sc.nextInt();
        
        tests = new char[n][];
        key = new char[k];
        
        for( int i=0; i<n; i++ ) tests[i] = sc.next().trim().toCharArray();
        
        keys(k);
        
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
        
        new exam_vanb().doit();
    }

}
