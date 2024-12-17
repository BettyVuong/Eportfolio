package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * The class is a panel for the buy option which allows users to purchase investments.
 * @author Betty Vuong
 */
public class BuyPanel extends JPanel{
    Portfolio portfolio;
    private String typeStr;

    /**
     * Panel method to create buy panel
     * @param portfolio instance of portfolio to handle backend
     */
    public BuyPanel(Portfolio portfolio){
        this.portfolio = portfolio;
        JPanel upperInputPanel = new JPanel();
        upperInputPanel.setLayout(new GridLayout(6,3, 10, 15));
        Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
        //setLayout(new GridLayout(3,1));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20,20,20,20));

        //title label
        JLabel option = new JLabel("Buying an investment");

        //labels for each input
        JLabel type = new JLabel("Type");
        JLabel symbol = new JLabel("Symbol");
        JLabel name = new JLabel("Name");
        JLabel quant = new JLabel("Quantity");
        JLabel price = new JLabel("Price");

        //creating jcombobox for type
        String [] typeList = {"Stock", "Mutual Fund"};
        JComboBox<String> typeCombo = new JComboBox<>(typeList);
        typeCombo.setSelectedIndex(0);
        typeStr = (String) typeCombo.getSelectedItem();

        //creating input fields
        JTextField symbTxt = new JTextField();
        symbTxt.setBorder(border);
        JTextField nameTxt = new JTextField();
        nameTxt.setBorder(border);
        JTextField quantTxt = new JTextField();
        quantTxt.setBorder(border);
        JTextField priceTxt = new JTextField();
        priceTxt.setBorder(border);

        //creating buttons
        JButton reset = new JButton("Reset");
        //reset.setBorder(border);
        JButton buy = new JButton("Buy");
        //buy.setBorder(border);
        buy.setBackground(Color.WHITE);

        //creating blank fields for column 3
        JLabel blank1 = new JLabel();
        JLabel blank2 = new JLabel();
        JLabel blank3 = new JLabel();
        JLabel blank4 = new JLabel();
        JLabel blank5 = new JLabel();
        //JLabel blank6 = new JLabel();


        //adding to panel
        upperInputPanel.add(option);
        upperInputPanel.add(blank1);
        upperInputPanel.add(blank2);
        upperInputPanel.add(type);
        upperInputPanel.add(typeCombo);
        upperInputPanel.add(blank3);
        upperInputPanel.add(symbol);
        upperInputPanel.add(symbTxt);
        upperInputPanel.add(reset);
        upperInputPanel.add(name);
        upperInputPanel.add(nameTxt);
        upperInputPanel.add(blank4);
        upperInputPanel.add(quant);
        upperInputPanel.add(quantTxt);
        upperInputPanel.add(buy);
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

        //creating ActionListeners to the buttons and combobox
        //for type
        typeCombo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                typeStr = (String) typeCombo.getSelectedItem();
            }
        });

        //for reset
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //clearing all the text fields
                symbTxt.setText("");
                nameTxt.setText("");
                quantTxt.setText("");
                priceTxt.setText("");
            }
        });

        //for buy button
        //call the method in backend main to compute and create a purchase
        buy.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //try catch block to handle exceptions from back end constructors handling the paratmeter pass
                try{
                    String symbStr = symbTxt.getText();
                    String nameStr = nameTxt.getText();
                    String quantStr = quantTxt.getText();
                    String priceStr = priceTxt.getText();
                    //call method for buy and pass the parameters
                    String output = portfolio.buy(typeStr, symbStr, nameStr, quantStr, priceStr);
                    //updating arraylist size count
                    MainWindow.clickMax = Portfolio.investments.size();
                    reset.doClick();
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
