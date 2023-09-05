

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

public class EvalString {

    public static double[] Quadraric(String expr)
    {
        System.out.println("quadratic expres.: " + expr);
        //expr = "1x^2-2x+1";
        expr = expr.replace("x^2", "").replace("x", "");
        Scanner scanner = new Scanner(expr.replace("+", "|+").replace("-", "|-"));
        scanner.useDelimiter("[|]");
        double a = Double.parseDouble(scanner.next());
        double b = Double.parseDouble(scanner.next());
        double c = Double.parseDouble(scanner.next());

        double x[] = new double[2];
        x[0] = ((-b)+Math.sqrt(Math.pow(b,2)+(-4*a*c)))/(2*c);
        x[1] = ((-b)-Math.sqrt(Math.pow(b,2)+(-4*a*c)))/(2*c);

        return x;
    }

    public static String foil(String factor, int power)
    {
        //only binom. foil
        //in 2|+2x format

        String expresion = "";
        if(power==0)
            expresion= "1";
        else if(power==1)
            expresion= factor.replace("|", "");
        else if(power==2)
        {
            factor+="+0.0+0.0x";
            factor = combineLikeTerms(factor).replace("+","|+").replace("-","|-");

            Scanner scanner = new Scanner(factor);
            scanner.useDelimiter("[|]");
            String secondTerm = scanner.next().trim();
            String firstTerm = scanner.next().trim();



            double secondTermDIgit = Double.parseDouble(secondTerm.replace("x",""));

            //1term to 1term
            String tem = Math.pow(Double.parseDouble(firstTerm),2)+"";
            if(tem.contains("E")) tem = FromScientificToDouble(tem);
            System.out.println(tem);
            expresion+=tem;
            //1term to 2term
            tem = (Double.parseDouble(firstTerm)*secondTermDIgit*2)+"";
            if(tem.contains("E")) tem = FromScientificToDouble(tem);
            System.out.println(tem);
            expresion+="+"+tem+"x";
            //2tern to 2term
            tem = Math.pow(secondTermDIgit,2)+"";
            if(tem.contains("E")) tem = FromScientificToDouble(tem);
            System.out.println(tem);
            expresion+="+"+tem+"x^2";

            expresion = expresion.replace("+-", "-");
            expresion = expresion.replace("-+", "-");
            expresion = expresion.replace("--", "+");
            System.out.println(expresion);
        }else
            expresion = ""+Math.pow(Double.parseDouble(factor.replace("0.0x^2","").replace("0.0x","")),power);
        return expresion;
    }

    public static String foil(String f1, String f2,Boolean cube)
    {

        f2+="+0.0x^2+0.0x+0.0";
        f1+="+0.0x+0.0";
        f1 = combineLikeTerms(f1);
        f2 = combineLikeTerms(f2);

        System.out.println("1 Factor: "+f1);
        System.out.println("2 Factor: "+f2);

        f1 = f1.replace("+","|+").replace("-","|-");

        Scanner scanner = new Scanner(f1);
        scanner.useDelimiter("[|]");
        //first term in f1
        double temp = Double.parseDouble(scanner.next().replace("x",""));
        System.out.println("term 1 in factor 1: " + temp);
        String FirstPart = distributeSingleTerm(temp, f2);

        //times First Part by x   0.0x^2+1.0x+8.0-->1.0x^2+8.0x+0.0
        FirstPart = FirstPart.replace("+","|+").replace("-","|-");
        Scanner scanner1 = new Scanner(FirstPart);
        scanner1.useDelimiter("[|]");
        //x^3
        String XXX = scanner1.next().replace("x^2", "x^3");
        if(cube==false)
            XXX="";
        //x^2
        String XX = scanner1.next().replace("x","x^2");
        //x
        String X = scanner1.next()+"x";

        FirstPart = XXX+XX+X;
        System.out.println(FirstPart);

        System.out.println("");
        //second term in f1
        temp = Double.parseDouble(scanner.next());
        System.out.println("term 2 in factoe 1: " + temp);
        String SecondPart = distributeSingleTerm(temp, f2);

        System.out.println(SecondPart);


        String expr = FirstPart + "+" + SecondPart;
        expr = expr.replace("+-", "-").replace("++", "+");
        expr = combineLikeTerms(expr);
        System.out.println("");


        return expr;

    }

    public static String combineLikeTerms(String expr)
    {
        //only with x and x^2 and x^3
        StringBuilder Xs = new StringBuilder();
        StringBuilder XXs = new StringBuilder();
        StringBuilder XXXs = new StringBuilder();

        expr = expr+" ";
        if(expr.charAt(0)!='-')
            expr="+" + expr;

        while(expr.contains("x^3"))
        {
            int positionOfXXX = expr.indexOf("x^3");
            int startPosition = positionOfXXX;

            while(!(startPosition==0||expr.charAt(startPosition)=='-'||expr.charAt(startPosition)=='+'))
                startPosition--;

            String term = expr.substring(startPosition, positionOfXXX+3);
            XXXs.append(term);
            expr = expr.replaceFirst(Pattern.quote(term), "");
        }

        while(expr.contains("x^2"))
        {
            int positionOfX = expr.indexOf("x^2");
            int startPosition = positionOfX;

            while(!(startPosition==0||expr.charAt(startPosition)=='-'||expr.charAt(startPosition)=='+'))
                startPosition--;


            String term = expr.substring(startPosition, positionOfX+3);
            XXs.append(term);
            expr = expr.replaceFirst(Pattern.quote(term), "");
            //System.out.println("new string1: "+expr);
        }


        while(expr.contains("x"))
        {
            int positionOgX = expr.indexOf('x');
            int startPosition = positionOgX;

            while(!(startPosition==0||expr.charAt(startPosition)=='-'||expr.charAt(startPosition)=='+'))
                startPosition--;

            String term = expr.substring(startPosition, positionOgX+1);
            Xs.append(term);
            expr = expr.replaceFirst(Pattern.quote(term), "");
            //System.out.println("new string2: "+expr);
        }



        String temp;
        String sumOfXXXs = "";
        String sumOfXXs = "";
        String sumOfXs = "";
        String sumOfNum = "";
        if(!XXXs.toString().replace("x^3","").equals("")) {
            temp = String.format("%.12f",eval(XXXs.toString().replace("x^3","")));
            sumOfXXXs = temp + "x^3";
        }else{sumOfXXXs="";}

        if(!XXs.toString().replace("x^2","").equals("")) {
            temp = String.format("%.12f",eval(XXs.toString().replace("x^2","")));
            sumOfXXs = temp + "x^2";
        }else{sumOfXXs="";}

        if(!Xs.toString().replace("x", "").equals(""))
        {
            temp = String.format("%.12f",eval(Xs.toString().replace("x", "")));
            sumOfXs = temp + "x";
        }

        if(!expr.equals(""))
            sumOfNum = String.format("%.12f",eval(expr));


        expr = sumOfXXXs + "+" + sumOfXXs + "+" + sumOfXs + "+" + sumOfNum;
        expr = expr.replace("+-","-");

        if(expr.charAt(0)=='+')
            expr = expr.replaceFirst(Pattern.quote("+"), "");

        return expr;
    }

    public static String distributeSingleTerm(double term, String expr)
    {
        //separate term in expression
        //expecting expression to be in ax^2+bx+c format

        expr = expr.replace("-", "|-");
        expr = expr.replace("+", "|+");

        Scanner scanner = new Scanner(expr);
        scanner.useDelimiter("[|]");

        String ex_a;
        String ex_b;
        double ex_c;

        //ax^2
        ex_a = (term * Double.parseDouble(scanner.next().replace("x^2", ""))) + "x^2";
        //bx
        ex_b =(term * Double.parseDouble(scanner.next().replace("x",""))) + "x";
        //c
        ex_c =term * Double.parseDouble(scanner.next());

        expr = (ex_a + "+" + ex_b + "+" + ex_c).replace("+-", "-");
        return expr;

    }

    public static String expandExponentNotation(String s){
        s = s.replace("^", "|");
        Scanner scanner = new Scanner(s);
        scanner.useDelimiter("[|]");
        String toPower = scanner.next();
        int power = Integer.parseInt(scanner.next());

        s = "";
        for(int i = 0; i<power; i++)
        {
            String append = "*";
            if(i == power-1)
                append = "";

            s += toPower + append ;
        }

        return s;
    }

    public static String FromScientificToDouble(String s)
    {
        Scanner sr = new Scanner(s);
        sr.useDelimiter("[E]");

        return String.format("%.12f", sr.nextDouble()*Math.pow(10,sr.nextDouble()));

    }

    public static String changeTermSign(String term)
    {
        StringBuilder numberPart = new StringBuilder();
        StringBuilder notNumberPart = new StringBuilder();
        boolean PartOfTheNumber = true;

        for(int i = 0; i < term.length(); i++)
        {

            if((Character.isDigit(term.charAt(i))||term.charAt(i)=='.'||term.charAt(i)=='+'||term.charAt(i)=='-')&&PartOfTheNumber)
               numberPart.append(term.charAt(i));
            else
            {
                notNumberPart.append(term.charAt(i));
                PartOfTheNumber=false;
            }

        }

        double newNumber = -1 * Double.parseDouble(numberPart.toString());

        if(newNumber>0)
            term = "+"+ newNumber +notNumberPart.toString();
        else
            term = newNumber +notNumberPart.toString();
        return term;
    }

    public static double eval(final String str) {
                    return new Object() {
                        int pos = -1, ch;

                        void nextChar() {
                            ch = (++pos < str.length()) ? str.charAt(pos) : -1;
                        }

                        boolean eat(int charToEat) {
                            while (ch == ' ') nextChar();
                            if (ch == charToEat) {
                                nextChar();
                                return true;
                            }
                            return false;
                        }

                        double parse() {
                            nextChar();
                            double x = parseExpression();
                            //if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                            return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    //else if (func.equals("pow")) x = Math.pow()
                    //else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}
