import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Solution to Atlas.
 *
 * @author vanb
 */
public class triptik_vanb
{
    /** Input. */
    private static Scanner sc;
    
    /** Output. */
    private static PrintStream ps;
        
    /**  Powers of 2 will be useful. */
    private int power2[];
    

    /** The n. */
    private int n;
    
    /** The k. */
    private int k;
    
    /**
     * A Point.
     *
     * @author vanb
     */
    private class Point implements Comparable<Point>
    {
        
        /** The coordinate. */
        public int x;
        
        /** The weight. */
        public int weight;
        
        /**
         * Instantiates a new point.
         *
         * @param x the coordinate
         * @param weight the weight
         */
        public Point(int x,  int weight)
        {
            this.x = x;
            this.weight = weight;
        }
        
        /**
         * To string (for debugging).
         *
         * @return the string
         */
        @Override
        public String toString()
        {
            return "[(" + x + ")," + weight + "]";
        }

        /**
         * Compare by weight.
         *
         * @param other the other Point
         * @return Standard for compareTo
         */
        @Override
        public int compareTo( Point other )
        {
            return this.weight - other.weight;
        }
    }
    
    /**
     * A Priority Queue with a maximum size.
     *
     * @author vanb
     */
    private class MaxQueue extends PriorityQueue<Point>
    {
        
        /** The max size. */
        private int max;
        
        /**
         * Instantiates a new max queue.
         *
         * @param max the max size
         */
        public MaxQueue( int max )
        {
            super(20);
            this.max = max;
        }
        
        /**
         * Adds a Point to this queue.
         * Will maintain the max size, removing the worst points if it has to.
         *
         * @param p the Point
         * @return true, if successful
         */
        @Override
        public boolean add( Point p )
        {
            boolean result = super.add( p );
            while( this.size()>max ) this.poll();
            
            return result;
        }
        
        /**
         * Adds a bunch of points to this queue.
         * Will maintain the max size, removing the worst points if it has to.
         *
         * @param q the q
         * @return true, if successful
         */
        public boolean addAll( MaxQueue q )
        {
            boolean result = super.addAll( q );
            while( this.size()>max ) this.poll();
            
            return result;
        }
    }
    
    /**
     * A node in a Segment Tree.
     *
     * @author vanb
     */
    private class Segment
    {
        
        /** The lower value. */
        int lo;
       
        
        /** The upper value. */
        int hi;
                
        /** The lower left quadrant. */
        Segment left = null;
        
        /** The lower right quadrant. */
        Segment right = null;
        
        
        /** The points. */
        MaxQueue points;

        /**
         * Instantiates a new quad.
         *
         * @param lo the lower value
         * @param hi the upper value
         */
        public Segment(int lo, int hi)
        {
            this.lo = lo;
            this.hi = hi;
                        
            points = new MaxQueue(k);
        }
        
        /**
         * Adds a Point to the Quad Tree node.
         *
         * @param p the Point
         */
        public void add( Point p )
        {
            points.add( p );
              
            // Midpoint of the range, defaulting to lower if lo=hi-1
            int mid = lo==hi-1 ? lo : (lo+hi)/2;
            
            // If lo==hi we're down to a single point.
            // If not, then we've got more depth to plumb.
            if( hi>lo )
            {
                if( p.x>mid  )
                {
                    if( right==null ) right = new Segment( mid+1, hi );
                    right.add( p );
                }
                
                if( p.x<=mid )
                {
                    if( left==null ) left = new Segment( lo, mid );
                    left.add( p );
                }                
            }
        }
        
        /**
         * Search for the Points in a viewport.
         *
         * @param vlo the lower end of the viewport
         * @param vhi the upper end of the viewport
         * @param queue A list of points to build up
         */
        public void search( int vlo, int vhi, MaxQueue queue )
        {
            if( vlo<=lo &&  hi<=vhi )
            {
                // If this Segment is entirely in the viewport, just add its points.
                queue.addAll( points );
            }
            else
            {
                // Otherwise, go down to the next level of Segments.
                int mid = lo==hi-1 ? lo : (lo+hi)/2;
                
                if( vhi>mid && right!=null ) right.search( vlo, vhi, queue );
                if( vlo<=mid && left!=null ) left.search( vlo, vhi, queue );
            }
        }
    }   
    
    /**
     * A State of the Breadth-First Search.
     *
     * @author vanb
     */
    private class State
    {
        
        /** The point at the center. */
        public Point p;
        
        /** The zoom (the number of times the dimension has been doubled). */
        public int zoom;
        
        /** The count (the number of steps to get here). */
        public int count;
        
//        /** The State we came from. */
//        public State from;

        /**
         * Instantiates a new state.
         *
         * @param p the p
         * @param zoom the zoom
         * @param count the count
         * @param from the from
         */
        public State( Point p, int zoom, int count )
        {
            this.p = p;
            this.zoom = zoom;
            this.count = count;
            //this.from = from;
        }
        
        /**
         * To string for debugging.
         *
         * @return the string
         */
        @Override
        public String toString()
        {
            return "{" + p + "," + zoom + "," + count + "[" + (p.x-power2[zoom]) + ".." + (p.x + power2[zoom]) + "]}";
        }
        
//        /**
//         * Trace the path of States for debugging.
//         */
//        public void trace()
//        {
//            if( from!=null ) from.trace();
//            System.err.println( this );
//        }
    }
    
    /**
     * Do it!.
     */
    private void doit()
    {    
        // Powers of 2 will be useful.
        power2 = new int[32];
        power2[0] = 0; // This is for zoom of 0.5
        power2[1] = 1;
        for( int i=2; i<power2.length; i++ ) power2[i] = power2[i-1]<<1;
        
        n = sc.nextInt();
        k = sc.nextInt();
               
        // Build up the quad tree
        Segment root = new Segment( -100000000, 100000000 );
        for( int i=1; i<=n; i++ ) root.add( new Point(sc.nextInt(), i ) );
                
        // There's no need to look at any viewport more than once.
        // This will keep track of whether we've already handled a viewport.
        // visited[i][j] represents the viewport centered at point j, zoomed out i times
        boolean visited[][] = new boolean[power2.length][n+1];
        for( int i=0; i<power2.length; i++ ) 
        {
            Arrays.fill( visited[i], false );
        }
        visited[0][0] = true;
                
        // best[i] is the least number of steps for point i
        // We've got an artificial point at 0,0 to start us off. 
        // That's why it's of size n+1
        int best[] = new int[n+1];
        Arrays.fill( best, -1 );
                
        // OK, now it's just a Breadth-First Search
        MaxQueue points = new MaxQueue(k);
        ArrayDeque<State> queue = new ArrayDeque<State>(n*32+1);
        queue.add( new State( new Point( 0, 0 ), 1, 0 ) );
        while( !queue.isEmpty() )
        {
            State s = queue.pop();
            Point p = s.p;            
            points.clear();
            int zoom = s.zoom;
            int vhi = p.x + power2[zoom];   
            int vlo = p.x - power2[zoom];   
            root.search( vlo, vhi, points );
            
            if( best[p.weight]<0 && points.contains( p ) ) 
            {
                best[p.weight] = s.count;
            }
            
            // Try zooming out
            if( s.zoom<31 && !visited[s.zoom+1][p.weight] )
            {
                queue.add( new State( p, s.zoom+1, s.count+1 ) );
                visited[s.zoom+1][p.weight] = true;
            }
            
            // Try zooming in
            if( s.zoom>0 && !visited[s.zoom-1][p.weight] )
            {
                queue.add( new State( p, s.zoom-1, s.count+1 ) );
                visited[s.zoom-1][p.weight] = true;
            }
            
            // Try moving to all the points in the viewport

            for( Point point : points )
            {
                if( !visited[s.zoom][point.weight] )
                {
                    queue.add( new State( point, s.zoom, s.count+1 ) );
                    visited[s.zoom][point.weight] = true;
                }
            }
        }
        
        for( int i=1; i<=n; i++ ) ps.println( best[i] ); 
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
        
        new triptik_vanb().doit();
    }

}
