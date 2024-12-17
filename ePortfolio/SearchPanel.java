package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * Class is used for search panel in the menu search option. search will provide an output according to the users input attributes.
 * @author Betty Vuong 
 */
public class SearchPanel extends JPanel{
    Portfolio portfolio;

    /**
     * Method creates the gui interface for search.
     * @param portfolio an instance of Portfolio for the backend.
     */
    public SearchPanel(Portfolio portfolio){
        this.portfolio = portfolio;
        JPanel upperInputPanel = new JPanel();
        upperInputPanel.setLayout(new GridLayout(5,3, 10, 15));
        Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
        //setLayout(new GridLayout(3,1));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20,20,20,20));

        //title label
        JLabel option = new JLabel("Searching investments");

        //labels for each input
        JLabel symbol = new JLabel("Symbol");
        JLabel nameKey = new JLabel("Name keywords");
        JLabel low = new JLabel("Low price");
        JLabel high = new JLabel("High price");

        //creating input fields
        JTextField symbTxt = new JTextField();
        symbTxt.setBorder(border);
        JTextField nameKeyTxt = new JTextField();
        nameKeyTxt.setBorder(border);
        JTextField lowTxt = new JTextField();
        lowTxt.setBorder(border);
        JTextField highTxt = new JTextField();
        highTxt.setBorder(border);

        //creating buttons
        JButton reset = new JButton("Reset");
        //reset.setBorder(border);
        JButton search = new JButton("Search");
        //buy.setBorder(border);
        search.setBackground(Color.WHITE);

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
        upperInputPanel.add(symbol);
        upperInputPanel.add(symbTxt);
        upperInputPanel.add(blank3);
        upperInputPanel.add(nameKey);
        upperInputPanel.add(nameKeyTxt);
        upperInputPanel.add(reset);
        upperInputPanel.add(low);
        upperInputPanel.add(lowTxt);
        upperInputPanel.add(search);
        upperInputPanel.add(high);
        upperInputPanel.add(highTxt);
        upperInputPanel.add(blank5);
        upperInputPanel.setPreferredSize(new Dimension(800, 360));

        //creating bottom part of the panel
        JLabel msg = new JLabel("Search results");
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

        //creating actionlistener for buttons
        //for reset
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //clearing all the text fields
                symbTxt.setText("");
                nameKeyTxt.setText("");
                lowTxt.setText("");
                highTxt.setText("");
            }
        });

        search.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //try catch block to handle exceptions from back end constructors handling the paratmeter pass
                try{
                    String symbStr = symbTxt.getText();
                    String nameKeyStr = nameKeyTxt.getText();
                    String lowStr = lowTxt.getText();
                    String highStr = highTxt.getText();
                    //call method for buy and pass the parameters
                    String output = portfolio.search(symbStr, nameKeyStr, lowStr, highStr);
                    reset.doClick();
                    msgArea.setText(output);
                } catch(IllegalArgumentException ex){
                    msgArea.setText(ex.getMessage());
                }
            }
        });

        //add to the main panel
        add(upperInputPanel, BorderLayout.NORTH);
        add(msg, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }
}
