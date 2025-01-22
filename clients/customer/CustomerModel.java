package clients.customer;

import catalogue.Basket;
import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockException;
import middle.StockReader;

import javax.swing.*;
import java.util.Arrays;
import java.util.Observable;

/**
 * Implements the Model of the customer client
 */
public class CustomerModel extends Observable {
    private Product theProduct = null;          // Current product
    private Basket theBasket = null;          // Bought items

    private String pn = "";                    // Product being processed

    private StockReader theStock = null;
    private OrderProcessing theOrder = null;
    private ImageIcon thePic = null;


    /*
     * Construct the model of the Customer
     * @param mf The factory to create the connection objects
     */
    public CustomerModel(MiddleFactory mf) {
        try
        {
            theStock = mf.makeStockReader();           // Database access
        } catch (Exception e) {
            DEBUG.error("CustomerModel.constructor\n" +
                    "Database not created?\n%s\n", e.getMessage());
        }
        theBasket = makeBasket();                    // Initial Basket
    }

    /**
     * return the Basket of products
     *
     * @return the basket of products
     */
    public Basket getBasket() {
        return theBasket;
    }

    /**
     * Check if the product is in Stock
     *
     * @param productNum The product number
     */
    public void doCheck (String productNum, String... quantitty) {
        theBasket.clear();    // Clear s. list
        String theAction = "";
        pn = productNum.trim();     // Product no.
        int quantity = Integer.parseInt(Arrays.toString(quantitty));
        if (pn.length() == 1) {pn = "000" + pn;}   // Add zeros to product code if single digit ID is entered

        try {
            if (theStock.exists(pn) && Integer.parseInt(pn) < 9 && Integer.parseInt(pn) > 0)
                // ^^ Stock Exists and is in range?
            {                                         // T
                Product pr = theStock.getDetails(pn); //  Product
                if (pr.getQuantity() >= quantity)       //  In stock?
                {
                    theAction =                           //   Display
                            String.format("%s : %7.2f (%2d) ", //
                                    pr.getDescription(),              //    description
                                    pr.getPrice(),                    //    price
                                    pr.getQuantity());               //    quantity
                    pr.setQuantity(quantity);             //   Require 1
                    theBasket.add(pr);                  //   Add to basket
                    thePic = theStock.getImage(pn);     //    product
                } else {                                //  F
                    theAction =                           //   Inform
                            pr.getDescription() +               //    product not
                                    "Not in stock";                   //    in stock
                }
            } else {                                  // F
                theAction =                             //  Inform Unknown
                        "Unknown product number " + pn;       //  product number
            }
        } catch (StockException e) {
            DEBUG.error("CustomerClient.doCheck()\n%s",
                    e.getMessage());
        }
        setChanged();
        notifyObservers(theAction);
    }

    /**
     * Clear the products from the basket
     */
    public void doClear() {
        String theAction = "";
        theBasket.clear();                        // Clear s. list
        theAction = "Enter Product Number";       // Set display
        thePic = null;                            // No picture
        setChanged();
        notifyObservers(theAction);
    }

    /**
     * Return a picture of the product
     *
     * @return An instance of an ImageIcon
     */
    public ImageIcon getPicture() {
        return thePic;
    }

    /**
     * ask for update of view called at start
     */
    private void askForUpdate() {
        setChanged();
        notifyObservers("START only"); // Notify
    }

    /**
     * Make a new Basket
     *
     * @return an instance of a new Basket
     */
    protected Basket makeBasket() {
        return new Basket();
    }
}

