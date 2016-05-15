
public class Level1Driver {

	//Implements the driver function for Level 1 of LP1.
public static void testLevel0(){
	BigNum a = new BigNum("1234567890123456789012345678901234567890");
	BigNum b = new BigNum(999L);
	BigNum c = BigNum.add(a, b);
	BigNum d = BigNum.subtractUtil(c, a);
	BigNum e = BigNum.product(c, a);
	BigNum zero = new BigNum(0L);
	BigNum f = BigNum.product(a, zero);
	BigNum two = new BigNum(2L);
	BigNum g = BigNum.power(two, 1025L);
	System.out.println("a = " + a);
	System.out.println("b = " + b);
	System.out.println("c=a+b = " + c);
	System.out.println("a+b-a = " + d);
	System.out.println("a*c = " + e);
	System.out.println("a*0 = " + f);
	System.out.println("2^1025 = " + g);
	System.out.println("Internal representation:");
	g.printList();
}
}