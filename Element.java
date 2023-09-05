import java.util.Scanner;

public class Element {
    private String Name;
    private String Symbol;
    private double Mass;
    private int AtomicNumber;

    public double getMass() {
        return Mass;
    }

    public String getSymbol() {
        return Symbol;
    }

    public int getAtomicNumber() {
        return AtomicNumber;
    }

    public String getName() {
        return Name;
    }

    public Element(String name, String symbol, double mass, int atomicNumber)
    {
        Name= name;
        Symbol = symbol;
        Mass = mass;
        AtomicNumber = atomicNumber;
    }

    public static Element ElementDataToElementType(String ed)
    {
        Scanner s = new Scanner(ed);
        s.useDelimiter("\\s+");

        int aNum = Integer.parseInt(s.next());
        String symbol = s.next();
        String name = s.next();
        double mass = Double.parseDouble(s.next());

        return new Element(name,symbol,mass,aNum);
    }



}
