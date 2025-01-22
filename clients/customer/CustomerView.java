package clients.customer;

import clients.Picture;
import middle.MiddleFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 */

public class CustomerView implements Observer {
    static class Name                              // Names of buttons
    {
        public static final String CHECK = "Check";
        public static final String CLEAR = "Clear";
    }

    private static final int H = 300;       // Height of window pixels
    private static final int W = 400;       // Width  of window pixels

    private final JLabel theAction = new JLabel();
    private final JTextField theInput = new JTextField();
    private final JTextArea theOutput = new JTextArea();

    private final Picture thePicture = new Picture(80, 80);
    private CustomerController cont = null;

    /**
     * Construct the view
     *
     * @param rpc Window in which to construct
     * @param mf  Factor to deliver order and stock objects
     * @param x   x-coordinate of position of window on screen
     * @param y   y-coordinate of position of window on screen
     */

    public CustomerView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try {
            mf.makeStockReader();// Database Access
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        Container cp = rpc.getContentPane();    // Content Pane
        Container rootWindow = (Container) rpc;     // Root Window
        cp.setLayout(null);           // No layout manager
        rootWindow.setSize(W, H);         // Size of Window
        rootWindow.setLocation(x, y);

        Font f = new Font("Monospaced", Font.PLAIN, 12);  // Font f is

        JLabel pageTitle = new JLabel();
        pageTitle.setBounds(110, 0, 270, 20);
        pageTitle.setText("Thank You for Shopping at MiniStore");
        cp.add(pageTitle);

        JButton theBtCheck = new JButton(Name.CHECK);
        theBtCheck.setBounds(16, 25, 80, 40);    // Check button
        theBtCheck.addActionListener(     // Call back code
                e -> cont.doCheck(theInput.getText()));
        cp.add(theBtCheck);         //  Add to canvas

        JButton theBtClear = new JButton(Name.CLEAR);
        theBtClear.setBounds(16, 25 + 60, 80, 40);    // Clear button
        theBtClear.addActionListener(    // Call back code
                e -> cont.doClear());
        cp.add(theBtClear);                           //  Add to canvas

        theAction.setBounds(110, 25, 270, 20);   // Message area
        theAction.setText(" ");       // blank
        cp.add(theAction);      //  Add to canvas
        theAction.setText("Enter item name or ID");

        theInput.setBounds(110, 50, 270, 40);   // Product no area
        theInput.setText("");     // Blank
        cp.add(theInput);      //  Add to canvas

        JScrollPane theSP = new JScrollPane();
        theSP.setBounds(110, 100, 270, 160);  // Scrolling pane
        theOutput.setText("");     //  Blank
        theOutput.setFont(f);     //  Uses font
        cp.add(theSP);         //  Add to canvas
        theSP.getViewport().add(theOutput);    //  In TextArea

        thePicture.setBounds(16, 25 + 60 * 2, 80, 80);   // Picture area
        cp.add(thePicture);    //  Add to canvas
        thePicture.clear();

        rootWindow.setVisible(true);  // Make visible;
        theInput.requestFocus(); // Focus is here
    }

    /**
     * The controller object, used so that an interaction can be passed to the controller
     *
     * @param c The controller
     */

    public void setController(CustomerController c) {
        cont = c;
    }

    /**
     * Update the view
     *
     * @param modelC The observed model
     * @param arg    Specific args
     */

    public void update(Observable modelC, Object arg) {
        CustomerModel model = (CustomerModel) modelC;
        String message = (String) arg;
        theAction.setText(message);
        ImageIcon image = model.getPicture();  // Image of product
        if (image == null) {
            thePicture.clear();                  // Clear picture
        } else {
            thePicture.set(image);             // Display picture
        }
        theOutput.setText(model.getBasket().getDetails());
        theInput.requestFocus();               // Focus is here
    }

}
