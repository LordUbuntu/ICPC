
import java.io.*;
import java.util.*;
import java.awt.geom.*;

/**
 * Solution to Containment
 * 
 * @author vanb
 */
public class Containment2
{
    public PrintStream ps;
       
    /**
     * A node of a max flow graph.
     * 
     * @author vanb
     */
    public class Node
    {
        // Has the node been visited?
        public boolean visited = false;
        
        // List of edges from this node
        public LinkedList<Edge> edges = new LinkedList<Edge>();
        
        // If this node has been visited, what edge did we get here from?
        public Edge gothere = null;
    }
    
    /**
     * An edge of a max flow graph.
     * 
     * @author vanb
     */
    public class Edge
    {
        Node destination;
        int capacity;
        Edge dual;
        
        public Edge( Node d, int c )
        {
            capacity = c;
            destination = d;
            dual = null;
        }
    }
    
    /**
     * Create a link between two nodes of a maxx flow graph.
     * 
     * @param n1 From node
     * @param n2 To node
     * @param cost Cost to go from n1 to n2
     */
    public void link( Node n1, Node n2, int cost )
    {
        Edge e12 = new Edge( n2, cost );
        Edge e21 = new Edge( n1, 0 );
        e12.dual = e21;
        e21.dual = e12;
        n1.edges.add( e12 );
        n2.edges.add( e21 );
    }
    
    /** Queue for the Ford/Fulkerson algorithm */
    public LinkedList<Node> queue = new LinkedList<Node>();
    
    /**
     * Perform the Ford/Fulkerson algorithm on a graph.
     * 
     * @param src Source node
     * @param snk Sink node
     * @param nodes The graph, represented as a list of nodes
     * @return The max flow from the source to the sink
     */
    public int fordfulkerson( Node src, Node snk, List<Node> nodes )
    {
        int total = 0;
        
        // Keep going until you can't get from the source to the sink
        for(;;)
        {  
            // Reset the graph
            for( Node node : nodes )
            {
                node.visited = false;
                node.gothere = null;
            }

            // Reset the queue
            // Start at the source
            queue.clear();
            queue.add( src );
            src.visited = true;
            
            // Have we found the sink?
            boolean found = false;
            
            // Use a breadth-first search to find a path from the source to the sink
            while( queue.size()>0 )
            {
                Node node = queue.poll();
                
                // have we found the sink? If so, break out of the BFS.
                if( node==snk )
                {
                    found = true;
                    break;
                }
                
                // Look for edges to traverse
                for( Edge edge : node.edges )
                {
                    Node dest = edge.destination;
                    
                    // If this destination hasn't been visited,
                    // and the edge has capacity, 
                    // put it on the queue.
                    if( edge.capacity>0 && !dest.visited )
                    {
                        // Node has been visited
                        dest.visited = true;
                        
                        // Remember the edge that got us here
                        dest.gothere = edge;
                        
                        // Add to the queue
                        queue.add( dest );
                    }
                }
            }
            
            // If we were unable to get to the sink, then we're done
            if( !found ) break;
            
            // Otherwise, look along the path to find the minimum capacity
            int min = Integer.MAX_VALUE;
            for( Node node = snk; node.gothere != null; )
            {
                Edge edge = node.gothere;
                if( edge.capacity < min ) min = edge.capacity;
                node = edge.dual.destination;
            }
            
            // Add that minimum capacity to the total
            total += min;
            
            // Go back along the path, and for each edge, move the min
            // capacity from the edge to its dual.
            for( Node node = snk; node.gothere != null; )
            {
                Edge edge = node.gothere;
                edge.capacity -= min;
                edge.dual.capacity += min;
                node = edge.dual.destination;
            }        
        }
        
        // Return the total
        return total;
    }

    /**
     * Driver.
     * @throws Exception
     */
    public void doit(Scanner sc) throws Exception
    {
        ps = System.out; 
        
        LinkedList<Node> nodes = new LinkedList<Node>();

        Node source = new Node(); nodes.add( source );
        Node sink = new Node(); nodes.add( sink );
        Node ins[][][] = new Node[10][10][10];
        Node outs[][][] = new Node[10][10][10];
        boolean failure[][][] = new boolean[10][10][10];
        for( int i=0; i<10; i++ )for( int j=0; j<10; j++ )for( int k=0; k<10; k++ )
        {
            ins[i][j][k] = new Node(); nodes.add( ins[i][j][k] );
            outs[i][j][k] = new Node(); nodes.add( outs[i][j][k] );
            link( ins[i][j][k], outs[i][j][k], 6 );
            failure[i][j][k] = false;
        }
        
        int n = sc.nextInt();
        for( int i=0; i<n; i++ )
        {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int z = sc.nextInt();
            failure[x][y][z] = true;
        }
                
        for( int i=0; i<10; i++ ) for( int j=0; j<10; j++ ) 
        {
            link( source, ins[i][j][0], 1 );
            link( source, ins[i][j][9], 1 );
            link( source, ins[i][0][j], 1 );
            link( source, ins[i][9][j], 1 );
            link( source, ins[0][i][j], 1 );
            link( source, ins[9][i][j], 1 );
        }
        
        for( int i=0; i<10; i++ )for( int j=0; j<10; j++ )for( int k=0; k<10; k++ )
        {
            if( failure[i][j][k] ) link( outs[i][j][k], sink, 6 );
            else
            {
                if( i>0 ) link( outs[i][j][k], ins[i-1][j][k], 1 );
                if( i<9 ) link( outs[i][j][k], ins[i+1][j][k], 1 );
                if( j>0 ) link( outs[i][j][k], ins[i][j-1][k], 1 );
                if( j<9 ) link( outs[i][j][k], ins[i][j+1][k], 1 );
                if( k>0 ) link( outs[i][j][k], ins[i][j][k-1], 1 );
                if( k<9 ) link( outs[i][j][k], ins[i][j][k+1], 1 );
            }
        }
        
        ps.println( fordfulkerson( source, sink, nodes ) );        
    }
    
    /**
     * @param args
     */
    public static void main( String[] args ) throws Exception
    {
        Scanner sc = new Scanner(System.in) ;
        int t = sc.nextInt() ;
        while (t-- > 0)
           new containment_vanb().doit(sc);
    }   
}
