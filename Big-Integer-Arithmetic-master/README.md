# Advanced-Algortihm-Implementation
BigNum Implementation for arbitrary precision arithmetic.
Files: 
1. BigNum.java
2. Level1Driver.java -   Contains the driver program for Level 1
3. ParseInputUtil.java - Input file parser for Level2
4. ParseVo.java - Wrapper class for ParseInputUtil 
5. BigNumDriver.java - Takes user input for calling level1 or level 2 input specification


Compilation:
javac BigNum.java
javac Level1Driver.java
javac ParseVo.java
javac ParseInputUtil.java
javac BigNumDriver.java


Execution Command : 

java BigNumDriver
If level 2 is selected and there is no command line arguments then prompts for file path and reads the file for parsing. 

Give inputs in the console and press control+z for starting execution.

If userchoice is 1 then  calls function for testing level1. Subtract function has to be called with subtractUtil(). This function calls subtraction after performing necessary checks.


