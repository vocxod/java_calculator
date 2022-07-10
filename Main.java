import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

class Main {
  
  public static void main(String[] args){
    String sResult = ""; 
    String sRomanDigit = "";
    int iRomanDigit = 0;
    System.out.println("************************************************************");
    System.out.println("Enter expression for calculation");
    System.out.println("in format: operandA OPERATION operandB and press Enter");
    System.out.println("Where operandA and operandB is digit (arabic 0-9, roman I-X)");
    System.out.println("OPERATION one from is: + - / * ");
    System.out.println("Enter expression for calculate (CTRL-C to exit)");
    System.out.println("************************************************************");

    Scanner myObj = new Scanner(System.in);
    while (sResult != "x") {
      sResult = myObj.nextLine(); // for i,v, c, x to uppercase
      sResult = sResult.toUpperCase();
      sResult = cleanString(sResult);  // drop invalid chars from input line
      if ( validateInput(sResult) ){
        System.out.println("Expression is valid");
      } else {
        iRomanDigit = romanToDecimal(sResult);
        System.out.println("From Roma " + sResult + " to Arabic " + Integer.toString(iRomanDigit) + " digit ");
        System.out.println("Expression INVALID!");
      }
      System.out.println("Your entered: " + sResult);
    }
    System.out.println("Buy!");
  }
 

  private static boolean validateInput(String sInputExpression){
    boolean bResult = false;
    // check sInput as arabic digit
    // Pattern pattern = Pattern.compile("^[0-9][\\+\\-\\*\\/][0-9]$", Pattern.CASE_INSENSITIVE);
    Pattern pattern = Pattern.compile("^([0-9]|10)[\\+\\-\\*\\/]([0-9]|10)$", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(sInputExpression);
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
  public static String calc(String sInputExpression) {
    String sResult = sInputExpression;
    return sResult;
  }


  // extract invalid char from source string
  // and return desctination without baned char
  
  public static boolean canInsert(char myChar){
    boolean iResult = false;
    char[] enabledChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '/', '*', 'I', 'V', 'X'};
  /*
    char[] enabledChars = new char[19];
    enabledChars[0] = '0';
    enabledChars[1] = '1';
    enabledChars[2] = '2';
    enabledChars[3] = '3';
    enabledChars[4] = '4';
    enabledChars[5] = '5';
    enabledChars[6] = '6';
    enabledChars[7] = '7';
    enabledChars[8] = '8';
    enabledChars[9] = '9';
    enabledChars[10] = '+';
    enabledChars[11] = '-';
    enabledChars[12] = '/';
    enabledChars[13] = '*';
    enabledChars[14] = 'I';
    enabledChars[15] = 'V';
    enabledChars[16] = 'X';
    enabledChars[17] = 'M';
    enabledChars[18] = 'C';
    */
    for (char item: enabledChars){
      System.out.println("Item " + item + " Char: " + myChar);
      if (myChar == item){
        iResult = true;
        break;
      }
    }
    return iResult;
  }

  public static String cleanString(String sSource){
    String sDestination = "";
    for (int i=0; i<sSource.length(); i++){
      System.out.print(sSource.charAt(i));
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
        if (r == 'L')
            return 50;
        if (r == 'C')
            return 100;
        if (r == 'D')
            return 500;
        if (r == 'M')
            return 1000;
        return -1;
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

