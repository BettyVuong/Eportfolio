package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 * The main GUI class that holds the frame along with the menu to manipulate the cardlayout and panels.
 * @author Betty Vuong
 */
public class MainWindow extends JFrame{
    public static final int WIDTH = 800;
    public static final int LENGTH = 640;
    private JPanel mainFrame, mainPanel;
    public static Portfolio portfolio;
    public static int clickMax;
    public static UpdatePanel updatePanel;
    public static GainPanel gainPanel;

    /**
     * The method creates the main frame and GUI interface for the main panel. The method also creates the menu.
     * @param portfolio an instance of the Portfolio for acces to the backend.
     */
    public MainWindow(Portfolio portfolio){
        super();
        this.portfolio = portfolio;
        setSize(WIDTH,LENGTH);
        setTitle("ePortfolio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setFont(new Font("Arial", Font.PLAIN, 25));

        //create main frame
        mainFrame = new JPanel();
        mainFrame.setLayout(new CardLayout());

        //creating panels for the different options
        BuyPanel buyPanel = new BuyPanel(portfolio);
        SellPanel sellPanel = new SellPanel(portfolio);
        updatePanel = new UpdatePanel(portfolio);
        gainPanel = new GainPanel(portfolio);
        SearchPanel searchPanel = new SearchPanel(portfolio);

        //create the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,1)); //used for borders
        Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
        JTextArea mainMsg = new JTextArea("\n\n\n\nWelcome to ePortfolio.\n\n\nChoose a command from the \"Commands\" menu to buy or sell"+
        " an investment, update prices for all investments, get gain for the portfolio, search for relevant"+
        " investments, or quit the program.");

        mainMsg.setEditable(false);//making the area not editable
        mainMsg.setLineWrap(true); //wrapping text to frame
        mainMsg.setWrapStyleWord(true);//wraps after a word

        mainPanel.setBorder(border); //creating border on panel
        mainPanel.setBorder(new EmptyBorder(20,20,20,20));

        mainPanel.add(mainMsg); //adding msg to panel

        //creating command menu
        JMenuBar choice = new JMenuBar();
        JMenu choiceList = new JMenu("Commands");

        //creating register choices
        //for buy
        JMenuItem buyChoice = new JMenuItem("Buy Investments");
        buyChoice.addActionListener(e -> changePanel(0));
        choiceList.add(buyChoice);

        //for sell
        JMenuItem sellChoice = new JMenuItem("Sell Investments");
        sellChoice.addActionListener(e -> changePanel(1));
        choiceList.add(sellChoice);

        //for update
        JMenuItem updateChoice = new JMenuItem("Update Investments");
        updateChoice.addActionListener(e -> changePanel(2));
        choiceList.add(updateChoice);

        //for gain
        JMenuItem gainChoice = new JMenuItem("Get Total Gain of Portfolio");
        gainChoice.addActionListener(e -> changePanel(3));
        choiceList.add(gainChoice);

        //for search
        JMenuItem searchChoice = new JMenuItem("Search Investments");
        searchChoice.addActionListener(e -> changePanel(4));
        choiceList.add(searchChoice);

        //for quit
        JMenuItem quitChoice = new JMenuItem("Quit");
        quitChoice.addActionListener(e -> changePanel(5));
        choiceList.add(quitChoice);

        choice.add(choiceList);
        choice.setBorder(border);

        //adding panels to main fram
        mainFrame.add(mainPanel, "main");
        mainFrame.add(buyPanel, "buy");
        mainFrame.add(sellPanel, "sell");
        mainFrame.add(updatePanel, "update");
        mainFrame.add(gainPanel, "gain");
        mainFrame.add(searchPanel, "search");

        //adding panels to frame
        add(choice, BorderLayout.NORTH);
        add(mainFrame, BorderLayout.CENTER);
    }

    /**
     * Method is invoked when the menu box is selected and changes the panell for the south border of the frame layout.
     * Other methods wihin each panel are also invoked when necessary.
     * @param index the index of the panel chosen to show in cardLayout.
     */
    public void changePanel(int index){
        CardLayout panel = (CardLayout) mainFrame.getLayout();
        //chaning panels depending on user
        if(index == 0) {
			panel.show(mainFrame, "buy");
		} else if(index == 1){
            panel.show(mainFrame, "sell");
        } else if(index == 2){
            updatePanel.resetPanel(); //to invoke and have an updated output due to the list changing
            panel.show(mainFrame, "update");
        } else if(index == 3){
            gainPanel.newCalculate(); //to invoke and have an updated output
            panel.show(mainFrame, "gain");
        } else if(index == 4){
            panel.show(mainFrame, "search");
        } else if(index == 5){ //quit program
            System.exit(0);
        }
        else{
            panel.show(mainFrame, "main");
        }
    }
    
}
