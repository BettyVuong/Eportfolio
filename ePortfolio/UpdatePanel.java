package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * Class is for the update panel. The panel is selected from the menu and is invoked whenever update is clicked.
 * User will be able to traverse their portfolio and update their investments as desired.
 * @author Betty Vuong
 */
public class UpdatePanel extends JPanel {
    Portfolio portfolio;
    private int clickPos = 0;
    private String symbObj;
    private String nameObj;
    private String priceObj;
    private JButton prev;
    private JButton next;
    private JButton save;
    private JTextField symbTxt;
    private JTextField nameTxt;
    private JTextField priceTxt;
    private JTextArea msgArea;

    /**
     * method displays the update panel GUI interface.
     * @param portfolio an instance of Portfolio for the backend.
     */
    public UpdatePanel(Portfolio portfolio) {
        this.portfolio = portfolio;
        JPanel upperInputPanel = new JPanel();
        upperInputPanel.setLayout(new GridLayout(4, 3, 10, 15));
        Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
        // setLayout(new GridLayout(3,1));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // title label
        JLabel option = new JLabel("Updating investments");

        // labels for each input
        JLabel symbol = new JLabel("Symbol");
        JLabel name = new JLabel("Name");
        JLabel price = new JLabel("Price");

        // creating input fields
        symbTxt = new JTextField();
        symbTxt.setBorder(border);
        symbTxt.setEditable(false);// making the area not editable
        nameTxt = new JTextField();
        nameTxt.setBorder(border);
        nameTxt.setEditable(false);// making the area not editable
        priceTxt = new JTextField();
        priceTxt.setBorder(border);

        // creating buttons
        prev = new JButton("Prev");
        // prev.setBorder(border);
        next = new JButton("Next");
        // next.setBorder(border);
        save = new JButton("Save");
        save.setBackground(Color.WHITE);

        // creating blank fields for column 3
        JLabel blank1 = new JLabel();
        JLabel blank2 = new JLabel();

        // adding to panel
        upperInputPanel.add(option);
        upperInputPanel.add(blank1);
        upperInputPanel.add(blank2);
        upperInputPanel.add(symbol);
        upperInputPanel.add(symbTxt);
        upperInputPanel.add(prev);
        upperInputPanel.add(name);
        upperInputPanel.add(nameTxt);
        upperInputPanel.add(next);
        upperInputPanel.add(price);
        upperInputPanel.add(priceTxt);
        upperInputPanel.add(save);
        upperInputPanel.setPreferredSize(new Dimension(800, 360));

        // creating bottom part of the panel
        JLabel msg = new JLabel("Messages");
        msg.setPreferredSize(new Dimension(800, 80));
        // text output
        msgArea = new JTextArea();
        msgArea.setEditable(false);// making the area not editable
        msgArea.setLineWrap(true); // wrapping text to frame
        msgArea.setWrapStyleWord(true);// wraps after a word
        // creating scroll
        JScrollPane scroll = new JScrollPane(msgArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(800, 160));

        // creating action listener for buttons

        // disabling buttons when investments are empty
        if (MainWindow.clickMax == 0) { // no investments at all
            prev.setEnabled(false);
            next.setEnabled(false);
            save.setEnabled(false);
        } else { // investments are available
            prev.setEnabled(true);
            next.setEnabled(true);
            save.setEnabled(true);
        }

        // for prev
        prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (MainWindow.clickMax != 0 && clickPos > 0) {
                    // updating position in list
                    clickPos -= 1;

                    // allowing button to click
                    if (clickPos == 0) {
                        prev.setEnabled(false);
                        next.setEnabled(true);
                    } else {
                        prev.setEnabled(true);
                    }
                    // getting values from object to display
                    Investment instance = Portfolio.investments.get(clickPos);
                    symbObj = instance.getSymbol();
                    nameObj = instance.getName();
                    priceObj = Double.toString(instance.getPrice());

                    // displaying on textfield
                    symbTxt.setText(symbObj);
                    nameTxt.setText(nameObj);
                    priceTxt.setText(priceObj);
                }

                //checking to disable buttons
                if(clickPos == 0){ //disable prev
                    prev.setEnabled(false);
                } else {
                    prev.setEnabled(true);
                }

                if(clickPos == MainWindow.clickMax-1){ //disable next
                    next.setEnabled(false);
                } else{
                    next.setEnabled(true);
                }
            }

        });

        // for next
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (MainWindow.clickMax != 0 && clickPos < MainWindow.clickMax - 1) {
                    // updating position in list
                    clickPos += 1;

                    // allowing button to click
                    if (clickPos == MainWindow.clickMax) {
                        next.setEnabled(false);
                        prev.setEnabled(true);
                    } else {
                        next.setEnabled(true);
                    }
                    // getting values from object to display
                    Investment instance = Portfolio.investments.get(clickPos);
                    symbObj = instance.getSymbol();
                    nameObj = instance.getName();
                    priceObj = Double.toString(instance.getPrice());

                    // displaying on textfield
                    symbTxt.setText(symbObj);
                    nameTxt.setText(nameObj);
                    priceTxt.setText(priceObj);
                }
                //checking to disable buttons
                if(clickPos == 0){ //disable prev
                    prev.setEnabled(false);
                } else {
                    prev.setEnabled(true);
                }

                if(clickPos == MainWindow.clickMax-1){ //disable next
                    next.setEnabled(false);
                } else{
                    next.setEnabled(true);
                }
            }
        });

        // for save
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // getting price from textfield
                    String priceObj = priceTxt.getText();
                    String output = portfolio.update(priceObj, clickPos); // getting the output from main
                    msgArea.setText(msgArea.getText() + output + "\n");
                } catch (IllegalArgumentException ex) {
                    msgArea.setText(msgArea.getText() + ex.getMessage() + "\n");
                }

            }
        });

        // add to the main panel
        add(upperInputPanel, BorderLayout.NORTH);
        add(msg, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }

    /**
     * method is called to update the update panel and refresh the values according to the investments user adds or removes.
     */
    public void resetPanel() {
        // disabling buttons when investments are empty
        if (MainWindow.clickMax == 0) { // no investments at all
            prev.setEnabled(false);
            next.setEnabled(false);
            save.setEnabled(false);
        } else { // investments are available
            prev.setEnabled(true);
            next.setEnabled(true);
            save.setEnabled(true);
        }
        clickPos = 0;

        // getting values from object to display
        if (Portfolio.investments.size() != 0) {
            Investment instance = Portfolio.investments.get(clickPos);
            symbObj = instance.getSymbol();
            nameObj = instance.getName();
            priceObj = Double.toString(instance.getPrice());

            // displaying on textfield
            symbTxt.setText(symbObj);
            nameTxt.setText(nameObj);
            priceTxt.setText(priceObj);
            msgArea.setText("");
        } else { //display null if there is nothing to be updated yet
            symbTxt.setText("");
            nameTxt.setText("");
            priceTxt.setText("");
            msgArea.setText("No investments are in the portfolio to update.");
        }
    }
}
