
import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/** Sample driver code for Project LP4.  Modify as needed.
 *  Do not change input/output formats.
 *  /**

 * @author : Team G12
 *
 * Anirudh K V
 * Malini K Bhaskaran
 * Neha Nirmala Srinivas
 * Saumya Ann George
 */
public class LP4Driver {
    static long[] description;
    static final int DLENGTH = 100000;

    public static void main(String[] args)  throws FileNotFoundException {
	Scanner in;
	long timeStart =System.currentTimeMillis();
	if(args.length > 0) {
	    in = new Scanner(new File(args[0]));
        } else {
	    in = new Scanner(System.in);
	}
	String s;
	double rv = 0;
	description = new long[DLENGTH];

	Timer timer = new Timer();
	MDS mds = new MDS();

	
	long timeTotal =0;
	while(in.hasNext()) {
	    s = in.next();
	    if(s.charAt(0) == '#') {
		s = in.nextLine();
		continue;
	    }
			if (s.equals("Insert")) {
				long id = in.nextLong();
				double price = in.nextDouble();
				long des = in.nextLong();
				int index = 0;
				while (des != 0) {
					description[index++] = des;
					des = in.nextInt();
				}
				description[index] = 0;
				long timeStrt = System.currentTimeMillis();
				rv += mds.insert(id, price, description, index);
				long timeEnd = System.currentTimeMillis();
				
				timeTotal = timeTotal + (timeEnd - timeStrt);
			} else if (s.equals("Find")) {
				long id = in.nextLong();
				long timeStrt = System.currentTimeMillis();
				rv += mds.find(id);
				long timeEnd = System.currentTimeMillis();
				timeTotal = timeTotal + (timeEnd - timeStrt);
				
			} else if (s.equals("Delete")) {
				long id = in.nextLong();
				long timeStrt = System.currentTimeMillis();
				rv += mds.delete(id);
				long timeEnd = System.currentTimeMillis();
				timeTotal = timeTotal + (timeEnd - timeStrt);
				
			} else if (s.equals("FindMinPrice")) {
				
				long des = in.nextLong();
				long timeStrt = System.currentTimeMillis();
				rv += mds.findMinPrice(des);
				long timeEnd = System.currentTimeMillis();
				timeTotal = timeTotal + (timeEnd - timeStrt);
			
			} else if (s.equals("FindMaxPrice")) {
				long des = in.nextLong();
				long timeStrt = System.currentTimeMillis();
				rv += mds.findMaxPrice(des);
				long timeEnd = System.currentTimeMillis();
				timeTotal = timeTotal + (timeEnd - timeStrt);
			
			} else if (s.equals("FindPriceRange")) {
				long des = in.nextLong();
				double lowPrice = in.nextDouble();
				double highPrice = in.nextDouble();
				long timeStrt = System.currentTimeMillis();
				rv += mds.findPriceRange(des, lowPrice, highPrice);
				long timeEnd = System.currentTimeMillis();
				timeTotal = timeTotal + (timeEnd - timeStrt);
			
			} else if (s.equals("PriceHike")) {
				long minid = in.nextLong();
				long maxid = in.nextLong();
				double rate = in.nextDouble();
				long timeStrt = System.currentTimeMillis();
				rv += mds.priceHike(minid, maxid, rate);
				long timeEnd = System.currentTimeMillis();
				timeTotal = timeTotal + (timeEnd - timeStrt);
			
			} else if (s.equals("Range")) {
				double lowPrice = in.nextDouble();
				double highPrice = in.nextDouble();
				long timeStrt = System.currentTimeMillis();
				rv += mds.range(lowPrice, highPrice);
				long timeEnd = System.currentTimeMillis();
				timeTotal = timeTotal + (timeEnd - timeStrt);
			
			} else if (s.equals("SameSame")) {
				rv += mds.samesame();
			} else if (s.equals("End")) {
				break;
			} else {
				System.out.println("Houston, we have a problem.\nUnexpected line in input: " + s);
				System.exit(0);
			}
	}
	long timeOut = System.currentTimeMillis();
	DecimalFormat df = new DecimalFormat("#.##");
    df.setRoundingMode(RoundingMode.HALF_EVEN);
    BigDecimal  dec = new BigDecimal(rv);    
    System.out.println(df.format(dec));
	System.out.println("Time taken excluding read operation in ms "+timeTotal);
	System.out.println("Time taken including read operation in ms "+(timeOut-timeStart));
    }
}