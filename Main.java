import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

class Main {
  
  public static void main(String[] args){
    String sResult = ""; 
    System.out.println("************************************************************");
    System.out.println("Enter expression for calculation");
    System.out.println("in format: operandA OPERATION operandB and press Enter");
    System.out.println("Where operandA and operandB is digit (arabic 0-9, roman I-X)");
    System.out.println("OPERATION one from is: + - / * ");
    System.out.println("************************************************************");

    Scanner myObj = new Scanner(System.in);
    while (sResult != "x") {
      System.out.println("Enter expression for calculate (x - exit):");
      sResult = myObj.nextLine().toUpper(); // for i,v, c, x to uppercase

      if (sResult == "x" || sResult == "X"){
        break;
      }
      System.out.println("Your entered: " + sResult);
    }
    System.out.println("Buy!");
  }
 

  private static boolean validateInput(String sInput){
    boolean bResult = false;
    Pattern pattern = Pattern.compile("w3schools", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher("Visit W3Schools!");
    boolean matchFound = matcher.find();
    if(matchFound) {
      bResult = true;
    } else {
      bResult = false;
    }
    return bResult;

  }

  private static boolean is_arabic(char ch){
    boolean bResult = false;
    switch (ch){
      case '0':
        bResult = true;
        break;
      case '1':
        bResult = true;
        break;
      case '2':
        bResult = true;
        break;
      case '3':
        bResult = true;
        break;
      case '4':
        bResult = true;
        break;
      case '5':
        bResult = true;
        break;
      case '6':
        bResult = true;
        break;
      case '7':
        bResult = true;
        break;
      case '8':
        bResult = true;
        break;
      case '9':
        bResult = true;
        break;
      default:
        bResult = false;
    }
    return bResult;

  }

  public static String calc(String input) {
        String sResult = "Result is: " + input;
        return sResult;
    }
}

