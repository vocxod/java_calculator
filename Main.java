import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

class Main {
  
    // for colored text
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
  public static void welcome(){
    System.out.println(ANSI_YELLOW  + "************************************************************");
    System.out.println("Enter expression for calculation");
    System.out.println("in format: operandA OPERATION operandB and press Enter");
    System.out.println("Where operandA and operandB is digit (arabic from 0 to 10, roman from I to X)");
    System.out.println("OPERATION one from is: + - / * ");
    System.out.println("Enter expression for calculate (CTRL-C to exit)");
    System.out.println("************************************************************" + ANSI_RESET);      
  }
    
  public static void main(String[] args){
    String sResult = ""; 
    String sRomanDigit = "";
    int iRomanDigit = 0;
    
    welcome();

    Scanner myObj = new Scanner(System.in);
    while (sResult != "x") {
      sResult = myObj.nextLine(); // for i,v, c, x to uppercase
      sResult = sResult.toUpperCase();
      // @todo "save" mode for drop unnesessary chars from input string (future dev)
      // "save" mode (default) - drop invalid chars from input string
      // "strict" mode - generate excepion in strint consist invalid chars (not matched regular expression) 
      sResult = cleanString(sResult, "save");  // drop invalid chars from input line
      if (validateInput(sResult) == 1){
        System.out.println(ANSI_BLUE + "Arabic expression is valid" + ANSI_RESET);
      } else if (validateInput(sResult) == 2) {
          System.out.println(ANSI_BLUE + "ROMA expression is valid" + ANSI_RESET);
          // iRomanDigit = romanToDecimal(sResult);
          // System.out.println("From Roma " + sResult + " to Arabic " + Integer.toString(iRomanDigit) + " digit ");
      } else {
          System.out.println(ANSI_RED + "Error input data!" + ANSI_RESET);
      }
      welcome();
      // System.out.println("Your entered: " + sResult);
    }
    System.out.println("Buy!");
  }
 

  /* Check user input string with REGEXP
  * Returns: 
  * 1 if arabic digit is valid
  * 2 if roma digit is valid
  * Error codes for exceptions:
  * 0 - unknow error (if error not detect)
  */
  private static int validateInput(String sInputExpression){
    int iResult = 0;
    
    // check sInput as arabic digit
    Pattern pattern = Pattern.compile("^([0-9]|10)[\\+\\-\\*\\/]([0-9]|10)$", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(sInputExpression);
    boolean matchFound = matcher.find();
    if(matchFound) {
        // arabic digit is valid
      iResult = 1;
    } else {
        // check ROMA digit
        // for any ROMA digit we cal use without LCDM
        // ^/^(X{0,3}I{0,3}|X{0,2}VI{0,3}|X{0,2}|I?[IVX])$/gm$
        // RegExp from https://stackoverflow.com/questions/9340823/how-to-create-regular-expression-checking-roman-numerals
        // RexExp checked on https://regex101.com/
        // regular expression
        pattern = Pattern.compile("^(X{0,3}I{0,3}|X{0,2}VI{0,3}|X{0,2}|I?[IVX])[\\+\\-\\*\\/](X{0,3}I{0,3}|X{0,2}VI{0,3}|X{0,2}|I?[IVX])$", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(sInputExpression);
        matchFound = matcher.find();        
        if (matchFound){
            iResult = 2;
        } else {
            // @todo - check problem and return detail result for get message in exception
            // ignore pronlem and to reinput valid data now
            System.out.println(ANSI_RED + "EXPRESSIN  not valid!" + ANSI_RESET);
        }
    }
    return iResult;
  }

  public static String calc(String sInputExpression) {
    String sResult = sInputExpression;
    return sResult;
  }

  // extract invalid char from source string
  // and return desctination without baned char
  // defence from stupid user
  public static boolean canInsert(char myChar){
    boolean iResult = false;
    char[] enabledChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '/', '*', 'I', 'V', 'X'};
    for (char item: enabledChars){
      // System.out.println("Item " + item + " Char: " + myChar);
      if (myChar == item){
        iResult = true;
        break;
      }
    }
    return iResult;
  }

  public static String cleanString(String sSource, String sMode){
    String sDestination = "";
    for (int i=0; i<sSource.length(); i++){
      // debug out
      // @todo - remove it
      if (i == sSource.length()-1){
          // last char
          System.out.println(sSource.charAt(i));
      } else {
          System.out.print(sSource.charAt(i));
      }  
      // debug out
      
      
      if (canInsert(sSource.charAt(i))){
        sDestination = sDestination + sSource.charAt(i); 
      }
    }
    return sDestination;
  }

  // roman to digit
    public static int value(char r)
    {
        if (r == 'I')
            return 1;
        if (r == 'V')
            return 5;
        if (r == 'X')
            return 10;
        return -1;
    }
 
    // after get caculation - convert result for roman output
    public static String decimalToRoman(){
        String sResult = "";
        return sResult;
    }
    
    // Finds decimal value of a
    // given romal numeral
    public static int romanToDecimal(String str)
    {
        // Initialize result
        int res = 0;
 
        for (int i = 0; i < str.length(); i++)
        {
            // Getting value of symbol s[i]
            int s1 = value(str.charAt(i));
 
            // Getting value of symbol s[i+1]
            if (i + 1 < str.length())
            {
                int s2 = value(str.charAt(i + 1));
 
                // Comparing both values
                if (s1 >= s2)
                {
                    // Value of current symbol
                    // is greater or equalto
                    // the next symbol
                    res = res + s1;
                }
                else
                {
                    // Value of current symbol is
                    // less than the next symbol
                    res = res + s2 - s1;
                    i++;
                }
            }
            else
            {
                res = res + s1;
            }
        }
 
        return res;
    }
 

}

