

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.spec.ECField;
import java.util.Scanner;

public class EquilibriumWindow extends JFrame {
    private JTextField txtK;
    private JTextField txtX;
    private JTextField txtFirstReactant, txtSecondReactant, txtFirstProduct, txtSecondProduct;
    private JTextField txtiFirstReactant, txtiSecondReactant, txtiFirstProduct, txtiSecondProduct;
    private JTextField txtcFirstReactant, txtcSecondReactant, txtcFirstProduct, txtcSecondProduct;
    private JTextField txteFirstReactant, txteSecondReactant, txteFirstProduct, txteSecondProduct;
    private String inFirstReactantFoil, inSecondReactantFoil, inFirstProductFoil, inSecondProductFoil;
    private JButton btnEnter;
    private boolean ruleOfFive = true;

    //private String

    public EquilibriumWindow() {
        super("Equilibrium");
        setSize(400, 210);
        setLayout(new BorderLayout());


        JPanel panTop = new JPanel();
        panTop.setLayout(new BoxLayout(panTop, BoxLayout.Y_AXIS));

        JPanel panK = new JPanel();
        panK.setLayout(new FlowLayout());

        panK.add(new JLabel("\uD835\uDCDA: "));
        panK.add(txtK = new JTextField(8));
        panK.add(btnEnter = new JButton("Enter"));
        panK.add(new JLabel("X="));
        panK.add(txtX = new JTextField(8));

        panTop.add(panK);

        JPanel panReaction = new JPanel();
        panReaction.setLayout(new FlowLayout());
        txtFirstReactant = new JTextField(8);
        panReaction.add(txtFirstReactant);

        txtSecondReactant = new JTextField(8);
        panReaction.add(txtSecondReactant);

        panReaction.add(new JLabel("<-->"));

        txtFirstProduct = new JTextField(8);
        panReaction.add(txtFirstProduct);

        txtSecondProduct = new JTextField(8);
        panReaction.add(txtSecondProduct);

        panTop.add(panReaction);
        add(panTop, BorderLayout.NORTH);


        JPanel panICE = new JPanel();
        panICE.setLayout(new BoxLayout(panICE, BoxLayout.Y_AXIS));

        JPanel panInitial = new JPanel();
        panInitial.setLayout(new FlowLayout());

        txtiFirstReactant = new JTextField(8);
        panInitial.add(txtiFirstReactant);

        txtiSecondReactant = new JTextField(8);
        panInitial.add(txtiSecondReactant);

        panInitial.add(new JLabel("\uD835\uDCF2"));

        txtiFirstProduct = new JTextField(8);
        panInitial.add(txtiFirstProduct);

        txtiSecondProduct = new JTextField(8);
        panInitial.add(txtiSecondProduct);

        panICE.add(panInitial);


        JPanel panChange = new JPanel();
        panChange.setLayout(new FlowLayout());

        txtcFirstReactant = new JTextField(8);
        txtcFirstReactant.setEditable(false);
        panChange.add(txtcFirstReactant);

        txtcSecondReactant = new JTextField(8);
        txtcSecondReactant.setEditable(false);
        panChange.add(txtcSecondReactant);

        panChange.add(new JLabel("\uD835\uDCEC"));

        txtcFirstProduct = new JTextField(8);
        txtcFirstProduct.setEditable(false);
        panChange.add(txtcFirstProduct);

        txtcSecondProduct = new JTextField(8);
        txtcSecondProduct.setEditable(false);
        panChange.add(txtcSecondProduct);
        panICE.add(panChange);


        JPanel panEquilibruim = new JPanel();
        panEquilibruim.setLayout(new FlowLayout());

        txteFirstReactant = new JTextField(8);
        txteFirstReactant.setEditable(false);
        panEquilibruim.add(txteFirstReactant);

        txteSecondReactant = new JTextField(8);
        txteSecondReactant.setEditable(false);
        panEquilibruim.add(txteSecondReactant);

        panEquilibruim.add(new JLabel("\uD835\uDCEE"));

        txteFirstProduct = new JTextField(8);
        txteFirstProduct.setEditable(false);
        panEquilibruim.add(txteFirstProduct);

        txteSecondProduct = new JTextField(8);
        txteSecondProduct.setEditable(false);
        panEquilibruim.add(txteSecondProduct);

        panICE.add(panEquilibruim);
        add(panICE, BorderLayout.CENTER);


        theHandler handler = new theHandler();
        btnEnter.addActionListener(handler);


        setVisible(true);
    }

    private class theHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnEnter) {
                inFirstReactantFoil = "";
                inSecondReactantFoil = "";
                inFirstProductFoil = "";
                inSecondProductFoil = "";

                try {
                    setICEtable(txtFirstReactant, txtiFirstReactant, txtcFirstReactant, txteFirstReactant, 1, true);
                } catch (Exception ignored) {
                }
                try {
                    setICEtable(txtSecondReactant, txtiSecondReactant, txtcSecondReactant, txteSecondReactant, 2, true);
                } catch (Exception ignored) {
                }
                try {
                    setICEtable(txtFirstProduct, txtiFirstProduct, txtcFirstProduct, txteFirstProduct, 3,true);
                } catch (Exception ignored) {
                }
                try {
                    setICEtable(txtSecondProduct, txtiSecondProduct, txtcSecondProduct, txteSecondProduct, 4, true);
                } catch (Exception ignored) {
                }

                double K;
                if (txtK.getText().contains("E")) {
                    K = Double.parseDouble(EvalString.FromScientificToDouble(txtK.getText()));
                } else
                    K = Double.parseDouble(txtK.getText());

                String products = "";
                String reactans = "";

                if (!(inFirstProductFoil.contains("x^2") || inSecondProductFoil.contains("x^2"))) {
                    changeInFoilIfitdNull("", "1");
                    products = EvalString.foil(inFirstProductFoil, inSecondProductFoil, false);
                } else {
                    changeInFoilIfitdNull("", "0");
                    products = (inFirstProductFoil + "+" + inSecondProductFoil).replace("+-", "-");
                    products = EvalString.combineLikeTerms(products);
                }


                if (!(inFirstReactantFoil.contains("x^2") || inSecondReactantFoil.contains("x^2"))) {
                    changeInFoilIfitdNull("1", "");
                    reactans = EvalString.foil(inFirstReactantFoil, inSecondReactantFoil, false);
                } else {
                    changeInFoilIfitdNull("0", "");
                    reactans = (inFirstReactantFoil + "+" + inSecondReactantFoil).replace("+-", "-");
                    reactans = EvalString.combineLikeTerms(reactans);
                }


                System.out.println("products: " + products);
                System.out.println("reactans: " + reactans);
                JOptionPane.showMessageDialog(null,"products: " + products);
                JOptionPane.showMessageDialog(null,"reactans: " + reactans);

                if (!txtK.getText().equals("")) {

                    double preK = 0;
                    double lastK = 0;
                    double lastX = 0;
                    double step = 0;
                    double present = 1;
                    while(present<99.9)
                    {
                        step = 1/(present*100000);
                        lastX+= step;

                        preK=getPreK(products,reactans,lastX);
                        present = (preK/K)*100;


                        System.out.print("-----X: "+lastX);
                        System.out.print("-----pre K: "+ preK);
                        System.out.println("-----%: "+present);


                    }

                }
            }
        }
    }

    private double getPreK(String num, String denom, double x)
    {
        String expresion = "("+num+")/("+denom+")";
        expresion = expresion.replace("x", "*("+x+")");
        return EvalString.eval(expresion);
    }

    private void changeInFoilIfitdNull(String r, String p) {
        if (inFirstReactantFoil.equals("")) inFirstReactantFoil = r;
        if (inSecondReactantFoil.equals("")) inSecondReactantFoil = r;
        if (inFirstProductFoil.equals("")) inFirstProductFoil = p;
        if (inSecondProductFoil.equals("")) inSecondProductFoil = p;
    }

    private boolean checkX(double x) {
        if (!txteFirstReactant.getText().equals("")) {
            String conEx = txteFirstReactant.getText().replace("x", "*" + x);
            if (EvalString.eval(conEx) < 0)
                return false;
        }

        if (!txteSecondReactant.getText().equals("")) {
            String conEx = txteSecondReactant.getText().replace("x", "*" + x);
            if (EvalString.eval(conEx) < 0)
                return false;
        }

        if (!txteFirstProduct.getText().equals("")) {
            String conEx = txteFirstProduct.getText().replace("x", "*" + x);
            if (EvalString.eval(conEx) < 0)
                return false;
        }

        if (!txteSecondProduct.getText().equals("")) {
            String conEx = txteSecondProduct.getText().replace("x", "*" + x);
            if (EvalString.eval(conEx) < 0)
                return false;
        }
        return true;
    }

    private String quadraticForm(double k, String products, String reactans) {
        //left - k*reactans
        //right - products

        String leftSide = EvalString.distributeSingleTerm(k, reactans);

        //move right side to the left to use quadratic formula
        if (!products.contains("x^2"))
            products = "0.0x^2" + products;


        String a_right, b_right, c_right;
        String formattedRight = products.replace("+", "|+").replace("-", "|-");
        Scanner scanner = new Scanner(formattedRight);
        scanner.useDelimiter("[|]");

        a_right = scanner.next();
        System.out.println(a_right);
        b_right = scanner.next();
        System.out.println(b_right);
        c_right = scanner.next();
        System.out.println(c_right);

        leftSide += EvalString.changeTermSign(a_right);
        leftSide += EvalString.changeTermSign(b_right);
        leftSide += EvalString.changeTermSign(c_right);

        leftSide = EvalString.combineLikeTerms(leftSide);
        return leftSide;
    }

    private void setICEtable(JTextField ele, JTextField i, JTextField c, JTextField e, int type, boolean foil) {
        //type = 1 - fistReactant
        //type = 2 - secondReactant
        //type = 3 - firstProduct
        //type = 4 - secondProduct

        String reactant = ele.getText();

        if (ele.getText().equals("") || i.getText().equals("")) throw new RuntimeException();

        if (!Character.isDigit(reactant.charAt(0)))
            reactant = "1" + reactant;

        Scanner scanner = new Scanner(reactant);
        scanner.useDelimiter("[A-Z]");
        int coof = Integer.parseInt(scanner.next());

        double initial;
        if (i.getText().contains("E"))
            initial = Double.parseDouble(EvalString.FromScientificToDouble(i.getText()));
        else
            initial = Double.parseDouble(i.getText());

        String change;
        if (type == 1 || type == 2)
            change = "-" + coof + "x";
        else
            change = "+" + coof + "x";


        String equilibrium = initial + change;


        c.setText(change);
        e.setText(equilibrium);

        if (foil) {
            String foild = EvalString.foil(initial + "|" + change, coof);
            if (type == 1) {
                inFirstReactantFoil = foild;
            } else if (type == 2) {
                inSecondReactantFoil = foild;
            } else if (type == 3) {
                inFirstProductFoil = foild;
            } else {
                inSecondProductFoil = foild;
            }
        }
    }
}
