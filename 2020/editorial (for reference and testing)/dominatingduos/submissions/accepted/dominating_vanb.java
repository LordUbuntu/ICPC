import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Solution to Dominating Duos.
 *
 * @author vanb
 */
public class dominating_vanb
{
    /** Input. */
    private static Scanner sc;
    
    /** Output. */
    private static PrintStream ps;
       
    /**
     * A Person.
     *
     * @author vanb
     */
    private class Person implements Comparable<Person>
    {       
        /** Their height. */
        public int height;
        
        /** Where they stand. */
        public int index;
        
        /**
         * Instantiates a new person.
         *
         * @param height their height
         * @param index where they stand
         */
        public Person(int height, int index)
        {
            this.height = height;
            this.index = index;
        }

        /**
         * Compare two people by height, bigger first.
         *
         * @param other the other Person
         * @return Standard for compareTo
         */
        @Override
        public int compareTo( Person other )
        {
            return other.height - this.height;
        }
        
        /**
         * To string for debugging.
         *
         * @return the string
         */
        @Override
        public String toString()
        {
            return "[" + height + " at " + index + "]";
        }
    }
    
    /**
     * Do it!.
     */
    private void doit()
    {        
        // Read in the people, remember their height and where they stand
        int n = sc.nextInt();        
        Person people[] = new Person[n];
        for( int i=0; i<n; i++ ) people[i] = new Person( sc.nextInt(), i );
        
        // Sort them by height, biggest first
        Arrays.sort( people );
               
        // Min and max indices
        int min = people[0].index;
        int max = people[0].index;
        int total = 0;
        
        // Look at the people, by decreasing height. Form the line in this order.
        // We've started with the tallest person alone in the line.
        // If the next person to join is not on the end, then they can form 2 Dominating Duos with
        // the people on either side, since all subsequent people to be put in line
        // are shorter.
        // If they're past the end on either side of the existing line, then they can't form
        // a DD on that side (they can only form 1 DD), and they become the new end.
        for( int i=1; i<n; i++ )
        {
            ++total;
            if( people[i].index>max ) max = people[i].index;
            else if( people[i].index<min ) min = people[i].index;
            else ++total;
        }
        
        ps.println( total );
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
        
        new dominating_vanb().doit();
    }

}
