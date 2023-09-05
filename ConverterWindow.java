import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ConverterWindow extends JFrame{
    private ArrayList<Element> Elements;

    private JButton[] ShortCutButtons;

    private JTextField txtMolecule;
    private JTextField txtInput;
    private JButton btnConvert;
    private JComboBox cobFrom;
    private JCheckBox checkBoxSTP;
    private JPanel panOut;


    public ConverterWindow(){
        super("Converter");

        Elements = Main.getElements();

        setSize(600,300);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        String optionsForFromComboBox[] = {"Grams", "Milligrams", "Kilograms", "Moles", "Liters", "Milliliters"};

        JPanel panElements = new JPanel();
        panElements.setLayout(new GridLayout(0,4));
            ShortCutButtons = new JButton[32];
            String commonUsedElements[] = { "1", "H", "Li", "C","2", "N", "O", "F","3", "Na", "Mg", "Al","4", "S",
                    "Cl", "K","5", "Ca", "Fe", "Ni","6", "Cu", "As", "Br","7","Ag",
                    "I", "Au","8", "Hg", "Pb", "U"};

            for(int i = 0; i < ShortCutButtons.length; i++)
            {
                ShortCutButtons[i] = new JButton();
                addShortCuts(panElements, ShortCutButtons[i], commonUsedElements[i]);
            }
            add(panElements, BorderLayout.WEST);



        JPanel panContent = new JPanel();
        panContent.setLayout(new BoxLayout(panContent, BoxLayout.Y_AXIS));

            JPanel panEnterMolecule = new JPanel();
            panEnterMolecule.setBackground(Color.darkGray);
            panEnterMolecule.setLayout(new FlowLayout());

                JLabel label1 = new JLabel("Molecule/Element: ");
                label1.setForeground(Color.lightGray);
                panEnterMolecule.add(label1);

                txtMolecule = new JTextField(10);
                panEnterMolecule.add(txtMolecule);

                JButton btnClear = new JButton("Clear");
                btnClear.addActionListener(e-> {
                    txtMolecule.setText("");
                    txtInput.setText("");
                });
                panEnterMolecule.add(btnClear);
                panContent.add(panEnterMolecule);


            JPanel panOptions = new JPanel();
            panOptions.setLayout(new FlowLayout());
                JLabel lblFrom = new JLabel("In ");
                panOptions.add(lblFrom);
                cobFrom = new JComboBox(optionsForFromComboBox);
                panOptions.add(cobFrom);

                txtInput = new JTextField("0.0",7);
                panOptions.add(txtInput);

                checkBoxSTP = new JCheckBox("STP");
                panOptions.add(checkBoxSTP);

                btnConvert = new JButton("Convert");
                panOptions.add(btnConvert);
                panContent.add(panOptions);
                add(panContent,BorderLayout.NORTH);



                panContent.add(panOptions);
            //panContent.add();

            JPanel panBottom = new JPanel();
            panBottom.setLayout(new FlowLayout());




        JPanel panRight = new JPanel();
        panRight.setLayout(new FlowLayout());
            panOut = new JPanel();
            panOut.setLayout(new BoxLayout(panOut,BoxLayout.Y_AXIS));

            panOut.add(addLable("Grams: 0.0"));
            panOut.add(addLable("Milligrams: 0.0"));
            panOut.add(addLable("Kilograms: 0.0"));
            panOut.add(addLable("Moles: 0.0" ));
            panOut.add(addLable("Atoms/Molec.: 0.0"));
            panOut.add(addLable("Liters: 0.0"));
            panOut.add(addLable("Milliliters: 0.0"));


        panRight.add(panOut);
        add(panRight,BorderLayout.CENTER);

        theHandler handler = new theHandler();
        txtInput.addActionListener(handler);
        btnConvert.addActionListener(handler);
        setVisible(true);
    }

    private void addShortCuts(JPanel panel,JButton button, String text){
        Font buttonsFont = new Font("Serif", Font.PLAIN, 20);
        button.setText(text);
        button.setFont(buttonsFont);
        button.addActionListener(e -> txtMolecule.setText(txtMolecule.getText() + text));
        panel.add(button);
    }

    private class theHandler implements ActionListener{
        private double grams, mGrams, kGrams, moles, molecules, liters, mLiters;
        @Override
        public void actionPerformed(ActionEvent e) {
            panOut.removeAll();
            if(e.getSource()==btnConvert)
            {
                Molecule m = new Molecule(txtMolecule.getText());
                if(cobFrom.getSelectedItem() == "Grams"){
                    try {
                        double molarMass = m.findMass();

                        grams = Double.parseDouble(txtInput.getText());
                        mGrams = grams*1000;
                        kGrams = grams/1000;

                        moles = grams/molarMass;
                        molecules = moles*Math.pow(6.02,23);
                        if(checkBoxSTP.isSelected())
                            liters = moles*22.4;
                        else{

                            double pressure = Double.parseDouble(
                                    JOptionPane.showInputDialog(null, "Enter pressure in atmospheres: ")
                            );

                            double temperature = Double.parseDouble(
                                    JOptionPane.showInputDialog(null,"Enter temperature in kelvin: ")
                            );
                                    liters = getLitersOnNotSTP(pressure,moles,temperature);
                        }

                        mLiters=liters/1000;


                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                if(cobFrom.getSelectedItem()=="Milligrams"){
                    try {
                        double molarMass = m.findMass();

                        mGrams = Double.parseDouble(txtInput.getText());
                        grams = mGrams*1000;
                        kGrams = grams*1000;

                        moles = grams/molarMass;
                        molecules = moles*Math.pow(6.02,23);
                        if(checkBoxSTP.isSelected())
                            liters = moles*22.4;
                        else{

                            double pressure = Double.parseDouble(
                                    JOptionPane.showInputDialog(null, "Enter pressure in atmospheres: ")
                            );

                            double temperature = Double.parseDouble(
                                    JOptionPane.showInputDialog(null,"Enter temperature in kelvin: ")
                            );
                            liters = getLitersOnNotSTP(pressure,moles,temperature);
                        }
                        mLiters=liters/1000;


                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                if(cobFrom.getSelectedItem() == "Kilograms"){
                    try {
                        double molarMass = m.findMass();

                        kGrams = Double.parseDouble(txtInput.getText());
                        grams = kGrams/1000;
                        mGrams = grams/1000;


                        moles = grams/molarMass;
                        molecules = moles*Math.pow(6.02,23);
                        if(checkBoxSTP.isSelected())
                            liters = moles*22.4;
                        else{

                            double pressure = Double.parseDouble(
                                    JOptionPane.showInputDialog(null, "Enter pressure in atmospheres: ")
                            );

                            double temperature = Double.parseDouble(
                                    JOptionPane.showInputDialog(null,"Enter temperature in kelvin: ")
                            );
                            liters = getLitersOnNotSTP(pressure,moles,temperature);
                        }
                        mLiters=liters/1000;


                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                if(cobFrom.getSelectedItem() == "Moles"){
                    try {
                        double molarMass = m.findMass();

                        moles = Double.parseDouble(txtInput.getText());
                        grams = moles*molarMass;
                        mGrams = grams*1000;
                        kGrams = grams/1000;

                        molecules = moles*Math.pow(6.02,23);
                        if(checkBoxSTP.isSelected())
                            liters = moles*22.4;
                        else{

                            double pressure = Double.parseDouble(
                                    JOptionPane.showInputDialog(null, "Enter pressure in atmospheres: ")
                            );

                            double temperature = Double.parseDouble(
                                    JOptionPane.showInputDialog(null,"Enter temperature in kelvin: ")
                            );
                            liters = getLitersOnNotSTP(pressure,moles,temperature);
                        }
                        mLiters=liters/1000;


                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }


                if(cobFrom.getSelectedItem() == "Liters"){
                    try {
                        double molarMass = m.findMass();

                        liters = Double.parseDouble(txtInput.getText());

                        if(checkBoxSTP.isSelected())
                            moles=liters/22.4;
                        else{
                            double pressure = Double.parseDouble(
                                    JOptionPane.showInputDialog(null, "Enter pressure in atmospheres: ")
                            );

                            double temperature = Double.parseDouble(
                                    JOptionPane.showInputDialog(null,"Enter temperature in kelvin: ")
                            );
                            moles = getMolesOnNotSTP(liters,pressure,temperature);
                        }

                        grams = moles*molarMass;
                        mGrams = grams*1000;
                        kGrams = grams/1000;

                        molecules = moles*Math.pow(6.02,23);
                        mLiters=liters/1000;


                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                if(cobFrom.getSelectedItem() == "Milliliters"){
                    try {
                        double molarMass = m.findMass();

                        mLiters = Double.parseDouble(txtInput.getText());
                        liters = mLiters/1000;
                        if(checkBoxSTP.isSelected())
                            moles=liters/22.4;
                        else{
                            double pressure = Double.parseDouble(
                                    JOptionPane.showInputDialog(null, "Enter pressure in atmospheres: ")
                            );

                            double temperature = Double.parseDouble(
                                    JOptionPane.showInputDialog(null,"Enter temperature in kelvin: ")
                            );
                            moles = getMolesOnNotSTP(liters,pressure,temperature);
                        }

                        grams = moles*molarMass;
                        mGrams = grams*1000;
                        kGrams = grams/1000;
                        molecules = moles*Math.pow(6.02,23);



                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }





                panOut.add(addLable("Grams: " + grams));
                panOut.add(addLable("Milligrams: " + mGrams));
                panOut.add(addLable("Kilograms: " + kGrams));
                panOut.add(addLable("Moles: " + moles));
                panOut.add(addLable("Atoms/Molec.: " + molecules));
                panOut.add(addLable("Liters: " + liters));
                panOut.add(addLable("Milliliters: " + mLiters));


            }

            refresh();
        }
    }

    private double getLitersOnNotSTP(double p, double m, double t)
    {
        return .0821*t*m/p;
    }

    private double getMolesOnNotSTP(double l, double p,double t)
    {
        return l*p/.0821/t;
    }


    private JLabel addLable(String s)
    {
        Font outFont = new Font("Serif", Font.BOLD,18);
        JLabel label = new JLabel(s);
        label.setFont(outFont);
        return label;
    }

    public void refresh()
    {
        //refresh window
        invalidate();
        validate();
        repaint();
    }
}


