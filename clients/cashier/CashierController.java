package clients.cashier;


/**
 * The Cashier Controller
 */

public class CashierController {
    private final CashierModel model;

    /**
     * Constructor
     *
     * @param model The model
     */
    public CashierController(CashierModel model) {
        this.model = model;
    }

    /**
     * Check interaction from view
     *
     * @param pn The product number to be checked
     */
    public void doCheck(String pn, int quantity) {
        model.doCheck(pn, quantity);
    }

    /**
     * Buy interaction from view
     */
    public void doBuy() {
        model.doBuy();
    }

    public void doClear() {
        model.doClear();
    }

    /**
     * Bought interaction from view
     */
    public void doBought() {
        model.doBought();
    }
}