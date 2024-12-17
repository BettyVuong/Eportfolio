package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * Class is for the search panel when user selects sell in the menu, this allows user to input the investments they want to sell.
 * And recieve outputs accordingly.
 * @author Betty Vuong
 */
public class SellPanel extends JPanel{
    Portfolio portfolio;
    /**
     * method displays the sell panel for the GUI interface.
     * @param portfolio an instance of Portfolio for the backend.
     */ 
    public SellPanel(Portfolio portfolio){
        this.portfolio = portfolio;
        JPanel upperInputPanel = new JPanel();
        upperInputPanel.setLayout(new GridLayout(4,3, 10, 15));
        Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
        //setLayout(new GridLayout(3,1));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20,20,20,20));

        //title label
        JLabel option = new JLabel("Selling an investment");

        //labels for each input
        JLabel symbol = new JLabel("Symbol");
        JLabel quant = new JLabel("Quantity");
        JLabel price = new JLabel("Price");

        //creating input fields
        JTextField symbTxt = new JTextField();
        symbTxt.setBorder(border);
        JTextField quantTxt = new JTextField();
        quantTxt.setBorder(border);
        JTextField priceTxt = new JTextField();
        priceTxt.setBorder(border);

        //creating buttons
        JButton reset = new JButton("Reset");
        //reset.setBorder(border);
        JButton sell = new JButton("Sell");
        //sell.setBorder(border);
        sell.setBackground(Color.WHITE);

        //creating blank fields for column 3
        JLabel blank1 = new JLabel();
        JLabel blank2 = new JLabel();
        JLabel blank5 = new JLabel();
        //JLabel blank6 = new JLabel();


        //adding to panel
        upperInputPanel.add(option);
        upperInputPanel.add(blank1);
        upperInputPanel.add(blank2);
        upperInputPanel.add(symbol);
        upperInputPanel.add(symbTxt);
        upperInputPanel.add(reset);
        upperInputPanel.add(quant);
        upperInputPanel.add(quantTxt);
        upperInputPanel.add(sell);
        upperInputPanel.add(price);
        upperInputPanel.add(priceTxt);
        upperInputPanel.add(blank5);
        upperInputPanel.setPreferredSize(new Dimension(800, 360));

        //creating bottom part of the panel
        JLabel msg = new JLabel("Messages");
        msg.setPreferredSize(new Dimension(800, 80));
        //text output
        JTextArea msgArea = new JTextArea();
        msgArea.setEditable(false);//making the area not editable
        msgArea.setLineWrap(true); //wrapping text to frame
        msgArea.setWrapStyleWord(true);//wraps after a word
        //creating scroll
        JScrollPane scroll = new JScrollPane(msgArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(800,160));

        //creating action listener for buttons
        //for reset
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //clearing all the text fields
                symbTxt.setText("");
                quantTxt.setText("");
                priceTxt.setText("");
            }
        });

        //for sell button
        //call the method in backend main to compute and create a purchase
        sell.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //try catch block to handle exceptions from back end constructors handling the paratmeter pass
                try{
                    String symbStr = symbTxt.getText();
                    String quantStr = quantTxt.getText();
                    String priceStr = priceTxt.getText();
                    //call method for buy and pass the parameters
                    String output = portfolio.sell(symbStr, quantStr, priceStr);
                    reset.doClick();
                    //updating arraylist size count
                    MainWindow.clickMax = Portfolio.investments.size();
                    msgArea.setText(msgArea.getText() + output + "\n\n");
                } catch(IllegalArgumentException ex){
                    msgArea.setText(msgArea.getText() + ex.getMessage() + "\n\n");
                }
            }
        });

        //add to the main panel
        add(upperInputPanel, BorderLayout.NORTH);
        add(msg, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }
}
