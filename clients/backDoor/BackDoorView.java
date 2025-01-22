package clients.backDoor;

import middle.MiddleFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 */

public class BackDoorView implements Observer
{
  private static final String RESTOCK  = "Add";
  private static final String CLEAR    = "Clear";
  private static final String QUERY    = "Query";
 
  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels

    private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextField  theInputNo = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();

    private BackDoorController cont= null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-coordinate of position of window on screen
   * @param y     y-coordinate of position of window on screen
   */
  public BackDoorView(  RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                             // 
    {
        mf.makeStockReadWriter();// Database access
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );
    
    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

      JLabel pageTitle = new JLabel();
      pageTitle.setBounds( 110, 0 , 270, 20 );
    pageTitle.setText( "Staff check and manage stock" );                        
    cp.add(pageTitle);

      JButton theBtQuery = new JButton(QUERY);
      theBtQuery.setBounds( 16, 25, 80, 40 );    // Buy button
    theBtQuery.addActionListener(                   // Call back code
      e -> cont.doQuery( theInput.getText() ) );
    cp.add(theBtQuery);                           //  Add to canvas

      JButton theBtRStock = new JButton(RESTOCK);
      theBtRStock.setBounds( 16, 25 + 60, 80, 40 );   // Check Button
    theBtRStock.addActionListener(                  // Call back code
      e -> cont.doRStock( theInput.getText(),
                          theInputNo.getText() ) );
    cp.add(theBtRStock);                          //  Add to canvas

      JButton theBtClear = new JButton(CLEAR);
      theBtClear.setBounds( 16, 25+60*2, 80, 40 );    // Buy button
    theBtClear.addActionListener(                   // Call back code
      e -> cont.doClear() );
    cp.add(theBtClear);                           //  Add to canvas

 
    theAction.setBounds( 110, 25 , 270, 20 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 110, 50, 120, 40 );         // Input Area
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas
    
    theInputNo.setBounds( 260, 50, 120, 40 );       // Input Area
    theInputNo.setText("0");                        // 0
    cp.add( theInputNo );                           //  Add to canvas

      JScrollPane theSP = new JScrollPane();
      theSP.setBounds( 110, 100, 270, 160 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add(theSP);                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here
  }
  
  public void setController( BackDoorController c )
  {
    cont = c;
  }

  /**
   * Update the view, called by notifyObservers(theAction) in model,
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override
  public void update( Observable modelC, Object arg )  
  {
    BackDoorModel model  = (BackDoorModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    
    theOutput.setText( model.getBasket().getDetails() );
    theInput.requestFocus();
  }

}