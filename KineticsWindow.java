import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class KineticsWindow extends JFrame {
    private JButton btnFormat;
    private JTextPane txtData;
    private JComboBox comboBoxDataFormat;
    private JLabel lblRateLaw;
    private JLabel lblkValue;
    private JPanel panOut;

    public KineticsWindow(){
        super("Kinetics");

        setSize(800,300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        theHandler handler = new theHandler();

        JPanel panInput = new JPanel();
        //panInput.setBounds(0,0,getParent().getWidth(),getParent().getHeight());
        panInput.setBackground(Color.darkGray);
        panInput.setLayout(new BorderLayout());
            JPanel panDataFormat = new JPanel();
            panDataFormat.setBackground(Color.darkGray);
            panDataFormat.setLayout(new FlowLayout());

            JLabel lblEnterFormat = new JLabel("Select Data Format: ");
            lblEnterFormat.setForeground(Color.lightGray);
            panDataFormat.add(lblEnterFormat);

            String dataFormatOptions[] = {"[A] [B] Initial Rate",
                                            "[A] Initial Rate",
                                            "Time [A]"};
            comboBoxDataFormat = new JComboBox(dataFormatOptions);
            panDataFormat.add(comboBoxDataFormat);

            btnFormat = new JButton("Enter");
            btnFormat.addActionListener(handler);
            panDataFormat.add(btnFormat);

            panInput.add(panDataFormat,BorderLayout.NORTH);


            txtData = new JTextPane();
            txtData.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
            panInput.add(txtData,BorderLayout.CENTER);
        add(panInput,BorderLayout.WEST);



        JPanel panSide = new JPanel();
            panSide.setLayout(new BorderLayout());
            panSide.setAlignmentY(Box.CENTER_ALIGNMENT);
            panOut = new JPanel();
            panOut.setLayout(new BoxLayout(panOut, BoxLayout.Y_AXIS));
            
            //new BoxLayout(panOut, BoxLayout.Y_AXIS)




            JPanel panel = new JPanel();
            panel.setBackground(Color.gray);
            JPanel panel1 = new JPanel();
            panel1.setBackground(Color.gray);

            panSide.add(panel, BorderLayout.EAST);
            panSide.add(panel1, BorderLayout.WEST);




            panSide.add(panOut,BorderLayout.CENTER);
        add(panSide,BorderLayout.CENTER);

        setVisible(true);
    }

   private class theHandler implements ActionListener {
       @Override
       public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnFormat)
        {
            //Analise data
            //try {
                KineticDataAnalysis.PutData(comboBoxDataFormat, txtData.getText());




            //Display data
            if(comboBoxDataFormat.getSelectedItem()=="[A] [B] Initial Rate" || comboBoxDataFormat.getSelectedItem()=="[A] Initial Rate")
            {
                String RateLaw = KineticDataAnalysis.getRateLaw();
                double k = KineticDataAnalysis.solveForK(RateLaw,comboBoxDataFormat);

                panOut.removeAll();
                Font font = new Font(Font.DIALOG,Font.PLAIN,20);
                lblRateLaw = new JLabel("Rate Law: " + RateLaw);
                lblkValue = new JLabel("Value of k: " + k);

                lblRateLaw.setFont(font);
                lblkValue.setFont(font);

                panOut.add(lblRateLaw);
                panOut.add(lblkValue);
            }

            if(comboBoxDataFormat.getSelectedItem()=="Time [A]")
            {
                ArrayList<Double[]> data = KineticDataAnalysis.addLnAndInvToData();
                panOut.removeAll();

                //lblkValue.setText(" qwqrqwrq qw   qwr ");
                //lblRateLaw.setText("qwr qQ3 qQ Rr  frwr ");
                //panSide.add(new Graph());


                JPanel graphs = new JPanel();
                graphs.setLayout(new BoxLayout(graphs,BoxLayout.LINE_AXIS));


                JPanel OOrder = new JPanel();
                OOrder.setLayout(new BoxLayout(OOrder,BoxLayout.Y_AXIS));
                OOrder.add(new JComponent() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(new BasicStroke(3));
                        Rectangle rectangle = new Rectangle(0,0,120,120);
                        g2.draw(rectangle);
                        g2.setColor(Color.RED);

                        double x[] = new double[data.size()];
                        double y[] = new double[data.size()];

                        for(int i = 0; i < data.size(); i++)
                        {
                            x[i] = data.get(i)[0];
                            y[i] = data.get(i)[1];
                        }

                        giveProportion(x,y);

                        for(int i = 0; i<data.size(); i++)
                        {
                            int yCoord = (int)(rectangle.height-y[i]);
                            int xCoord = (int) (rectangle.getX()+x[i]);

                            g2.drawLine(xCoord,yCoord,xCoord,yCoord);
                        }

                    }
                });
                OOrder.add(new JLabel("0 Order"));
                graphs.add(OOrder);



                JPanel IOrder = new JPanel();
                IOrder.setLayout(new BoxLayout(IOrder,BoxLayout.Y_AXIS));
                IOrder.add(new JComponent() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(new BasicStroke(3));
                        Rectangle rectangle = new Rectangle(0,0,120,120);
                        g2.draw(rectangle);
                        g2.setColor(Color.RED);

                        double x[] = new double[data.size()];
                        double y[] = new double[data.size()];

                        for(int i = 0; i < data.size(); i++)
                        {
                            x[i] = data.get(i)[0];
                            y[i] = data.get(i)[2];
                        }

                        giveProportion(x,y);

                        for(int i = 0; i<data.size(); i++)
                        {
                            int yCoord = (int)(rectangle.height-y[i]);
                            int xCoord = (int) (rectangle.getX()+x[i]);

                            g2.drawLine(xCoord,yCoord,xCoord,yCoord);
                        }

                    }
                });
                IOrder.add(new JLabel("I Order"));
                graphs.add(IOrder);



                JPanel IIOrder = new JPanel();
                IIOrder.setLayout(new BoxLayout(IIOrder,BoxLayout.Y_AXIS));
                IIOrder.add(new JComponent() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(new BasicStroke(3));
                        Rectangle rectangle = new Rectangle(0,0,120,120);
                        g2.draw(rectangle);
                        g2.setColor(Color.RED);

                        double x[] = new double[data.size()];
                        double y[] = new double[data.size()];

                        for(int i = 0; i < data.size(); i++)
                        {
                            x[i] = data.get(i)[0];
                            y[i] = data.get(i)[3];
                        }

                        giveProportion(x,y);

                        for(int i = 0; i<data.size(); i++)
                        {
                            int yCoord = (int)(rectangle.height-y[i]);
                            int xCoord = (int) (rectangle.getX()+x[i]);

                            g2.drawLine(xCoord,yCoord,xCoord,yCoord );
                        }

                    }
                });
                IIOrder.add(new JLabel("II Order"));
                graphs.add(IIOrder);


                panOut.add(graphs);
                panOut.add(Box.createHorizontalStrut(600));




            }




            //}catch(Exception exe){}finally {error();}
            refresh();
        }
       }
   }

   private void error()
   {
       JOptionPane.showMessageDialog(null,"Wrong input format.");
   }

    public void refresh()
    {
        //refresh window
        invalidate();
        validate();
        repaint();
    }

    private void giveProportion(double x[],double y[])
    {
        double tempArray[] = x;
        Arrays.sort(tempArray);
        double largestNum = tempArray[tempArray.length-1];
        for(int i = 0; i < tempArray.length; i++)
        {
            x[i] = (x[i] * 110)/(largestNum);
        }

        tempArray = y;
        Arrays.sort(tempArray);
        largestNum = tempArray[tempArray.length-1];
        for(int i = 0; i < tempArray.length; i++)
        {
            y[i] = (y[i] * 110)/(largestNum);
        }
    }


}

















                      

























