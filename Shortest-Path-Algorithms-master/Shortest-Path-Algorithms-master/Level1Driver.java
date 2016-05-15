/**
 * Level1 Driver 
 * @author : Team G12
 *
 * Anirudh K V
 * Malini K Bhaskaran
 * Neha Nirmala Srinivas
 * Saumya Ann George
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level1Driver {
/**
 * Test class
 * @param args
 * @throws FileNotFoundException
 */
	public static void main(String[] args) throws FileNotFoundException {
		ShortestPathUtil shortestPathUtil = new ShortestPathUtil();
		Scanner in ;
	
		if (args.length > 0) {
			in = new Scanner(new File(args[0]));
		} else {
			in = new Scanner(System.in);
		}
		
		Graph g = Graph.readGraph(in, true);
		shortestPathUtil.runLevel1(g);
		in.close();

	}

}
