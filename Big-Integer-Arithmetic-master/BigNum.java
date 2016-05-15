import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*Program to implement arithmetic with large integers 
 * 
 * Level 1 and Level 2 are done as part of this project
 * 
 * @author Group 12
 */


/**
 * Class to implement BigNum Functionalities
 */
public class BigNum {	

	public List<Long> bigNum = new ArrayList<Long>(); //Number representation
	private boolean isNeg;  //To indicate whether the number is negative or not
	final static Integer base = 100;  //Base in which the number is represented		
	final static int numDigInNode = Integer.toString(base).length()-1;
	
	/**
	 * BigNum class String Constructor
	 * @param S -String Value
	 */
	BigNum(String S) {
		if (S.charAt(0) == '-') {
			this.isNeg = true;
		}
		this.bigNum = numToLinkedList(S);		
	}

	/**
	 * BigNum String Constructor
	 * @param num-String Value
	 */
	BigNum(Long num) {
		if (num < 0)
			this.isNeg = true;
		this.bigNum = numToLinkedList(num);		
	}

	/**
	 * BigNum Empty Default Constructor
	 */
	public BigNum() {
		super();		
	}

	/**
	 * Function to convert Long value to Internal representation
	 * @param num Convert a long integer value to a BigNum represenation
	 * @return -List representation of Long 
	 */
	private static List<Long> numToLinkedList(Long num) {
		Long rem = 0L;
		List<Long> number = new ArrayList<Long>();
		if(num==0)
		{
			number.add(num);
		}
		else
		{
		while (num > 0) {
			rem = (Long) (num % base);
			num = num / base;			
			number.add(rem);
		}
		}
		return number;
	}

	/**
	 * Number to Linked List representation of String
	 * @param value Stores the string value in to the Array List nodes based on the base
	 * @return - List representation of Number
	 */
	private List<Long> numToLinkedList(String value) {
		String str = new String();
		str = " ";
		String strNew = new String();
		List<Long> number = new ArrayList<Long>();
		boolean neg = false;
		if(value.charAt(0)=='-')
		{
			neg = true;
			value = value.substring(1,value.length());
		}
		Integer length = value.length();
		for (int i = length - 1; i >= 0; i--) {
			str = value.charAt(i) + str.trim();
			if (str.charAt(0) != '0') // To avoid cases like "07"
			{
				if (Integer.valueOf(str) < base && str.length() < (Integer.toString(base).length() - 1)) {
					strNew = str;
					if (i != 0)
						continue;
					else {
						number.add(Long.valueOf(strNew.toString()));
					}
				} else {
					number.add(Long.valueOf(str.toString()));
					strNew = " ";
					str = "";
					if (i + 1 == 0) {
						break;
					} else {
						value = value.substring(0, i);
					}
				}
			} else {
				if (str.length() == (Integer.toString(base).length() - 1)) {
					number.add(Long.valueOf(str.toString()));
					strNew = " ";
					str = "";
				}
				else if(str.equals("0")&&value.length()==1)
				{
					number.add(Long.valueOf(str.toString()));
					strNew = " ";
					str = "";
				}
			}
		}
		return number;
	}

	/**
	 * Function to perform bignum addition
	 * @param a - First number
	 * @param b - Second Number
	 * @return - Result 
	 */
	public static BigNum add(BigNum a, BigNum b) {
		// Add the numbers in the 2 linked List and store the result into the
		// output list
		// Parameters: list 1 - number1
		// list 2 - number2
		// base - base of the number
		// Output : res - Result
		Long carry = 0L;

		Long currSum = 0L;
		Iterator<Long> it1 = a.bigNum.iterator();
		Iterator<Long> it2 = b.bigNum.iterator();
		BigNum z = new BigNum();
		
		//For negative number case
		//If both numbers are having same sign, then perform addition
		if((!a.isNeg&&!b.isNeg)||(a.isNeg&&b.isNeg))
		{
		// Check if Lists are not empty
		while (it1.hasNext() || it2.hasNext()) {
			// Both lists are not empty
			if (it1.hasNext() && it2.hasNext()) {
				currSum = it1.next() + it2.next() + carry;
				carry = 0L;
			}
			// First list is not empty, Second list is null
			else if (it1.hasNext()) {
				currSum = it1.next() + carry;
				carry = 0L;
			}
			// Second list is not empty, First list is null
			else {
				currSum = it2.next() + carry;
				carry = 0L;
			}
			// Calculate the carry value by checking the value in the currSum is
			// greater than the base
			if (currSum >= base) {
				carry = (Long) (currSum / base);
				currSum = (Long) (currSum % base);
			}
			// Add value to the sum List
			z.bigNum.add(currSum);
		}
		// adds last index in the result if the carry is non zero
		if (carry > 0)
			z.bigNum.add(carry);		
		
	
		//If both numbers are negative numbers then add negative sign in the last bit
		if(a.isNeg&&b.isNeg)
		{
			z.isNeg = true;
		}	
		}
			
		else 
		{
			//If one value is negative and one is positive			
			int great = compare(a,b);
			if(great==1)
			z = subtract(a,b);
			else
			z=subtract(b,a);
			z.bigNum=removeLeadingZero(z);
			if((a.isNeg&&great==1)||(b.isNeg&&great!=1))
			{
				z.isNeg = true; //Mark the number as negative  
			}			
		}
		return z;

	}

	/**
	 * Function to perform Subtraction between two big numbers
	 * @param a -First bignumber
	 * @param b -Second bigNumber
	 * @return -Result 
	 */
	public static BigNum subtract(BigNum a, BigNum b) {
		// Function to perform subtraction
		// Parameters : List num1 - Bigger number
		// List num2 - Smaller number
		// List res - result list for output
		// base - Base of the number
		Long borrow = 0L;
		Long element1;
		Long element2;
		Long currSub;
		// Lists are not completely traversed
		BigNum reslt = new BigNum();
		Iterator<Long> list1 = a.bigNum.iterator();
		Iterator<Long> list2 = b.bigNum.iterator();
		List<Long> res = new ArrayList<Long>();
		currSub = 0L;
		while (list1.hasNext() && list2.hasNext()) {
			// Both lists have numbers and the num1 digit is greater than num2
			// digit
			element1 = list1.next();
			element2 = list2.next();
			if (element1 >= element2) {
				// Num1 digit is greater number than the sum of num2 digit and
				// borrow

				if ((element2 + currSub) <= element1) {
					currSub = element1 - (element2 + currSub);
					borrow = 0L; // No borrow
				} else {
					// If num1 digit is lesser than the sum of borrow and num2
					// digit (Happens when both digits are same)
					// currSub = element1+10 - (element2+currSub);
					if(base>=100)
    				{
					while (currSub.toString().length() < (String.valueOf(base).length() - 1)) {
						currSub = borrow;
						currSub = element1 + base - (element2 + currSub);
						borrow = 1L;
					}
					}
					else
					{
						currSub = element1+base - (element2+currSub);
						borrow = 1L;
					}
				}

			}
			// Num2 digit is greater than the num1 digit
			else {
				currSub = element1 + base - (element2 + currSub);
				borrow = (long) 1;
				
			}
			// Add the result to the list
			res.add(currSub);
			currSub = borrow;
		}
		// Num1 digits are not ended and num2 is over
		while (list1.hasNext() && (!list2.hasNext())) {
			element1 = list1.next();
			if (element1 < currSub) {
				currSub = element1 + base - currSub;
				borrow = 1L;
			} else {
				currSub = element1 - currSub;
				borrow = 0L;
			}
			// Add the result to the list
			res.add(currSub);
			currSub = borrow;
		}	
		reslt.bigNum = res;
		return reslt;

	}

	/**
	 * Function to perform necessary checks in Subtraction and call the function subtract with greater num first
	 * @param number1
	 * @param number2
	 * @return Result
	 */
	public static BigNum subtractUtil(BigNum number1, BigNum number2) {
		// Utility function to check if the first number is greater than second
		// number for performing subtraction
		// If first number is less than second number, then second number is
		// passed as the first parameter to the subtract call
		// Added - sign to the result in that case
		BigNum subResult = new BigNum();
		List<Long> subRes = new ArrayList<Long>();
		if(!number1.isNeg&&!number2.isNeg)
		{
		// In case if a bigger number is subtracted from a shorter number
		Boolean greaterNum = checkGreaterNum(number1, number2);
		// If checks returns false , it indicates the second number was greater
		// than the first one
		if (!greaterNum) {
			subResult = subtract(number2, number1);
			// Remove leading zeros from the resultant list
			subRes = removeLeadingZero(subResult);
			// Add negative sign to the result
			subResult.isNeg = true;
		} else {
			subResult = subtract(number1, number2);
			// Remove leading zeros from the resultant list
			subRes = removeLeadingZero(subResult);			
		}
		
		}
		else if(number1.isNeg&&number2.isNeg)
		{
			//If both numbers are negative perform addition
			int greatNum = compare(number1, number2);
			if(greatNum==0)
			{
				subResult = subtract(number2,number1);
				subRes = removeLeadingZero(subResult);
			}
			else
			{
			subResult = subtract(number1,number2);
			subRes = removeLeadingZero(subResult);
			subResult.isNeg = true;
			}
			
			
		}
		else if(number1.isNeg&&!number2.isNeg)
		{
			//If the only one number is neg
			//-a-b : Add a, b and give -ve sign 	
			number1.isNeg = false;
			subResult = add(number2, number1);
			number1.isNeg = true;
			subRes = removeLeadingZero(subResult);
			subResult.isNeg = true;
						
		}
		else if(!number1.isNeg&&number2.isNeg)
		{
			//If the only one number is neg
			//a- -b : just add a and b
			number2.isNeg = false;
			//Since number2 is greater 
			subResult = add(number1, number2);
			subRes = removeLeadingZero(subResult);	
			number2.isNeg = true;
			
		}
		subResult.bigNum = subRes;
		// Return the result List		
		return subResult;
	}
	
	
	/**
	 * Function to check whether the 2 numbers are greater
	 * @param number1
	 * @param number2
	 * @return true if first num is greater /false if first num is smaller
	 */
	public static Boolean checkGreaterNum(BigNum number1, BigNum number2) {
		// Function to check if the number1 is greater than the number2
		Integer num1Size = number1.bigNum.size();
		if ((number1.isNeg && number2.isNeg) || (!number1.isNeg && !number1.isNeg)) {
			
			int ret = compare(number1, number2);
			if(ret==2||ret==1)
				return true;
			else
				return false;
			
		} else {
			if (number1.isNeg && !number2.isNeg) {
				return false; // If number1 is negative and number2 is non
								// negative
			} else {
				return true; // if number2 is negative number 1 is not negative
			}
		}
		
	}
	
	/**
	 * Removes leading zero from the result list
	 * @param subRes -result list with leading zeros if exists
	 * @return list without leading zeros 
	 */

	public static List<Long> removeLeadingZero(BigNum subRes) {
		// Function to remove Leading Zeroes in the subtraction Result
		for (int i = (subRes.bigNum.size() - 1); i >= 0; i--) {
			// To avoid Leading zeros
			// Removes all leading zeros except at the first position (If the
			// result is zero)
			if ((subRes.bigNum.get(i) == 0 && i != 0)) {
				subRes.bigNum.remove(i);
			} else
				break;
		}
		return subRes.bigNum;
	}

	/**
	 * Function to perform product of 2 big numbers
	 * @param a
	 * @param b
	 * @return result product
	 */
	public static BigNum product(BigNum a, BigNum b)
	{	
		
		Long res  =0L;
		List<Long> pdt = new ArrayList<Long>();
		BigNum[] arrPdts = new BigNum[b.bigNum.size()];
		BigNum finalPdt = new BigNum();
		Long carry =0L;
		BigNum zero = new BigNum((long)0);
		if((compare(a, zero)==2)||(compare(b, zero)==2))
		{
			finalPdt.bigNum.add(0L);
			return finalPdt;
		}
		for(int i=0;i<b.bigNum.size();i++)
		{
			pdt.clear();
			for(int k=0;k<i;k++)
			{
				pdt.add(0L);   //Shifting purpose for actual addition
			}
			carry=0L;
			for(int j=0;j<a.bigNum.size();j++)
			{
				res = a.bigNum.get(j)*b.bigNum.get(i)+carry;
				carry =0L;
				if(res>base)
				{
					carry = res/base;
					res = res%base;
				}
				pdt.add(res);				
			}
			if(carry>0)
			{
				pdt.add(carry);
				
			}
			arrPdts[i]=new BigNum();
			arrPdts[i].bigNum.addAll(0,pdt); //Add all intermediate products to array			
			
		}
		
		for(int i=0;i<arrPdts.length;i++)
		{
			finalPdt = add(finalPdt,arrPdts[i]);
		}
		
		if((!a.isNeg&&b.isNeg)||(a.isNeg&&!b.isNeg))
		{
			finalPdt.isNeg = true;
		}
		
		return finalPdt;
		
		
	}
	
	/**
	 * Function to convert the List representation to String representation
	 * Returns : string value of the number stored in list
	 */

	public String toString() {
		
		String str = new String();
		int length = 0;
		for (int i = 0; i < this.bigNum.size(); i++) {
			str = this.bigNum.get(i) + str;
			length = this.bigNum.get(i).toString().length();		
			if (i != this.bigNum.size() - 1) {				
				while (length < (String.valueOf(base).toString().length() - 1)) {
					str = "0" + str;
					length++;
				}
			}			
		}
		if(this.isNeg&&str.charAt(0)!='-'&& !str.equals("0"))
		{
			str = "-"+str;
		}
		return str;
	}

	/**
	 * Prints the list with base information
	 */
	public void printList() {
		System.out.print("base:" + base);
		for (int i = 0; i < this.bigNum.size(); i++) {
			System.out.print(" " + this.bigNum.get(i));
		}
	}
	/**
	 * Function to compute factorial of a number
	 * @param x -number to calculate factorial
	 * @return - result of factorial
	 */
	public static BigNum factorial(BigNum x)
	{
		BigNum fact = new BigNum((long)1);
		if(!x.isNeg)
		{		
		BigNum p = new BigNum((long)1);
		BigNum inc = add(x,new BigNum((long)1));
		while(compare(inc, p)!=2)
	    {
	    	fact = product(fact,p);
	    	p = add(p,new BigNum((long)1));
	    }
		}
		else
		{
			System.out.println("Factorial not defined for negative numbers");
			fact.bigNum.remove(0);
			fact.bigNum.add(0L);
		}
		return fact;
	}
	
	public static BigNum power(BigNum x, Long n) { 
		BigNum half = new BigNum();
		BigNum res = new BigNum();
		if (n == 0) {
			BigNum value = new BigNum(1L);
			return value;
		} else if (n == 1)
			return x;
		else
			half = power(x, n / 2);
		res = BigNum.product(half, half);
		if (n % 2 == 0)
			return res;
		else
			return BigNum.product(res, x);
	}
	
	
	void shift_left(int a[], int n) {
		int i;

		Integer length = a.length;
		for (i = length - 1; i >= n; i--) {
			a[i] = a[i - n];
		}

		while (i >= 0) {
			a[i--] = 0;
		}
		
	}
	
	public static BigNum shift_left(BigNum n) {

 		BigNum value = new BigNum();
		int length = n.bigNum.size();
		List<Long> bigList = n.bigNum;
		List<Long> shiftList = new ArrayList<>();
		for (int i = 1; i < length; i++) {
			shiftList.add(bigList.get(i));
		}
		value.bigNum = shiftList;

		return value;
	}

	public static BigNum power(BigNum x, BigNum n) {

		Long a0 = null;
		int length = n.bigNum.size();
		if (length == 1) {
			a0 = n.bigNum.get(0);
			return power(x, a0.longValue());
		} else {
			BigNum s = shift_left(n);
			BigNum xToTheS = power(x, s);
			BigNum value1 = power(xToTheS, n.base.longValue());
			BigNum value2 = power(x, n.bigNum.get(0).longValue());
			return BigNum.product(value1, value2);
		}
	}
	
	/** Method to divide an integer by 2 faster */
	public static String divideBy2(String s) {
		//q- quotient obtained while dividing by 2
		//carry- remainder obtained while dividing by 2; it will be either 1 or 0
		String q = "";
		int carry = 0;
		for (int i = 0; i < s.length(); i++) {
			int d = s.charAt(i) - '0';
			q += (d + 10 * carry) / 2;
			carry = d % 2;
		}

		// Remove any leading 0's from the string 
		if (q.charAt(0) == '0' && q.length() > 1) {
			int i = 1;
			while (i < q.length() && q.charAt(i) == '0') {
				i++;
			}
			//Obtain the substring from the position where all leading 0's are skipped
			q = q.substring(i);
			if (q.length() == 0) {
				q = "0";
			}
		}

		return q;
	}

	/**
		 * 
		 * @param n1 1st BigNum to compare
		 * @param n2 2nd BigNum to compare
		 * @return 0 if n1<n2
		 * @return 1 if n1>n2
		 * @return 2 if n1=n2
		 */
		public static int compare(BigNum n1, BigNum n2){
			Integer n1Size=n1.bigNum.size();
			Integer n2Size=n2.bigNum.size();
			if(n1Size < n2Size)
				return 0;
			else
				if(n1Size> n2Size)
					return 1;
			int i = n2.bigNum.size() - 1;
			while (i >= 0) {
				Long element1 = n1.bigNum.get(i);
				Long element2 = n2.bigNum.get(i);
				if (element1 < element2) {
					return 0;
				} else if (element1 > element2) {
					return 1;
				}
				// Decrement the list index
				i--;
			}
			return 2;
			
		}

		/** Function to find square root of Big Integer
		 * 
		 * @param a BigNum 
		 * @return square root of a of type BigNum
		 */
		public static BigNum squareRoot(BigNum a){
			//mid - midpoint of low and high
			boolean isNeg=a.isNeg;
			a.isNeg=false;
			BigNum low=new BigNum("1");
			BigNum high=a;
			BigNum mid;
			String midPoint;
			
			while(compare(low, high)==0){
				
				midPoint=divideBy2(add(low,high).toString());				
				mid=new BigNum(midPoint);
				
				BigNum prod=product(mid, mid);
				
				//subRes - Difference between high and low after removing leading zeros
				List<Long> subRes=new ArrayList<>();
				subRes=removeLeadingZero(subtract(high, low));
				BigNum sub=new BigNum();
				sub.bigNum=subRes;
				
				/* mid is the square root of non perfect square */
				if(sub.toString().equals("1")){
					a.isNeg=isNeg;
					return mid;
				}
				/* mid is the square root of perfect square */
				if(compare(a,prod)==2){
					a.isNeg=isNeg;
					return mid;
				}
				else if(compare(a, prod)==0){
					high=mid;
				}
				else if(compare(a, prod)==1){
					low=mid;
				}
				else{
					a.isNeg=isNeg;
					return mid;
				}
			}
		
			a.isNeg=isNeg;
			return low;		
			
		}
		/**
		 * Function to perform division of two Big Integers
		 * 
		 * @param1 num BigNum
		 * @param2 den BigNum
		 * @return quotient of a type BigNum
		 */

		public static BigNum divide(BigNum num, BigNum den) {
			BigNum a = new BigNum(num.abs());
			BigNum b = new BigNum(den.abs());
			BigNum low = new BigNum("1");
			BigNum high = new BigNum(a.abs());
			BigNum mid = new BigNum("0");
			boolean flaga = false, flagb = false;

			if (den.isNeg) {
				flagb = true;
				b.isNeg = false;
			}
			if (num.isNeg) {
				flaga = true;
				a.isNeg = false;
			}

			// If numerator is equal to denominator return 1
			if (compare(b, a)==2) {
				if (flaga ^ flagb)
					return new BigNum("-1");
				else
					return new BigNum("1");
			}
           
			//if denominator is greater than numerator return 0
			if (checkGreaterNum(b, a)) {
				System.out.println("Denominator greater than numerator");
				return new BigNum("0");
			}

			/*
			 * Find the quotient By checking for the quotient in the range 1 to
			 * numerator using binary search technique.
			 */
			if (compare(b, new BigNum("0"))!=2) {
				while (checkGreaterNum(high, low)) {
					//Find the mid point of low and high
					String m = divideBy2(add(low, high).toString());
					mid = new BigNum(m);
					//Find the product of possible quotient and denominator.
					BigNum temp = BigNum.product(mid, b);
					// Find the reminder of the numerator and product calculated.
					BigNum temp1 = BigNum.subtract(a, temp);
					temp1.bigNum = removeLeadingZero(temp1);
					/* If the product is equal to the numerator or the denominator is greater than reminder break
					 * If the product is lower than the numerator search quotient in the upper half, 
					 * else search for quotient in lower half. 
					 */
					if ((compare(temp, a)==2) || (checkGreaterNum(b, temp1) && (compare(b, temp1)!=2))) {
						break;
					} else if (checkGreaterNum(a, temp)) {
						low = BigNum.add(mid, new BigNum("1"));
					} else {
						high = BigNum.add(mid, new BigNum("-1"));
					}
				}
			} else {
				System.out.println("division by zero can not be performed");
				return null;
			}

			//If either numerator or denominator was negative set isNeg flag.
			if (flaga ^ flagb) {
				mid.isNeg = true;
			}
			return mid;
		}
		/**
		 * Function to convert the List representation to Absolute value String
		 * representation Returns : string value of the absolute value number stored
		 * in list
		 */
		public String abs() {

			String str = new String();
			int length = 0;
			for (int i = 0; i < this.bigNum.size(); i++) {
				str = this.bigNum.get(i) + str;
				length = this.bigNum.get(i).toString().length();
				if (i != this.bigNum.size() - 1) {
					while (length < (String.valueOf(base).toString().length() - 1)) {
						str = "0" + str;
						length++;
					}
				}
			}
			return str;
		}
		
		/** Function to perform modulus of two Big Integers
		 * 
		 * @param1 num BigNum
		 * @param2 den BigNum 
		 * @return modulus of a type BigNum
		 */
			public static BigNum mod(BigNum num, BigNum den) {
				if (compare(den, new BigNum("0"))==2) {
					System.out.println("Modulo by zero is undefined");
					return null;
				}
			    BigNum a = new BigNum(num.abs());
			    BigNum b = new BigNum(den.abs());
			    BigNum temp=BigNum.divide(num,den);
			    //Calculate quotient of numerator and denominator.
				BigNum quotient=new BigNum(temp.abs());
				//Calculate the quotient times denominator.
				BigNum product= BigNum.product(b, quotient);
				//Calculate reminder of numerator and product.
				BigNum res=BigNum.subtract(a, product);
				res.bigNum=removeLeadingZero(res);
				if(temp.isNeg || (num.isNeg ^ den.isNeg)){
					res.isNeg=true;
				}
				return res;
			}
		

}
