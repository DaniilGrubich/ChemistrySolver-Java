import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class Molecule {
    private double MolarMass;
    private String Formula;
    private ArrayList<Element> Elements;

    public Molecule(String f)
    {
        Formula = f;
        Elements = Main.getElements();
        checkSubscript();
        addSpaces();


    }

    ///From Old Program///////////////////////////////////////
    public void checkSubscript()
    {
        String string = Formula;
        int stringLength = string.length();
        char letters[] = new char[stringLength+1];
        for(int i = 0; i < string.length(); i++)
        {
            letters[i] = string.charAt(i);
        }
        letters[stringLength] = ' ';

        string = "";
        for(int i =  0; i < letters.length; i++)
        {
            if((Character.isUpperCase(letters[i]) || Character.isLowerCase(letters[i]))&&((letters[i+1]=='(')||
                    (letters[i+1]==')')||
                    (Character.isUpperCase(letters[i+1]))||
                    (letters[i+1]==' ')||
                    (letters[i+1]=='+')||
                    (letters[i+1]=='-')))

            {
                string+=letters[i] + "1";
            }else{
                string+=letters[i];
            }
        }
        Formula = string;
    }

    private void addSpaces()
    {
        StringBuilder string = new StringBuilder(Formula + " ");
        int stringLength = string.length();
        char letters[] = new char[stringLength];

        for(int i = 0; i < letters.length; i++)
        {
            letters[i] = string.charAt(i);
        }
        string = new StringBuilder();
        for(int i = 0; i < letters.length; i++)
        {

            if((Character.isDigit(letters[i]))||
                    (letters[i] == '(')||
                    (letters[i] == ')')||
                    (letters[i]== '+')){
                string.append(letters[i]).append(" ");
            }else{
                string.append(letters[i]);
            }
        }
        string = new StringBuilder(string.toString().replaceAll("(?<=\\d) +(?=\\d)", ""));
        Formula = string.toString();
    }


    public double findMass() throws Exception
    {

        //replace element symbol with molar mass
        String string = Formula;

        //Replace elements with 2 letters in symbol
        for(int i =0; i < Elements.size(); i++)
        {
            String Symbol = Elements.get(i).getSymbol();
            if(Symbol.length()>1)
            {
                if(string.contains(Symbol))
                {
                    string = string.replaceAll(Symbol, Double.toString(Elements.get(i).getMass()) + "*");
                }
            }
        }
        for(int i =0; i < Elements.size(); i++)
        {
            String Symbol = Elements.get(i).getSymbol();
            if(Symbol.length()==1)
            {
                if(string.contains(Symbol))
                {
                    string = string.replaceAll(Symbol, Double.toString(Elements.get(i).getMass()) + "*");
                }
            }
        }

        //replace spaces with +
        string = string.trim().replaceAll(" ", "+");
        if(string.contains("("))
        {
            //replace "bad" +
            string = string.replace(")+", ")*");
            string = string.replace("(+","(");
            string = string.replace("+)",")");

        }

        //calculate string
        return EvalString.eval(string);
    }

    ////////////////////////////////////////////////////////////////////////


}
