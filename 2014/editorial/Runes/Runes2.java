import java.util.*;
import java.util.regex.*;
import java.io.*;

/**
 * Solution to Runes
 * 
 * @author vanb
 */
public class Runes2
{
    Scanner sc;
    PrintStream ps;
    
    public static final char digits[] = "0123456789".toCharArray();
        
    public void doit(Scanner sc) throws Exception
    {
        ps = System.out;
        
        String expression = sc.next();
        
        char e[] = expression.toCharArray();
        
        // Find operator, =
        int oppos=0; // Operator position
        int eqpos=0; // Equals position
        for( int i=1; i<expression.length(); i++ )
        {
            if( (e[i-1]=='?' || Character.isDigit( e[i-1] )) && (e[i]=='+' || e[i]=='-' || e[i]=='*') ) oppos = i;
            if( e[i]=='=' ) eqpos = i;
        }
        
        // Separate out the three terms and the operator
        String t1 = expression.substring( 0, oppos );
        String t2 = expression.substring( oppos+1, eqpos );
        String t3 = expression.substring( eqpos+1 );
        char op = expression.charAt( oppos );
        
        boolean itworks = false;
        char digit = ' ';
        
        // If there's a ? at the beginning of any of the terms,
        // and that term is longer than a single digit, then
        // we can't use 0, since we can't have leading 0s 
        // except for 0 itself.
        boolean okzero = true;
        if( t1.charAt( 0 )=='?' && t1.length()>1 ) okzero = false;
        else if( t2.charAt( 0 )=='?' && t2.length()>1 ) okzero = false;
        else if( t3.charAt( 0 )=='?' && t3.length()>1 ) okzero = false;
        
        // Go through all of the digits
        for( int d=okzero?0:1; d<10 && !itworks; d++ )
        {
            digit = digits[d];
            
            // If this digit appears anywhere in the expression,
            // then ? can't be this digit. Keep looking.
            if( expression.indexOf( digit )>=0 ) continue;
            
            try
            {
                long term1 = Long.parseLong( t1.replace( '?', digit ) );
                long term2 = Long.parseLong( t2.replace( '?', digit ) );
                long term3 = Long.parseLong( t3.replace( '?', digit ) );
                
                long result=0;
                switch( op )
                {
                    case '+' : result = term1+term2; break;   
                    case '-' : result = term1-term2; break;   
                    case '*' : result = term1*term2; break; 
                    default: System.err.println( "PANIC!! op is " + op );
                }
                
                itworks = (result==term3);
            }
            catch( NumberFormatException nfe ){}
        }
        
        ps.println( itworks ? (""+digit) : "-1" );
    }
    
    public static void main( String[] args ) throws Exception
    {
        Scanner sc = new Scanner( System.in );
        int cases = sc.nextInt() ;
        for (int i=0; i<cases; i++)
           new runes_vanb().doit(sc);
    }

}
