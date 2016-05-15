import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Program to trigger call for the BigNum calculation
//User choice 1 - will call Level 1 input
//USer Choice 2 - Will call Level 2 input/output specification
public class BigNumDriver {

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		int userCh = 0;

		System.out.println("Enter your choice");
		System.out.println("1.Level 1 Input");
		System.out.println("2.Level 2 Input/Output Format");
		System.out.println("3.Exit");
		userCh = in.nextInt();
		if (userCh > 0 && userCh <= 3) {
			switch (userCh) {
			case 1:
				Level1Driver.testLevel0();
				break;
			case 2:
				ParseInputUtil parse = new ParseInputUtil();
				String fileName = new String();
				if (args.length > 0) {
					fileName = args[0];
				} else {
					System.out.println("Enter Input File path or press 1 for console input");
					fileName = in.next();
				}
				if (!fileName.equals("1")) {
					File file = new File(fileName);
					Scanner ins;
					try {
						ins = new Scanner(file);
						parse.evaluateInput(ins);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Scanner inConsole = new Scanner(System.in);
					parse.evaluateInput(inConsole);
				}
				break;

			case 3:
				System.out.println("Exiting from Program !");
				System.exit(0);
			}
		}

	}

}
