package ePortfolio;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

/**
 * the class is used for the gain panel. The class method is invoked when gain is selected in the menu.
 * @author Betty Vuong
 */
public class GainPanel extends JPanel{
    Portfolio portfolio;
    private JTextArea msgArea;
    private JTextArea totalTxt;

    /**
     * Method is for the gain panel GUI interface.
     * @param portfolio an instance of Portfolio for the backend.
     */
    public GainPanel(Portfolio portfolio){
        this.portfolio = portfolio;
        JPanel upperInputPanel = new JPanel();
        upperInputPanel.setLayout(new GridLayout(2,3, 10, 15));
        Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
        //setLayout(new GridLayout(3,1));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20,20,20,20));

        //title label
        JLabel option = new JLabel("Getting Total Gains");

        //labels for each input
        JLabel total = new JLabel("Total gain");

        //creating input fields
        totalTxt = new JTextArea();
        totalTxt.setBorder(border);
        totalTxt.setEditable(false);//making the area not editable
        totalTxt.setLineWrap(true); //wrapping text to frame
        totalTxt.setWrapStyleWord(true);//wraps after a word

        //creating blank fields for column 3
        JLabel blank1 = new JLabel();
        JLabel blank2 = new JLabel();
        JLabel blank3 = new JLabel();


        //adding to panel
        upperInputPanel.add(option);
        upperInputPanel.add(blank1);
        upperInputPanel.add(blank2);
        upperInputPanel.add(total);
        upperInputPanel.add(totalTxt);
        upperInputPanel.add(blank3);
        upperInputPanel.setPreferredSize(new Dimension(800, 160));

        //creating bottom part of the panel
        JLabel msg = new JLabel("Individual Gains");
        msg.setPreferredSize(new Dimension(800, 80));
        //text output
        msgArea = new JTextArea();
        msgArea.setEditable(false);//making the area not editable
        msgArea.setLineWrap(true); //wrapping text to frame
        msgArea.setWrapStyleWord(true);//wraps after a word
        //creating scroll
        JScrollPane scroll = new JScrollPane(msgArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(800,360));

        //setting display for calculations
        String output = portfolio.gain();
        msgArea.setText(output);

        //setting display for total gain
        totalTxt.setText(Portfolio.totalGainStr);

        //add to the main panel
        add(upperInputPanel, BorderLayout.NORTH);
        add(msg, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }

    /**
     * Method is invoked whenever the panel is selected in mainwindow class. It's used to update the gain everytime user
     * manipulates the portfolio.
     */
    public void newCalculate(){
        //setting display for calculations
        String output = portfolio.gain();
        msgArea.setText(output);

        //setting display for total gain
        totalTxt.setText(Portfolio.totalGainStr);
    }
}
