import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Element> Elements = new ArrayList<Element>();

    public static ArrayList<Element> getElements() {
        return Elements;
    }

    public static void main(String args[]) throws Exception
    {
         ReadFile readFile = new ReadFile("elementsData.txt");
         ArrayList<String> elemrntsData = readFile.Read();

         for(int i = 0; i < elemrntsData.size(); i++)
         {
             Elements.add(Element.ElementDataToElementType(elemrntsData.get(i)));
         }

         //System.out.println(EvalString.foil("",2));


        MainWindow MainWindow = new MainWindow();
//        Scanner scanner = new Scanner(System.in);
//         System.out.println("Enter expression: ");
//         String expr = scanner.next();
//         System.out.println("Equals to: ");
//         double ans = scanner.nextDouble();
//
//         double precent = 1;
//         double step = 0;
//         double x = 0;
//         double newAns = 0;
//        while(precent<100)
//        {
//            int divider = 100*(int)precent;
//            step = 1/precent;
//            step/=divider;
//            x+=step;
//
//
//            newAns = EvalString.eval(expr.replace("x","*"+x));
//            precent=(newAns/ans)*100;
//            System.out.println("expr.: " + expr.replace("x","*"+x));
//            System.out.println("step="+step);
//            System.out.println("x= "+x);
//            System.out.println(precent+"%");
//
//        }
//
//        System.out.println("x= "+x);
//        System.out.println("expression = " +EvalString.eval(expr.replace("x","*"+x)));
//
//


    }
}
