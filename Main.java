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
    
    // for arabic to roman converter
    private static final int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
    private static final String[] romanLiterals = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };


    public static void welcome() {
        System.out.println(ANSI_YELLOW + "****************************************************************");
        System.out.println("Enter expression for calculation");
        System.out.println("in format: operandA OPERATION operandB and press Enter");
        System.out.println("Where operandA and operandB is digit (arabic from 0 to 10, roman from I to X)");
        System.out.println("OPERATION one from is: + - / * ");
        System.out.println("Type expression for calculate and press ENTER (" +
                ANSI_BLUE + "CTRL-C" + ANSI_YELLOW + " or " + ANSI_BLUE + "q" +
                ANSI_YELLOW + " input for exit)");
        System.out.println("*******************************************************************************" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "Usage: java Main [option]");
        System.out.println(ANSI_CYAN + "--safe: SAFE mode - autocorrect user input: drop invalid chars in input line)");
        System.out.println("*******************************************************************************" + ANSI_RESET);
    }

    public static void main(String[] args) {
        String sResult = "+";
        String sRomanDigit = "";
        int iRomanDigit = 0;

        welcome();

        Scanner myObj = new Scanner(System.in);
        while (sResult.length() > 0 || sResult.length()==0) {
            sResult = myObj.nextLine(); // for i,v, c, x to uppercase
            sResult = sResult.toUpperCase();     
            
            // empty input - need reinput
            if(sResult.length() == 0 ){
                welcome();
                continue;
            }
            
            // type q for exit
            if(sResult.length() == 1 && (sResult.charAt(0) == 'Q')){
                break;
            }
            

            // System.out.println(ANSI_RED + sResult + " [" + sResult.charAt(0) + "] " + ANSI_RESET);
            if( sResult.length() == 1 && sResult.charAt(0) == 'q'){
                break;
            }

            /* @todo "save" mode for drop unnesessary chars from input string 
            (future dev)
            "safe" mode (default) - drop invalid chars from input string
            "strict" mode - generate excepion in strint consist invalid chars (not matched regular expression) 
            */
            sResult = cleanString(sResult, "save");  // drop invalid chars from input line
            if (validateInput(sResult) == 1) {
                // System.out.println(ANSI_BLUE + "Arabic expression is valid" + ANSI_RESET);
                calc(sResult, 'a');
            } else if (validateInput(sResult) == 2) {
                // System.out.println(ANSI_BLUE + "ROMA expression is valid" + ANSI_RESET);
                calc(sResult, 'r');
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
    private static int validateInput(String sInputExpression) {
        int iResult = 0;

        // check sInput as arabic digit
        Pattern pattern = Pattern.compile("^([0-9]|10)[\\+\\-\\*\\/]([0-9]|10)$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sInputExpression);
        boolean matchFound = matcher.find();
        try {
            if (matchFound) {
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
                if (matchFound) {
                    iResult = 2;
                } else {
                    iResult = 3;
                    // ignore pronlem and to reinput valid data now
                    // System.out.println(ANSI_RED + "EXPRESSIN  not valid!" + ANSI_RESET);
                    // check use arabic-rome and rome-arabic chars
                    // A-R
                    // pattern = Pattern.compile("^([0-9]|10)[\\+\\-\\*\\/]([0-9]|10)$", Pattern.CASE_INSENSITIVE);
                    // pattern = Pattern.compile("^(X{0,3}I{0,3}|X{0,2}VI{0,3}|X{0,2}|I?[IVX])[\\+\\-\\*\\/](X{0,3}I{0,3}|X{0,2}VI{0,3}|X{0,2}|I?[IVX])$", Pattern.CASE_INSENSITIVE);
                    pattern = Pattern.compile("^([0-9]|10)[\\+\\-\\*\\/](X{0,3}I{0,3}|X{0,2}VI{0,3}|X{0,2}|I?[IVX])$", Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(sInputExpression);
                    matchFound = matcher.find();
                    if (matchFound){
                        // arabic - roma digit
                        throw new Exception(ANSI_RED + "Your can only ARABIC-ROME digits in expression!" + ANSI_RESET);
                    }
                    pattern = Pattern.compile("^(X{0,3}I{0,3}|X{0,2}VI{0,3}|X{0,2}|I?[IVX])[\\+\\-\\*\\/]([0-9]|10)$", Pattern.CASE_INSENSITIVE);
                    matcher = pattern.matcher(sInputExpression);
                    matchFound = matcher.find();
                    if (matchFound){
                        // roma-arabic digit
                        throw new Exception(ANSI_RED + "Your can only ROME-ARABIC digits in expression!" + ANSI_RESET);
                    }
                    throw new Exception(ANSI_RED + "UNKNOW EXPRESSION - see usege please." + ANSI_RESET);                    
                }
            }            
        } catch (Exception e){
            System.out.println(e);
            Runtime.getRuntime().halt(-200);
        }
        
        return iResult;
    }

    public static int calc(String sInputExpression, char cTypeData) {
        int iCalcResult = 0;
        Pattern p;
        Matcher m;
        int iFirst = 0;
        int iSecond = 0;
        String sTypeOperation = "";
        String sRomanResult;
        switch (cTypeData){
            // arabic
            case 'a':
                iCalcResult = 0;
                p = Pattern.compile("^([0-9]|10)([\\+\\-\\*\\/])([0-9]|10)$", Pattern.MULTILINE);
                m = p.matcher(sInputExpression);
                m.find();
                for (int g = 1; g <= m.groupCount(); g++) {
                    if (g == 1){
                        iFirst = Integer.parseInt(m.group(g));
                    }
                    if (g == 3){
                        iSecond = Integer.parseInt(m.group(g));
                    }
                    if (g == 2){
                        sTypeOperation = m.group(g);
                    }
                    // System.out.print("Part: " + g + " = ");
                    // System.out.println(m.group(g));
                }
                iCalcResult = getResult(iFirst, iSecond, sTypeOperation);
                System.out.println(ANSI_CYAN + "Result=" + ANSI_GREEN + iCalcResult + ANSI_RESET);
                break;
            // rome
            case 'r':
                iCalcResult = 0;
                p = Pattern.compile("^(X{0,3}I{0,3}|X{0,2}VI{0,3}|X{0,2}|I?[IVX])([\\+\\-\\*\\/])(X{0,3}I{0,3}|X{0,2}VI{0,3}|X{0,2}|I?[IVX])$", Pattern.MULTILINE);
                m = p.matcher(sInputExpression);
                m.find();
                for (int g = 1; g <= m.groupCount(); g++) {
                    if (g == 1){
                        iFirst = romanToDecimal(m.group(g));
                    }
                    if (g == 3){
                        iSecond = romanToDecimal(m.group(g));
                    }
                    if (g == 2){
                        sTypeOperation = m.group(g);
                    }
                }
                // check return result
                try {
                    iCalcResult = getResult(iFirst, iSecond, sTypeOperation);
                    // iCalcResult > 0 only else - error exception
                    if (iCalcResult < 1) {
                        // @todo Exception for STRICT mode
                        throw new Exception(ANSI_RED + "Rome digit only NATURAL (great than 0)" + ANSI_RESET);
                        //System.exit(0);
                    }
                    sRomanResult = decimalToRoman(iCalcResult);
                } catch (Exception e) {
                    System.out.println(e);
                    sRomanResult = "***";
                    // fail exit
                    Runtime.getRuntime().halt(-100);
                }
                
                
                System.out.println(ANSI_CYAN + "Result=" + ANSI_GREEN + sRomanResult + ANSI_RESET);
                break;
            // nothing to do
            default:
                iCalcResult = 0;
        }
        return iCalcResult;
    }

    
    private static int getResult(int iFirst, int iSecond, String sOperation){
        int iResult = 0;
        switch (sOperation){
            case "+":
                iResult = iFirst + iSecond;
                break;
            case "-":
                iResult = iFirst - iSecond;
                break;
            case "*":
                iResult = iFirst * iSecond;
                break;
            case "/":
                iResult = iFirst / iSecond;
                break;
            default:
                // @todo exception
                iResult = -100;
        }
        return iResult;
    }
    
    // extract invalid char from source string
    // and return desctination without baned char
    // defence from stupid user
    public static boolean canInsert(char myChar) {
        boolean iResult = false;
        char[] enabledChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '/', '*', 'I', 'V', 'X'};
        for (char item : enabledChars) {
            if (myChar == item) {
                iResult = true;
                break;
            }
        }
        return iResult;
    }

    public static String cleanString(String sSource, String sMode) {
        String sDestination = "";
        for (int i = 0; i < sSource.length(); i++) {
            if (canInsert(sSource.charAt(i))) {
                sDestination = sDestination + sSource.charAt(i);
            }
        }
        return sDestination;
    }

    // roman to digit
    public static int value(char r) {
        if (r == 'I') {
            return 1;
        }
        if (r == 'V') {
            return 5;
        }
        if (r == 'X') {
            return 10;
        }
        return -1;
    }

    // after get caculation - convert result for roman output
    public static String decimalToRoman(int iNumber) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (iNumber >= values[i]) {
                iNumber -= values[i];
                s.append(romanLiterals[i]);
            }
        }
        return s.toString();
    }

    // Finds decimal value of a
    // given romal numeral
    public static int romanToDecimal(String str) {
        // Initialize result
        int res = 0;

        for (int i = 0; i < str.length(); i++) {
            // Getting value of symbol s[i]
            int s1 = value(str.charAt(i));

            // Getting value of symbol s[i+1]
            if (i + 1 < str.length()) {
                int s2 = value(str.charAt(i + 1));

                // Comparing both values
                if (s1 >= s2) {
                    // Value of current symbol
                    // is greater or equalto
                    // the next symbol
                    res = res + s1;
                } else {
                    // Value of current symbol is
                    // less than the next symbol
                    res = res + s2 - s1;
                    i++;
                }
            } else {
                res = res + s1;
            }
        }

        return res;
    }

}
