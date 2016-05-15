import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
/**
 * This Class is responsible for evaluating the expression in the input console and doing
 * required action.
 */
public class ParseInputUtil{
/**
 * Method to evaluate the expression in the input console.
 * @param
 *      Scanner scanner :scanner instance. 
 */
	
	public void evaluateInput(Scanner scanner) {

		Map<Integer, String> lineValueMap = populateLineValueMap(scanner);//populate line number value map.

		Map<String, String> characterValueMap = new LinkedHashMap<>();
		for (Map.Entry<Integer, String> entry : lineValueMap.entrySet()) {
			String value = entry.getValue();
			String[] strList = value.split("=");//split the input based on '='
			//check if the value contains '=' or ')' expression.
			if ((!value.contains("=") || value.contains(")"))&&!value.contains("?")) {
				if (!value.contains(")")) {//value contains equal only.
					System.out.println(characterValueMap.get(value));//output the current value of variable.
				} else {
					//value contains ) .
					String array[] = value.split(Pattern.quote(")"));

					String bigValue = characterValueMap.get(array[0]);//get the current value of variable
					BigNum bigNum = new BigNum(bigValue);
					bigNum.printList();//print the list.
				}
			} else if (value.contains("?")) {
				loop(value,characterValueMap,entry.getKey(),lineValueMap);}
			
			else {
				String inputVariable = strList[0];//input variable.
				String exp = strList[1];//value hold by variable.
                parseExpression(inputVariable, exp, characterValueMap);
				

		}
		}

	}
	/**
	 * Method to iterate if the input pattern contains "?"
	 * @param
	 *       String value:input value.
	 * @param
	 *       Map<String, String> characterValueMap :character value map.
	 * @param
	 *       Integer lineNumber:linenumber input.
	 * @param
	 *       Map<Integer, String> lineValueMap :lineValeMap
	 * 
	 * 
	 */
	public void loop(String value,Map<String, String> characterValueMap,Integer lineNumber,Map<Integer, String> lineValueMap){

		String array[] = value.split(Pattern.quote("?"));
		String varName = array[0];
		Integer targetLineNumber = Integer.parseInt(array[1]);
		String currentValue = characterValueMap.get(varName);
		Long current =Long.parseLong(currentValue);
		BigNum bigValue = new BigNum(current);
		BigNum zero = new BigNum(0L);
		Integer currentLineNumber =lineNumber;
		while (true) {
			boolean test=test(bigValue,zero);
			if (test) {
				break;
			}
			targetLineNumber = Integer.parseInt(array[1]);
			while (targetLineNumber !=currentLineNumber) {
				String targetLineExp = lineValueMap.get(targetLineNumber);
				if(null==targetLineExp)
					break;
				
				if ((!targetLineExp.contains("=") || targetLineExp.contains(")"))&&!targetLineExp.contains("?")) {
					if (!value.contains(")")) {//value contains equal only.
						System.out.println(characterValueMap.get(targetLineExp));//output the current value of variable.
					} else {
						//value contains ) .
						String arrayT[] = value.split(Pattern.quote(")"));

						String bigValueT = characterValueMap.get(arrayT[0]);//get the current value of variable
						BigNum bigNum = new BigNum(bigValueT);
						bigNum.printList();//print the list.
					}
					targetLineNumber++;}
				else if(targetLineExp.contains("?")){
					
					loop(targetLineExp,characterValueMap,targetLineNumber,lineValueMap);
					targetLineNumber++;
				}
				else{
				String[] strListTest = targetLineExp.split("=");
				String currentValTarget = strListTest[0];
				String exp=null; 
		        //System.out.println(strListTest[0]);
		        //System.out.println(strListTest[1]);
				exp = strListTest[1];
				parseExpression(currentValTarget, exp, characterValueMap);
				
				
				String testValue = characterValueMap.get(varName);
				
				
				Long te =Long.parseLong(testValue);
				bigValue = new BigNum(te);
				targetLineNumber++;
				if (BigNum.compare(bigValue, zero) == 2||BigNum.compare(bigValue, zero) == 0) {
					break;
				}
				}
			}
		}

	
	}
	public boolean test(BigNum value1, BigNum value2) {
		boolean status = false;
		if (BigNum.compare(value1, value2) == 2||BigNum.compare(value1, value2) == 0) {
			status = true;

		}

		return status;}
	public  void parseExpression(String inputVariable,String exp,Map<String, String> characterValueMap){
		
        StringTokenizer st = new StringTokenizer(exp);
		String expressionValue = st.nextToken();//contains the info abt input parameters and operation.
        ParseVo parseUtil = parseInput(expressionValue);//parse the expression value.
		//retrieve first second and operation value from parse util.
		String first = parseUtil.getFirstValue();
		String second = parseUtil.getSecondValue();
		String operation = parseUtil.getOperation();
        //if the expression contains no operation then store the value of variale in the map.
		if (!first.isEmpty() && isNumeric(first) && operation.isEmpty()) {
			characterValueMap.put(inputVariable, first);
		} else {
			//expression contains operation.
			BigNum bigNum1 = null;
			BigNum bigNum2 = null;
			bigNum1 = convertToBigNum(first, characterValueMap);//convert first value to bignum.
			bigNum2 = convertToBigNum(second, characterValueMap);//convert second value to bignum.

			BigNum bigNumOutput = doOperation(operation, bigNum1, bigNum2);

			if (null != bigNumOutput)
				characterValueMap.put(inputVariable, bigNumOutput.toString());

		}
	}

	
	/**
	 * Method to perform operation based on based on the input values.
	 * @param
	 *      String operation :operation which need to be performed.
	 * @param
	 *      BigNum bigNum1 :bigNumber input1.
	 * @param
	 *      BigNum bigNum2 :bigNumber input2.
	 *  @return
	 *      BigNum bigNumOutput  :output of the operation.
	 * 
	 */
	public  BigNum doOperation(String operartion, BigNum bigNum1, BigNum bigNum2) {
		BigNum bigNumOutput = null;
		//do the operation according to the operation value.
		if (operartion.equals("+")) {			
			bigNumOutput = BigNum.add(bigNum1, bigNum2);
		} else if (operartion.equals("*")) {
			bigNumOutput = BigNum.product(bigNum1, bigNum2);
		} else if (operartion.equals("-")) {
			bigNumOutput = BigNum.subtractUtil(bigNum1, bigNum2);
		} else if (operartion.equals("^")) {
			bigNumOutput = BigNum.power(bigNum1, bigNum2);
		} else if (operartion.equals("/")) {
			bigNumOutput = BigNum.divide(bigNum1, bigNum2);
			
		} else if (operartion.equals("%")) {
			bigNumOutput = BigNum.mod(bigNum1, bigNum2);
			
		} else if (operartion.equals("~")) {
			
			bigNumOutput = BigNum.squareRoot(bigNum1);			
		} else if (operartion.equals("!")) {
			bigNumOutput = BigNum.factorial(bigNum1);			
		}
		return bigNumOutput;
	}
	/**
	 * Method to convert input string to BigNumber.
	 * @param
	 *       String input :Input String.
	 * @param
	 *       Map<String, String> characterValueMap :map which hold current value of variable.
	 * @return
	 *       BigNum bigNumOutPut: big number output.
	 * 
	 */
	
	public  BigNum convertToBigNum(String input, Map<String, String> characterValueMap) {
		BigNum bigNumOutPut = null;//bigNumber output
		String bigNumValue1 = new String();//String which contain the bigNum .

		if (!input.isEmpty()) {
			if (!isNumeric(input)) {
				bigNumValue1 = characterValueMap.get(input);//get the current value from character valueMap.
			} else {
				bigNumValue1 = input;
			}
			
			bigNumOutPut = new BigNum(bigNumValue1);//convert string to bignumber.
		}
		return bigNumOutPut;
	}
	/**
	 * Method to parse the input string.
	 * @param
	 *       String input:input string.
	 * @return 
	 *       ParseUtil parseUtil:parseutil object which hold info about the operation and input values.
	 * 
	 */
	public  ParseVo parseInput(String input) {
		int i = 0;
		ParseVo parseUtil = new ParseVo();//parseutil object which hold info about the operation and input values.
		String first = new String();
		String second = new String();
		String operartion = new String();
		for (i = 0; i < input.length(); i++) {
			int ascii = (int) input.charAt(i);
			//check if the ascii value corresponds to input operation.
			if (i != 0) {
				if (ascii == 43 || ascii == 45 || ascii == 47 || ascii == 42 || ascii == 94 || ascii == 33
						|| ascii == 37 || ascii == 126) {
					break;
				} else {
					// retrieve the first value
					first = first + input.charAt(i);
				}
			}
			else
			{
				first = first + input.charAt(i);
			}

		}
		//retrieve the operation
		if (i < input.length()) {
			operartion = operartion + input.charAt(i);
			i++;
		}
		//retrieve the second value.
		while (i < input.length()) {
			second = second + input.charAt(i);
			i++;
		}
		//set the values to parseUtil instances.
		parseUtil.setFirstValue(first);
		parseUtil.setSecondValue(second);
		parseUtil.setOperation(operartion);
		return parseUtil;

	}
	/**
	 * Method to populate the lineValue Map.
	 * @param
	 *       Scanner scanner :scanner input object.
	 * @return
	 *       Map<Integer, String> lineValueMap:map which hold info about line number and value it hold.
	 *       
	 */
	public  Map<Integer, String> populateLineValueMap(Scanner scanner) {
		Map<Integer, String> lineValueMap = new LinkedHashMap<>();//map which hold info about line number and value it hold.
		//Iterate till entire input from console read.
		while (scanner.hasNextLine()) {
			String str = scanner.nextLine();
			StringTokenizer st = new StringTokenizer(str);
			int lineNo = Integer.parseInt(st.nextToken());//line number
			String value = st.nextToken();//value
    
			lineValueMap.put(lineNo, value);//put values into map.
		}
		scanner.close();		
		return lineValueMap;
	}
	/**
	 * Method to check whether the input string is numeric or not.
	 * @param
	 *      String input :String input.
	 * @return
	 *      boolean status :return true if the input string is a number .
	 */
	public  boolean isNumeric(String input) {
		//get the char array.
		String test=input;
		String modValue=test.replaceAll("-", "").trim();
		for (char c : modValue.toCharArray()) {
			//check if the Character is digit or not.
			if (!Character.isDigit(c))
				return false;//not digit
		}
		return true;
	}
	
	

}
