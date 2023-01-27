import java.io.File;
import java.util.Scanner;

public class Polyhedra {

	public static void main( final String[] args ) throws Exception {

		//Scanner scanner = new Scanner( new File( "polyhedra.in" ) );
		Scanner scanner = new Scanner( System.in );
		int T = scanner.nextInt();
		for ( int tc = 0; tc < T; tc++ ) {
			int v = scanner.nextInt();
			int e = scanner.nextInt();

			int f = 2 - ( v - e );

			System.out.println( f );
		}

		scanner.close();
	}
}
