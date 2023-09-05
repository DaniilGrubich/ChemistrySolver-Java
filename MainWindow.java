

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

public class MainWindow extends JFrame{
    private JButton btnData;
    private JButton btnConverter;
    private JButton btnKinetics;
    private JButton btnEqualibrium;

    public MainWindow(){

        super("Chemistry Solver");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,150);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());


        ImageIcon imgHydrogen = new ImageIcon(getClass().getResource("img/Hydrogen.png"));
        btnData = new JButton("Element Data",imgHydrogen);
        add(btnData);

        ImageIcon imgConverter = new ImageIcon(getClass().getResource("img/Converter.png"));
        btnConverter = new JButton("Converter", imgConverter);
        add(btnConverter);

        ImageIcon imgKinetics = new ImageIcon(getClass().getResource("img/Kinetics.png"));
        btnKinetics = new JButton("Kinetics", imgKinetics);
        add(btnKinetics);

//        btnEqualibrium = new JButton("Equilibrium");
//        add(btnEqualibrium);

        setVisible(true);

        theHandler handler = new theHandler();
        btnData.addActionListener(handler);
        btnConverter.addActionListener(handler);
        btnKinetics.addActionListener(handler);
//        btnEqualibrium.addActionListener(handler);

    }


    private class theHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==btnData)
            {
                ElementInfoWindow elementInfoWindow = new ElementInfoWindow();
            }
            if(e.getSource()==btnConverter)
            {
                ConverterWindow converterWindow = new ConverterWindow();
            }
            if(e.getSource()==btnKinetics)
            {
                KineticsWindow kineticsWindow = new KineticsWindow();
            }
            if(e.getSource()==btnEqualibrium)
            {
                EquilibriumWindow equalibriumWindow = new EquilibriumWindow();
            }

        }
    }
}

