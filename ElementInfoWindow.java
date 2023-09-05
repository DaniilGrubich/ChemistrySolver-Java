

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;


public class ElementInfoWindow extends JFrame{
    private ArrayList<Element> Elements;

    private JTextField txtElement;
    private JLabel lblAtomicNumber;
    private JLabel lblSymbol;
    private JLabel lblName;
    private JLabel lblMass;

    public ElementInfoWindow(){
        super("Element Info");

        Elements = Main.getElements();

        setSize(250, 275);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);


        JPanel panEnterElement = new JPanel();
        panEnterElement.setBackground(Color.darkGray);
        panEnterElement.setLayout(new FlowLayout());

        JLabel label = new JLabel("Element: ");
        label.setForeground(Color.lightGray);
        panEnterElement.add(label);

        txtElement = new JTextField(2);
        panEnterElement.add(txtElement);
        add(panEnterElement,BorderLayout.NORTH);



        JPanel panElement = new JPanel();
        panElement.setLayout(new BoxLayout(panElement,BoxLayout.Y_AXIS));
        panElement.add(Box.createVerticalStrut(10));

        Font font = new Font("Serif", Font.BOLD, 20);

        lblAtomicNumber = new JLabel("Atomic Number");
        lblAtomicNumber.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAtomicNumber.setFont(font);
        panElement.add(lblAtomicNumber);

        lblSymbol = new JLabel("Xx");
        lblSymbol.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSymbol.setFont(new Font("Serif", Font.BOLD, 72));
        panElement.add(lblSymbol);

        lblName = new JLabel("Name");
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblName.setFont(font);
        panElement.add(lblName);

        lblMass = new JLabel("Mass");
        lblMass.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMass.setFont(font);
        panElement.add(lblMass);

        add(panElement,BorderLayout.CENTER);

        theHandler handler = new theHandler();
        txtElement.addActionListener(handler);


        setVisible(true);
    }
    private class theHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == txtElement){
                String ElementSymbol = txtElement.getText();
                Iterator iterator = Elements.iterator();

                String oldName = lblName.getText();

                while (iterator.hasNext())
                {
                    Element element = (Element) iterator.next();
                    if(element.getSymbol().equals(ElementSymbol)) {
                        lblAtomicNumber.setText("" + element.getAtomicNumber());
                        lblSymbol.setText(element.getSymbol());
                        lblName.setText(element.getName());
                        lblMass.setText("" + element.getMass());
                    }
                }
                if(lblName.getText().equals(oldName))
                {
                    JOptionPane.showMessageDialog(null,"Stop");
                }
            }

        }
    }
}
